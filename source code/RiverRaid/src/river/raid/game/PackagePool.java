package river.raid.game;

import org.andengine.util.adt.pool.GenericPool;

import android.util.Log;

public class PackagePool extends GenericPool<Package> {

	public static PackagePool instance;

	public static PackagePool sharedBulletPool() {
		if (instance == null)
			instance = new PackagePool();
		return instance;

	}

	private PackagePool() {
		super();
	}

	@Override
	protected Package onAllocatePoolItem() {
		return new Package();
	}

	protected void onHandleRecycleItem(final Package b) {
		b.sprite.clearEntityModifiers();
		b.sprite.clearUpdateHandlers();
		b.sprite.setVisible(false);
		b.sprite.detachSelf();
		Log.v("Jimvaders", "BulletPool onHandleRecycleItem()");
	}
}