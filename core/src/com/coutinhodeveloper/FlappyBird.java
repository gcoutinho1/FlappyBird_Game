package com.coutinhodeveloper;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class FlappyBird extends ApplicationAdapter {

    //classe SpriteBatch é usada para criar animações com imagens

    private SpriteBatch batch;
    private Texture passaro;



	
	@Override
	public void create () {

	    batch = new SpriteBatch();
	    passaro = new Texture("passaro1.png");




	}

	@Override
	public void render () {

	    batch.begin();


        batch.draw(passaro, 0, 0);

	    batch.end();


	}

}
