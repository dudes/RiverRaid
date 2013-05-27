package river.raid.game;

import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.ui.activity.SimpleBaseGameActivity;

import android.graphics.Color;
import android.graphics.Typeface;

public class FontRes {

	public Font mFont, popupFont;
	public Font jellyFont;
	public Font krashFont;
	public Font splashFont;
	
	private static FontRes instance = null;
	
	public FontRes(){
		
	}
	
	public static FontRes getInstance(){
		if(instance == null)
		{
			instance = new FontRes();
		}
		return instance;
	}
	
	public void loadRes(SimpleBaseGameActivity act){
		final ITexture fontTexture = new BitmapTextureAtlas(act.getTextureManager(), 512, 256, TextureOptions.BILINEAR);

		final ITexture fontTexture2 = new BitmapTextureAtlas(act.getTextureManager(), 512, 256, TextureOptions.BILINEAR);

		mFont = FontFactory.create(act.getFontManager(), act.getTextureManager(), 256, 256, Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 32);
		mFont.load();

		popupFont = FontFactory.create(act.getFontManager(), act.getTextureManager(), 256, 256, Typeface.create(Typeface.DEFAULT, Typeface.NORMAL), 22, Color.WHITE);
		popupFont.load();
		
		jellyFont = FontFactory.createFromAsset(act.getFontManager(), fontTexture, act.getAssets(), "fonts/JELLYBELLY.TTF", 48, true, Color.parseColor("#ffcc33"));
		jellyFont.load();

		krashFont = FontFactory.createFromAsset(act.getFontManager(), fontTexture2, act.getAssets(), "fonts/Kraash.ttf", 32, true, Color.BLACK);
		krashFont.load();

		splashFont = FontFactory.createFromAsset(act.getFontManager(), fontTexture2, act.getAssets(), "fonts/Artbrush.ttf", 48, true, Color.BLACK);
		splashFont.load();
		
	}
}
