package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Boss {
    public Texture texture;
    public Texture bulletTexture;
    public Sprite sprite;
    public Sprite bulletSprite;
    public Vector2 position;
    public Vector2 bulletPosition;
    public boolean destroyed;
    public int direction =1;
    public int xSpeed = 340;
    public int bulletSpeed = 200;

    public Boss(Texture texture, Texture bulletTexture, Vector2 position) {
        this.texture = texture;
        this.bulletTexture = bulletTexture;
        this.position = position;
        this.sprite = new Sprite(texture);
        this.bulletSprite = new Sprite(bulletTexture);
        sprite.setScale(1);
        sprite.setSize(200 , 120);
        bulletSprite.setScale(1);
        bulletSprite.setSize(20 , 100);
        bulletPosition = new Vector2(position.x , position.y);
        bulletSprite.rotate90(true);
    }

    public void draw(SpriteBatch spriteBatch) {
        update();
        sprite.setPosition(position.x , position.y);
        bulletSprite.setPosition(bulletPosition.x , bulletPosition.y);
        sprite.draw(spriteBatch);
        bulletSprite.draw(spriteBatch);
    }

    public void update() {
        if (bulletPosition.y + bulletSprite.getHeight() / 2 <= 0 && !this.destroyed) {
            bulletPosition.y = sprite.getY();
            bulletPosition.x = sprite.getX();
        }
        if(position.x + sprite.getWidth()/2 <= 0 || position.x + sprite.getWidth()/2 >= Gdx.graphics.getWidth()) direction *= -1;
        bulletPosition.y -= bulletSpeed * Gdx.graphics.getDeltaTime();
        position.x += xSpeed * Gdx.graphics.getDeltaTime() * direction;
    }
}
