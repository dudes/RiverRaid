package river.raid.game;

import org.andengine.util.adt.pool.GenericPool;

import android.util.Log;

/*
	Zbior pociskow widocznych na ekranie
*/
public class BulletPool extends GenericPool<Bullet> {

	public static BulletPool instance;

	public static BulletPool sharedBulletPool() {
		if (instance == null)
			instance = new BulletPool();
		return instance;

	}

	private BulletPool() {
		super();
	}

	@Override
	protected Bullet onAllocatePoolItem() {
		return new Bullet();
	}

	protected void onHandleRecycleItem(final Bullet b) {
		b.sprite.clearEntityModifiers();
		b.sprite.clearUpdateHandlers();
		b.sprite.setVisible(false);
		b.sprite.detachSelf();
	}
}