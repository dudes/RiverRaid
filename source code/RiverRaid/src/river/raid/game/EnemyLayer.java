package river.raid.game;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.Entity;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.modifier.MoveYModifier;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.util.modifier.IModifier;

import android.util.Log;

public class EnemyLayer extends Entity {

	public LinkedList<Enemy> enemies;
	public static EnemyLayer instance;
	public int enemyCount;
	private Enemy e;
	private IEntityModifierListener moveXListener;
	public static EnemyLayer getSharedInstance() {
		return instance;
	}

	public static boolean isEmpty() {
		if (instance.enemies.size() == 0)
			return true;
		return false;
	}

	public static Iterator<Enemy> getIterator() {
		return instance.enemies.iterator();
	}

	public static int getSize() {
		return instance.enemies.size();
	}

	public void purge() {

		detachChildren();
		for (Enemy e : enemies) {
			EnemyPool.sharedEnemyPool().recyclePoolItem(e);
		}
		enemies.clear();
	}

	public EnemyLayer(int x) {
		enemies = new LinkedList<Enemy>();
		instance = this;
		enemyCount = x;


	}

	public void restart() {
		enemies.clear();
		clearEntityModifiers();
		clearUpdateHandlers();

		for (int i = 0; i < enemyCount; i++) {

			Enemy e = EnemyPool.sharedEnemyPool().obtainPoolItem();
			float finalPosX = (-1 * e.sprite.getWidth());// (i % 6) * 4 *
															// e.sprite.getWidth();
			float finalPosY = ((int) (i / 6)) * e.sprite.getHeight() * 2 + i * e.sprite.getHeight();

			Random r = new Random();
			if (r.nextInt(1) == 0) {
				finalPosX = 900;
				e.direction = 0;
				e.sprite.setPosition(-e.sprite.getWidth() * 3, (r.nextInt(3) + 1) * e.sprite.getHeight());
			} else {
				finalPosX = -140;
				e.direction = 1;
				e.sprite.setPosition(BaseActivity.CAMERA_WIDTH + e.sprite.getWidth() * 3, (r.nextInt(3) + 1) * e.sprite.getHeight());
			}
			
			e.sprite.setVisible(true);
			attachChild(e.sprite);
			e.sprite.registerEntityModifier(new MoveModifier(3, e.sprite.getX(), finalPosX, e.sprite.getY(), finalPosY, moveXListener));

			enemies.add(e);

		}
		setVisible(true);

	}

	/*
		Dodawanie nowego przeciwnika
		Losowanie mu jego poczatkowej pozycji
		Okreslenie w jakim kierunku ma sie od przemieszczac
	*/
	public void addEnemy() {
	
		
		int i = enemies.size();
		e = EnemyPool.sharedEnemyPool().obtainPoolItem();
		float finalPosX = (-1 * e.sprite.getWidth());// (i % 6) * 4 *
														// e.sprite.getWidth();
		float finalPosY = ((int) (i / 6)) * e.sprite.getHeight() * 2 + i * e.sprite.getHeight();

		Random r = new Random();
		int l = r.nextInt(2);
		if (l == 0) {
			finalPosX = 900;
			e.direction = 0;
			
			e.sprite.setPosition(-e.sprite.getWidth() * 3, (r.nextInt(3) + 1) * e.sprite.getHeight());
		} else if(l == 1){
			finalPosX = -140;
			e.direction = 1;
			e.sprite.setPosition(BaseActivity.CAMERA_WIDTH + e.sprite.getWidth() * 3, (r.nextInt(3) + 1) * e.sprite.getHeight());
		}
		e.sprite.setVisible(true);
			
		attachChild(e.sprite);
		moveXListener = new IEntityModifierListener() {
			@Override
			public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {
				// Something you wanna do when modifier is still at work
			}
			@Override
			public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {

			}
		};
		e.sprite.registerEntityModifier(new MoveModifier(3, e.sprite.getX(), finalPosX, e.sprite.getY(), finalPosY, moveXListener));
		createSpriteShootTimeHandler(e.sprite);

		if (enemies.size() > 3)
			for (int j = 0; j < enemies.size() - 3; j++) {
				enemies.remove(j);

			}


	}
	private void createSpriteShootTimeHandler(final AnimatedSprite as) {

		TimerHandler spriteTimerHandler;
		float mEffectSpawnDelay = 0.8f;

		spriteTimerHandler = new TimerHandler(mEffectSpawnDelay, true, new ITimerCallback() {

			@Override
			public void onTimePassed(TimerHandler pTimerHandler) {
				if (as.getX() != -140 && as.getX() != 900)
					shoot(as.getX(), as.getY() + as.getHeight() / 2);
			//	mEffectSpawnDelay -= 0.1f;
			}
		});

		BaseActivity.getSharedInstance().getEngine().registerUpdateHandler(spriteTimerHandler);
	}
	public static void purgeAndRestart() {
		instance.purge();
		instance.restart();
	}

	@Override
	public void onDetached() {
		purge();
		clearUpdateHandlers();
		super.onDetached();
	}

	public void shoot(float x, float y) {

		GameScene scene = (GameScene) BaseActivity.getSharedInstance().mCurrentScene;

		Bullet b = BulletPool.sharedBulletPool().obtainPoolItem();
		b.sprite.setPosition(x, y);
		MoveYModifier mod = new MoveYModifier(1f, y, 460);

		b.sprite.setVisible(true);
		b.sprite.detachSelf();
		scene.attachChild(b.sprite);
		scene.bulletList.add(b);
		b.sprite.registerEntityModifier(mod);

		scene.bulletCount++;
	}

}