package com.coutinhodeveloper;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/** Created by Guilherme Coutinho
 *  on 11/09/2018
 */

public class FlappyBird extends ApplicationAdapter {

    //classe SpriteBatch é usada para criar animações com imagens
    private SpriteBatch batch;
    private Texture passaro;
    private Texture fundo;

    //atributos de configuração do jogo
    private int movimento=0;
    private int larguraDispositivo;
    private int alturaDispositivo;




	
	@Override
	public void create () {

	    batch = new SpriteBatch();
	    passaro = new Texture("passaro1.png");
        fundo = new Texture("fundo.png");

        larguraDispositivo = Gdx.graphics.getWidth();
        alturaDispositivo = Gdx.graphics.getHeight();



	}

	@Override
	public void render () {

	    movimento ++;
	    batch.begin();

	    batch.draw(fundo,0,0,larguraDispositivo,alturaDispositivo);
	    batch.draw(passaro, movimento, 400);

	    batch.end();


	}

}
