package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

public class Ufo {
    public Texture texture;
    public Texture laserTexture;
    public Vector2 position;
    public Vector2 laserPosition;
    public Sprite sprite;
    public Sprite laserSprite;
    public boolean alive = true;
    public static float xSpeed = 200;
    public static float ySpeed = 1000;
    public static float laserSpeed = 50;
    public int direction = 1;
    public Random random;
    public int currentRandomInt;

    public Ufo(Texture texture, Vector2 position, Texture laserTexture) {
        this.texture = texture;
        this.laserTexture = laserTexture;
        this.position = position;
        sprite = new Sprite(texture);
        laserSprite = new Sprite(laserTexture);
        sprite.setScale(1);
        sprite.setSize(100, 50);
        laserSprite.setScale(1);
        laserSprite.setSize(10, 60);
        laserSprite.rotate90(false);
        laserPosition = new Vector2(position.x, position.y);
        random = new Random();
        currentRandomInt = random.nextInt(4) + 2;
    }

    public void draw(SpriteBatch spriteBatch) {
        update();
        if (this.alive) {
            sprite.setPosition(position.x, position.y);
            sprite.draw(spriteBatch);
        }
        laserSprite.setPosition(laserPosition.x, laserPosition.y);
        laserSprite.draw(spriteBatch);
    }

    public void update() {
        if (laserPosition.y + laserSprite.getHeight() / 2 <= 0 && this.alive) {
            laserPosition.y = sprite.getY();
            laserPosition.x = sprite.getX();
            currentRandomInt = random.nextInt(4) + 2;
        }
        laserPosition.y -= laserSpeed * Gdx.graphics.getDeltaTime() * currentRandomInt;
        position.x += xSpeed * Gdx.graphics.getDeltaTime() * direction;
    }
}
