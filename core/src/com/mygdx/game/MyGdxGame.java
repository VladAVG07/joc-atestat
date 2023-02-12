package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;

import javax.xml.soap.Text;
import java.awt.*;
import java.util.Random;

public class MyGdxGame extends ApplicationAdapter {

    private static Texture background;
    private static Texture planeTexture;
    private static Texture ufoTexture;
    private static Texture laserTexture;
    private static Texture planeLaserTexture;
    private static Texture bossTexture;
    private static Texture bulletTexture;
    private static Sprite backgroundSprite;
    private static SpriteBatch spriteBatch;
    private static Plane plane;
    private static Ufo[] ufos;
    private static Boss boss;
    private int bossHitCounter;
    private Music backgroundMusic;
    private Sound explosion;
    private int planeHitCounter = 3;
    private BitmapFont font;

    @Override
    public void create() {
        background = new Texture(Gdx.files.internal("background.png"));
        planeTexture = new Texture(Gdx.files.internal("plane.png"));
        ufoTexture = new Texture(Gdx.files.internal("ufo.png"));
        laserTexture = new Texture(Gdx.files.internal("laser.png"));
        planeLaserTexture = new Texture(Gdx.files.internal("laserPlane.png"));
        bossTexture = new Texture(Gdx.files.internal("boss.png"));
        bulletTexture = new Texture(Gdx.files.internal("bullet.png"));
        plane = new Plane(planeTexture, planeLaserTexture);
        ufos = new Ufo[10];
        for (int i = 0; i < 10; i++) {
            int ufoSpacing = 90;
            Vector2 position = new Vector2(i* ufoSpacing, Gdx.graphics.getHeight()-200);
            ufos[i] = new Ufo(ufoTexture, position , laserTexture);
        }
        boss = new Boss(bossTexture , bulletTexture , new Vector2(Gdx.graphics.getWidth()/2f , Gdx.graphics.getHeight() - 150));
        backgroundSprite = new Sprite(background);
        spriteBatch = new SpriteBatch();
        bossHitCounter = 0;
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("music/bangarang.mp3"));
        backgroundMusic.setLooping(true);
        backgroundMusic.setVolume(0.1f);
        backgroundMusic.play();
        explosion = Gdx.audio.newSound(Gdx.files.internal("music/explosion.mp3"));
        font = new BitmapFont();
    }

    public void renderBackground() {
        backgroundSprite.draw(spriteBatch);
    }

    @Override
    public void render() {
        spriteBatch.begin();
        renderBackground();
        plane.draw(spriteBatch);
        for (int i = 0; i < 10; i++) {
            if (ufos[i].alive) {
                if (plane.laserSprite.getBoundingRectangle().overlaps(ufos[i].sprite.getBoundingRectangle())) {
                    ufos[i].alive = false;
                    plane.laserPosition.y = 100000;
                    explosion.play(0.1f);
                    break;
                }
                if(ufos[i].laserSprite.getBoundingRectangle().overlaps(plane.sprite.getBoundingRectangle())) {
                    planeHitCounter--;
                    ufos[i].laserPosition.y = ufos[i].position.y;
                }
                if(ufos[i].sprite.getBoundingRectangle().overlaps(plane.sprite.getBoundingRectangle())) {
                    Gdx.app.exit();
                }
            }
        }
        int dreaptaMax = 0;
        int stangaMax = 9;
        for(int i = 0 ; i < 10 ; i++) {
            if(ufos[i].alive) {
                if(i > dreaptaMax) dreaptaMax = i;
                if(i < stangaMax) stangaMax = i;
            }
        }
        if(ufos[dreaptaMax].position.x + ufos[dreaptaMax].sprite.getWidth()/2 > Gdx.graphics.getWidth()
        || ufos[stangaMax].position.x + ufos[stangaMax].sprite.getWidth()/2 < 0) {
            for(int i = 0 ; i < 10 ; i++) {
                ufos[i].direction *= -1;
                ufos[i].position.y -= Ufo.ySpeed * Gdx.graphics.getDeltaTime();
            }
        }
        for (int i = 0; i < 10; i++) {
                ufos[i].draw(spriteBatch);
        }
        if(plane.laserSprite.getBoundingRectangle().overlaps(boss.sprite.getBoundingRectangle())) {
            bossHitCounter++;
            explosion.play(0.1f);
            if (bossHitCounter == 3) boss.destroyed = true;
            plane.laserPosition.y = 100000;
        }
        if(boss.bulletSprite.getBoundingRectangle().overlaps(plane.sprite.getBoundingRectangle())) {
            planeHitCounter--;
            boss.bulletPosition.y = boss.position.y;
        }
        font.draw(spriteBatch , String.valueOf(planeHitCounter) , 20 , 20);
        if(planeHitCounter == 0) Gdx.app.exit();
        if(bossHitCounter == 3) {
            boss.position.y = 10000;
            boss.draw(spriteBatch);
            font.draw(spriteBatch , "FELICITARI AI CASTIGAT JOCUL" , Gdx.graphics.getWidth()/2 , Gdx.graphics.getHeight()/2);
        };
        if(!boss.destroyed) boss.draw(spriteBatch);
        spriteBatch.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        planeTexture.dispose();
        laserTexture.dispose();
        ufoTexture.dispose();
        bossTexture.dispose();
        bulletTexture.dispose();
        explosion.dispose();
        backgroundMusic.dispose();
        planeLaserTexture.dispose();
    }
}
