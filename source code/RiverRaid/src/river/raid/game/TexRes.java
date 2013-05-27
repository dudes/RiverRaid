package river.raid.game;

import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.ui.activity.SimpleBaseGameActivity;

public class TexRes {
	private BitmapTextureAtlas tex2lines, texPlayButton, texSmoke, texExit,
			texYesButton, texNoButton, texSettings, texScores, texPauseButton,
			texMenuBackground, texMenuText, texPopup, texGamePopup,
			texMenuButton, texPlayAgainButton, texSubmitButton, texSoundOn,
			texSoundOff;
	public TextureRegion reg2lines, regPlayButton, regSmoke, regExit,
			regMenuBackground, regSettings, regScores, regMenuText, regPopup,
			regYesButton, regNoButton, regPauseButton, regGamePopup,
			regMenuButton, regPlayAgainButton, regSubmitButton, regSoundOn,
			regSoundOff;

	private static TexRes instance = null;

	public TexRes() {

	}

	public static TexRes getInstance() {
		if (instance == null) {
			instance = new TexRes();
		}
		return instance;
	}

	public void loadRes(SimpleBaseGameActivity act) {
		tex2lines = new BitmapTextureAtlas(act.getTextureManager(), 64, 128, TextureOptions.BILINEAR);
		reg2lines = BitmapTextureAtlasTextureRegionFactory.createFromAsset(tex2lines, act.getAssets(), "gfx/2lines.png", 0, 0);
		tex2lines.load();

		texPlayButton = new BitmapTextureAtlas(act.getTextureManager(), 128, 128, TextureOptions.BILINEAR);
		regPlayButton = BitmapTextureAtlasTextureRegionFactory.createFromAsset(texPlayButton, act.getAssets(), "gfx/playbutton.png", 0, 0);
		texPlayButton.load();

		texSmoke = new BitmapTextureAtlas(act.getTextureManager(), 32, 32, TextureOptions.BILINEAR);
		regSmoke = BitmapTextureAtlasTextureRegionFactory.createFromAsset(texSmoke, act.getAssets(), "gfx/particle_fire.png", 0, 0);
		texSmoke.load();

		texExit = new BitmapTextureAtlas(act.getTextureManager(), 64, 64, TextureOptions.BILINEAR);
		regExit = BitmapTextureAtlasTextureRegionFactory.createFromAsset(texExit, act.getAssets(), "gfx/exitbutton.png", 0, 0);
		texExit.load();

		texSettings = new BitmapTextureAtlas(act.getTextureManager(), 64, 64, TextureOptions.BILINEAR);
		regSettings = BitmapTextureAtlasTextureRegionFactory.createFromAsset(texSettings, act.getAssets(), "gfx/settingsbutton.png", 0, 0);
		texSettings.load();

		texScores = new BitmapTextureAtlas(act.getTextureManager(), 64, 64, TextureOptions.BILINEAR);
		regScores = BitmapTextureAtlasTextureRegionFactory.createFromAsset(texScores, act.getAssets(), "gfx/scoresbutton.png", 0, 0);
		texScores.load();

		texYesButton = new BitmapTextureAtlas(act.getTextureManager(), 64, 64, TextureOptions.BILINEAR);
		regYesButton = BitmapTextureAtlasTextureRegionFactory.createFromAsset(texYesButton, act.getAssets(), "gfx/yesbutton.png", 0, 0);
		texYesButton.load();

		texNoButton = new BitmapTextureAtlas(act.getTextureManager(), 64, 64, TextureOptions.BILINEAR);
		regNoButton = BitmapTextureAtlasTextureRegionFactory.createFromAsset(texNoButton, act.getAssets(), "gfx/nobutton.png", 0, 0);
		texNoButton.load();

		texPauseButton = new BitmapTextureAtlas(act.getTextureManager(), 64, 64, TextureOptions.BILINEAR);
		regPauseButton = BitmapTextureAtlasTextureRegionFactory.createFromAsset(texPauseButton, act.getAssets(), "gfx/pausebutton.png", 0, 0);
		texPauseButton.load();

		texMenuBackground = new BitmapTextureAtlas(act.getTextureManager(), 1024, 512, TextureOptions.BILINEAR);
		regMenuBackground = BitmapTextureAtlasTextureRegionFactory.createFromAsset(texMenuBackground, act.getAssets(), "gfx/menubackground.png", 0, 0);
		texMenuBackground.load();

		texMenuText = new BitmapTextureAtlas(act.getTextureManager(), 512, 256, TextureOptions.BILINEAR);
		regMenuText = BitmapTextureAtlasTextureRegionFactory.createFromAsset(texMenuText, act.getAssets(), "gfx/maintext.png", 0, 0);
		texMenuText.load();

		texMenuButton = new BitmapTextureAtlas(act.getTextureManager(), 64, 64, TextureOptions.BILINEAR);
		regMenuButton = BitmapTextureAtlasTextureRegionFactory.createFromAsset(texMenuButton, act.getAssets(), "gfx/menubutton.png", 0, 0);
		texMenuButton.load();

		texPlayAgainButton = new BitmapTextureAtlas(act.getTextureManager(), 64, 64, TextureOptions.BILINEAR);
		regPlayAgainButton = BitmapTextureAtlasTextureRegionFactory.createFromAsset(texPlayAgainButton, act.getAssets(), "gfx/againbutton.png", 0, 0);
		texPlayAgainButton.load();

		texSubmitButton = new BitmapTextureAtlas(act.getTextureManager(), 64, 64, TextureOptions.BILINEAR);
		regSubmitButton = BitmapTextureAtlasTextureRegionFactory.createFromAsset(texSubmitButton, act.getAssets(), "gfx/submitbutton.png", 0, 0);
		texSubmitButton.load();
		
		texSoundOff = new BitmapTextureAtlas(act.getTextureManager(), 64, 64, TextureOptions.BILINEAR);
		regSoundOff = BitmapTextureAtlasTextureRegionFactory.createFromAsset(texSoundOff, act.getAssets(), "gfx/soundoff.png", 0, 0);
		texSoundOff.load();
		
		texSoundOn = new BitmapTextureAtlas(act.getTextureManager(), 64, 64, TextureOptions.BILINEAR);
		regSoundOn = BitmapTextureAtlasTextureRegionFactory.createFromAsset(texSoundOn, act.getAssets(), "gfx/soundon.png", 0, 0);
		texSoundOn.load();

		


		texPopup = new BitmapTextureAtlas(act.getTextureManager(), 256, 256, TextureOptions.BILINEAR);
		regPopup = BitmapTextureAtlasTextureRegionFactory.createFromAsset(texPopup, act.getAssets(), "gfx/popup.png", 0, 0);
		texPopup.load();

		texGamePopup = new BitmapTextureAtlas(act.getTextureManager(), 512, 256, TextureOptions.BILINEAR);
		regGamePopup = BitmapTextureAtlasTextureRegionFactory.createFromAsset(texGamePopup, act.getAssets(), "gfx/gamepopup.png", 0, 0);
		texGamePopup.load();
	}
}
