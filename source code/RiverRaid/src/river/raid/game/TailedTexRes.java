package river.raid.game;

import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.ui.activity.SimpleBaseGameActivity;

public class TailedTexRes {
	private BitmapTextureAtlas texBanana, texPlanes, texAdditions, texPackage, texButtons;
	public TiledTextureRegion regBanana, regPlanes, regAdditions, regPackage, regButtons;
	private static int SPR_COLUMN = 8;
	private static int SPR_ROWS = 2;

	private static int SPR_COLUMN_PLANES = 3;
	private static int SPR_ROWS_PLANES = 4;

	private static int SPR_COLUMN_ADDITIONS = 4;
	private static int SPR_ROWS_ADDITIONS = 2;

	private static int SPR_COLUMN_PACKAGE = 4;
	private static int SPR_ROWS_PACKAGE = 1;

	private static int SPR_COLUMN_BUTTONS = 4;
	private static int SPR_ROWS_BUTTONS = 2;
	
	
private static TailedTexRes instance = null;
	
	public TailedTexRes(){
		
	}
	
	public static TailedTexRes getInstance(){
		if(instance == null)
		{
			instance = new TailedTexRes();
		}
		return instance;
	}
	
	public void loadRes(SimpleBaseGameActivity act){
		texBanana = new BitmapTextureAtlas(act.getTextureManager(), 1024, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		regBanana = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(texBanana, act.getAssets(), "gfx/bob.png", 0, 0, SPR_COLUMN, SPR_ROWS);
		texBanana.load();

		texPlanes = new BitmapTextureAtlas(act.getTextureManager(), 1024, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		regPlanes = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(texPlanes, act.getAssets(), "gfx/hero.png", 0, 0, SPR_COLUMN_PLANES, SPR_ROWS_PLANES);
		texPlanes.load();

		texAdditions = new BitmapTextureAtlas(act.getTextureManager(), 1024, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		regAdditions = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(texAdditions, act.getAssets(), "gfx/additions.png", 0, 0, SPR_COLUMN_ADDITIONS, SPR_ROWS_ADDITIONS);
		texAdditions.load();

		texPackage = new BitmapTextureAtlas(act.getTextureManager(), 512, 64, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		regPackage = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(texPackage, act.getAssets(), "gfx/package.png", 0, 0, SPR_COLUMN_PACKAGE, SPR_ROWS_PACKAGE);
		texPackage.load();
	}
}
