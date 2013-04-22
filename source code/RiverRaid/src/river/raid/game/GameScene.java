package river.raid.game;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.IEntity;
import org.andengine.entity.IEntityFactory;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.particle.ParticleSystem;
import org.andengine.entity.particle.emitter.PointParticleEmitter;
import org.andengine.entity.particle.initializer.VelocityParticleInitializer;
import org.andengine.entity.particle.modifier.AlphaParticleModifier;
import org.andengine.entity.particle.modifier.RotationParticleModifier;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.ParallaxBackground;
import org.andengine.entity.scene.background.ParallaxBackground.ParallaxEntity;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.AnimatedSprite.IAnimationListener;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.ui.activity.BaseGameActivity;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.color.Color;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.util.Log;

public class GameScene extends Scene implements IOnSceneTouchListener {
	public Ship ship;
	Camera mCamera;
	public float accelerometerSpeedX;
	SensorManager sensorManager;
	public LinkedList<Bullet> bulletList;
	public LinkedList<Package> packageList;
	public LinkedList<Coin> coinList;
	private ArrayList<Package> packageToRemove;
	private ArrayList<Coin> coinToRemove;
	public int bulletCount;
	public int missCount, lastmissCount = -1;;
	public BitmapTextureAtlas mBitmapTextureAtlas;
	public TextureRegion mTextureRegion;
	private BaseActivity activity;
	private EnemyLayer enemyLayer;
	private EagleLayer eagleLayer;
	private BarScene barhud;
	private float enemySpawnTime = 2f;
	private Timer timer;
	private boolean removePackage = false;
	private Time time;
	private Coin coin;
	private Random randCoin;
	private int addMoney = 0;
	public GameScene() {
		activity = BaseActivity.getSharedInstance();
		mBitmapTextureAtlas = new BitmapTextureAtlas(activity.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR);
		mTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mBitmapTextureAtlas, activity, "gfx/background.png", 0, 0);
		mBitmapTextureAtlas.load();
		ParallaxBackground background = new ParallaxBackground(0, 0, 0);
		background.attachParallaxEntity(new ParallaxEntity(0, new Sprite(0, 0, mTextureRegion, activity.getVertexBufferObjectManager())));
		// setBackground(new Background(0.09804f, 0.6274f, 0.8784f));
		setBackground(background);
		// attaching an EnemyLayer entity with 12 enemies on it
		randCoin = new Random();
		enemyLayer = new EnemyLayer(1);
		attachChild(enemyLayer);
		eagleLayer = new EagleLayer(1);
		attachChild(eagleLayer);

		createSpriteSpawnTimeHandler(enemySpawnTime);
		barhud = new BarScene();
		mCamera = BaseActivity.getSharedInstance().mCamera;
		ship = Ship.getSharedInstance();
		ship.sprite.detachSelf();

		HUD hud = new HUD();

		hud.attachChild(barhud.lives);
		barhud.setHP(ship.MAX_HEALTH);
		hud.attachChild(barhud.result);
		barhud.setPoints(missCount);
		hud.attachChild(barhud.tpoints);
		hud.attachChild(barhud.pauseButton);
		mCamera.setHUD(hud);

		bulletList = new LinkedList<Bullet>();
		packageList = new LinkedList<Package>();
		coinList = new LinkedList<Coin>();
		packageToRemove = new ArrayList<Package>();
		coinToRemove = new ArrayList<Coin>();
		attachChild(ship.sprite);
		ship.sprite.setVisible(true);

		BaseActivity.getSharedInstance().setCurrentScene(this);
		sensorManager = (SensorManager) BaseActivity.getSharedInstance().getSystemService(BaseGameActivity.SENSOR_SERVICE);
		SensorListener.getSharedInstance();
		sensorManager.registerListener(SensorListener.getSharedInstance(), sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_GAME);

		setOnSceneTouchListener(this);

