package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Plane {
    public boolean destroyed;
    public Texture texture;
    public Sprite sprite;
    public Sprite laserSprite;
    public Vector2 position;
    public Vector2 laserPosition;
    public float speed = 400;
    public float laserSpeed = 1500;

    public Plane(Texture texture , Texture laserTexture) {
        this.texture = texture;
        sprite = new Sprite(texture);
        laserSprite = new Sprite(laserTexture);
        laserSprite.rotate90(false);
        laserSprite.setScale(1);
        laserSprite.setSize(40 , 60);
        sprite.setSize(80, 80);
        sprite.setScale(1);
        position = new Vector2(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2 - 270);
        laserPosition = new Vector2(0 , 10000);
    }

    public void update() {
        if(Gdx.input.isKeyPressed(Input.Keys.SPACE) && laserPosition.y > Gdx.graphics.getHeight()) {
            laserPosition.x = position.x + sprite.getWidth()/2;
            laserPosition.y = position.y + 15;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            position.x -= speed*Gdx.graphics.getDeltaTime();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            position.x += speed*Gdx.graphics.getDeltaTime();
        }
        if(position.x <= 0) position.x = 0;
        if(position.x + sprite.getWidth() >=Gdx.graphics.getWidth()) {
            position.x = Gdx.graphics.getWidth()-sprite.getWidth();
        }

        laserPosition.y += laserSpeed*Gdx.graphics.getDeltaTime();
    }

    public void draw(SpriteBatch spriteBatch) {
        update();
        sprite.setPosition(position.x, position.y);
        sprite.draw(spriteBatch);
        laserSprite.setPosition(laserPosition.x , laserPosition.y);
        laserSprite.draw(spriteBatch);
    }

}
