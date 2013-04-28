package river.raid.game;

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.util.modifier.IModifier;




/*
	Pierwsza scena gry - intro
*/
public class SplashScene extends Scene {
	BaseActivity activity;
	Sprite logo2lines;
	Text title1, title2;
	public SplashScene() {
		setBackground(new Background(255, 255, 255));
		activity = BaseActivity.getSharedInstance();
		title1 = new Text(0, 0, activity.splashFont, activity.getString(R.string.title_1), activity.getVertexBufferObjectManager());
		title2 = new Text(0, 0, activity.splashFont, activity.getString(R.string.title_2), activity.getVertexBufferObjectManager());

		title1.setPosition(activity.mCamera.getWidth(), activity.mCamera.getHeight() / 2 - title1.getHeight() / 2);
		title2.setPosition(activity.mCamera.getWidth(), activity.mCamera.getHeight() / 2 - title1.getHeight() / 2);
		attachChild(title1);

		title1.registerEntityModifier(new MoveXModifier(0.4f, title1.getX(), activity.mCamera.getWidth() / 2 - title1.getWidth() / 2));

		loadResources();

	}

	void loadResources() {
		DelayModifier dMod = new DelayModifier(2, new IEntityModifierListener() {

			@Override
			public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
				detachChild(title1);
				attachChild(title2);
				title2.registerEntityModifier(new MoveXModifier(0.4f, title2.getX(), activity.mCamera.getWidth() / 2 - title2.getWidth() / 2) {

					@Override
					protected void onModifierFinished(IEntity pItem) {
						DelayModifier mod2 = new DelayModifier(2, new IEntityModifierListener() {

							@Override
							public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
								// TODO Auto-generated method stub
							}

							@Override
							public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
								activity.setCurrentScene(new MainMenuScene());
							}
						});

						registerEntityModifier(mod2);

					}

				});
				// activity.setCurrentScene(new MainMenuScene());
			}
		});

		registerEntityModifier(dMod);
	}

}