		resetValues();

	}
	private void createSpriteSpawnTimeHandler(float interval) {

		timer = new Timer(interval, new Timer.ITimerCallback() {
			public void onTick() {
				if (ship.hp > 0) {
					if (missCount % 10 == 0 || missCount % 7 == 0 && missCount % 3 != 1)
						eagleLayer.addEnemy();
					enemyLayer.addEnemy();
				}
			}
		});
		activity.getEngine().registerUpdateHandler(timer);
	}
	// method to reset values and restart the game
	public void resetValues() {
		missCount = 0;
		bulletCount = 0;
		ship.restart();
		EnemyLayer.purgeAndRestart();
		EagleLayer.purgeAndRestart();
		clearChildScene();
		registerUpdateHandler(new GameLoopUpdateHandler());
		activity.mCurrentScene.detachSelf();
	//	ship.sprite.setCurrentTileIndex(0);
	}

	public void moveShip() {
		// Log.d("dd","ddddddddd");
		ship.moveShip(accelerometerSpeedX);

	}

	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		if (!CoolDown.getSharedInstance().checkValidity())
			return false;

		synchronized (this) {
			ship.shoot();
		}
		return true;
	}

	public void detach() {
		Log.v("Jimvaders", "GameScene onDetached()");
		clearUpdateHandlers();
		for (Bullet b : bulletList) {
			BulletPool.sharedBulletPool().recyclePoolItem(b);
		}
		bulletList.clear();
		detachChildren();
		Ship.instance = null;
		EnemyPool.instance = null;
		BulletPool.instance = null;

	}

	public void cleaner() {
		Log.d("ddddd,", "cliner");
		synchronized (this) {
			// setChildScene(new BarScene(mCamera));
			// if all Enemies are killed
			for (int g = 0; g < packageToRemove.size(); ++g) {
				PackagePool.sharedBulletPool().recyclePoolItem(packageToRemove.get(g));
				packageToRemove.remove(g);
				CoinPool.sharedBulletPool().recyclePoolItem(coin);
				//detachChild(coin.sprite);
			}
//			for (int g = 0; g < coinToRemove.size(); ++g) {
//				CoinPool.sharedBulletPool().recyclePoolItem(coinToRemove.get(g));
//				coinToRemove.remove(g);
//				//CoinPool.sharedBulletPool().recyclePoolItem(coin);
//				//detachChild(coin.sprite);
//			}
			barhud.setHP(ship.hp);
			barhud.setPoints(missCount);
			if (ship.hp == 0)
				ship.moveable = false;

			if (missCount % 10 == 0 && lastmissCount != missCount) {
				enemySpawnTime -= 0.1;
				timer.setInterval(enemySpawnTime);
				lastmissCount = missCount;
			}
			if(missCount >= 2000 && missCount <= 2200)
				enemySpawnTime = 1;
			if (EnemyLayer.isEmpty()) {
				Log.v("Jimvaders", "GameScene Cleaner() cleared");

				clearUpdateHandlers();
			}
			Iterator<Enemy> eIt = EnemyLayer.getIterator();
		//	final Iterator<Coin> coinIterator = coinList.iterator();
			final Iterator<Package> packageIterator = packageList.iterator();
			while (packageIterator.hasNext()) {
				final Package p = packageIterator.next();
				
				if (p.sprite.getY() >= 460 - p.sprite.getWidth() / 3) {
					if (p.sprite.collidesWith(ship.sprite)) {

						if (!p.sprite.isAnimationRunning()) {
							p.sprite.animate(150, false, new IAnimationListener() {
								@Override
								public void onAnimationStarted(AnimatedSprite pAnimatedSprite, int pInitialLoopCount) {
									// TODO Auto-generated method stub
									Log.d("ppppppppppppppppppppppppp", "start    " + p);

									// scene.packageList.add(b);
								}

								@Override
								public void onAnimationFrameChanged(AnimatedSprite pAnimatedSprite, int pOldFrameIndex, int pNewFrameIndex) {
									// TODO Auto-generated method stub

								}

								@Override
								public void onAnimationLoopFinished(AnimatedSprite pAnimatedSprite, int pRemainingLoopCount, int pInitialLoopCount) {
									// TODO Auto-generated method stub

								}

								@Override
								public void onAnimationFinished(AnimatedSprite pAnimatedSprite) {

									packageToRemove.add(p);
//									time = TimePool.sharedBulletPool().obtainPoolItem();
//									time.sprite.setPosition(p.sprite.getX() + p.sprite.getWidth() / 2, p.sprite.getY());
//									time.sprite.setCurrentTileIndex(0);
//									time.sprite.setVisible(true);
//									time.sprite.detachSelf();
//									MoveModifier modtimer = new MoveModifier(0.4f, p.sprite.getX() + p.sprite.getWidth() / 2, ship.sprite.getX(), p.sprite.getY(), ship.sprite.getY()
//											- ship.sprite.getHeight() / 4) {
//
//										@Override
//										protected void onModifierFinished(IEntity pItem) {
//											// TODO Auto-generated method stub
//											super.onModifierFinished(pItem);
//											ship.hp++;
//										//	TimePool.sharedBulletPool().recyclePoolItem(time);
//											//detachChild(time.sprite);
//										}
//
//									};
//									time.sprite.registerEntityModifier(modtimer);
//									attachChild(time.sprite);
									int whatCoin = randCoin.nextInt(3);
							//		final Coin c = coinIterator.next();
									coin = CoinPool.sharedBulletPool().obtainPoolItem();
									if(whatCoin == 0){
										coin.sprite.setCurrentTileIndex(4);
										addMoney = 10;
									}
									else if(whatCoin == 1){
										coin.sprite.setCurrentTileIndex(5);
										addMoney = 20;
									}
									else if(whatCoin == 3){
										coin.sprite.setCurrentTileIndex(6);
										addMoney = 30;
									}
									coin.sprite.setPosition(p.sprite.getX() + p.sprite.getWidth() / 2, p.sprite.getY());
								//	coin.sprite.setCurrentTileIndex(0);
									coin.sprite.setVisible(true);
									coin.sprite.detachSelf();
									MoveModifier mod = new MoveModifier(0.4f, p.sprite.getX() + p.sprite.getWidth() / 2, ship.sprite.getX(), p.sprite.getY(), ship.sprite.getY()
											- ship.sprite.getHeight() / 4) {

										@Override
										protected void onModifierFinished(IEntity pItem) {
											// TODO Auto-generated method stub
											super.onModifierFinished(pItem);
											missCount += addMoney;
										//	coinToRemove.add(c);
										}

									};
									coin.sprite.registerEntityModifier(mod);
									attachChild(coin.sprite);
								}

							});

							// }
						}
					}

					continue;
				}
			}
			while (eIt.hasNext()) {
				Enemy e = eIt.next();

				Iterator<Bullet> it = bulletList.iterator();

				while (it.hasNext()) {
					Bullet b = it.next();
					if (b.sprite.getY() >= 460) {
						BulletPool.sharedBulletPool().recyclePoolItem(b);
						it.remove();
						missCount++;
						continue;
					}

					if (b.sprite.collidesWith(ship.sprite) && ship.hp > 0) {

						if (!ship.gotHit()) {
							createExplosion(ship.sprite.getX() + ship.sprite.getWidth() / 2, ship.sprite.getY() + ship.sprite.getHeight() / 2, ship.sprite.getParent(),
									BaseActivity.getSharedInstance());
							setChildScene(new GameOverScene(mCamera,missCount));
						}
						BulletPool.sharedBulletPool().recyclePoolItem(b);
						it.remove();
						break;
					}
				}

			}
		}
	}

	private void createExplosion(final float posX, final float posY, final IEntity target, final SimpleBaseGameActivity activity) {

		int mNumPart = 90;
		int mTimePart = 2;

		PointParticleEmitter particleEmitter = new PointParticleEmitter(posX, posY);
		IEntityFactory<Rectangle> recFact = new IEntityFactory<Rectangle>() {

			@Override
			public Rectangle create(float pX, float pY) {
				Rectangle rect = new Rectangle(posX, posY, 4, 4, activity.getVertexBufferObjectManager());
				rect.setColor(Color.GREEN);
				return rect;
			}

		};
		final ParticleSystem<Rectangle> particleSystem = new ParticleSystem<Rectangle>(recFact, particleEmitter, 500, 500, mNumPart);

		particleSystem.addParticleInitializer(new VelocityParticleInitializer<Rectangle>(-50, 50, -50, 50));

		particleSystem.addParticleModifier(new AlphaParticleModifier<Rectangle>(0, 0.6f * mTimePart, 1, 0));
		particleSystem.addParticleModifier(new RotationParticleModifier<Rectangle>(0, mTimePart, 0, 360));

		target.attachChild(particleSystem);
		target.registerUpdateHandler(new TimerHandler(mTimePart, new ITimerCallback() {
			@Override
			public void onTimePassed(final TimerHandler pTimerHandler) {
				particleSystem.detachSelf();
				target.sortChildren();
				target.unregisterUpdateHandler(pTimerHandler);
			}
		}));

	}
	public void setPopup(Engine engine){
		if(!hasChildScene())
			setChildScene(new PauseGameScene(mCamera,engine),false, true, true);
	}
}
