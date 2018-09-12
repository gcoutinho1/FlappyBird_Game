package com.coutinhodeveloper;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Random;

import javax.xml.soap.Text;

/** Created by Guilherme Coutinho
 *  on 11/09/2018
 */

public class FlappyBird extends ApplicationAdapter {

    //classe SpriteBatch é usada para criar animações com imagens
    private SpriteBatch batch;
    private Texture[] passaros;
    private Texture fundo;
    private Texture canoBaixo;
    private Texture canoTopo;
    private Random numeroRng;

    //atributos de configurações do jogo
    private int larguraDispositivo;
    private int alturaDispositivo;
    private float variacao = 0;
    private float velocidadeQueda=0;
    private float posicaoInicialVertical;
    private float posicaoMovimentoCanoHorizontal;
    private float espacoEntreCanos;
    private float deltaTime;
    private float alturaRngCanos;




	
	@Override
	public void create () {

	    batch = new SpriteBatch();
	    numeroRng = new Random();
	    passaros = new Texture[3];
	    passaros[0] = new Texture("passaro1.png");
	    passaros[1] = new Texture("passaro2.png");
	    passaros[2] = new Texture("passaro3.png");

        fundo = new Texture("fundo.png");
        canoBaixo = new Texture("cano_baixo.png");
        canoTopo = new Texture("cano_topo.png");

        larguraDispositivo = Gdx.graphics.getWidth();
        alturaDispositivo = Gdx.graphics.getHeight();
        posicaoInicialVertical = alturaDispositivo / 2;
        posicaoMovimentoCanoHorizontal = larguraDispositivo;
        espacoEntreCanos = 300;




	}

	@Override
	public void render () {

	    deltaTime = Gdx.graphics.getDeltaTime();

        variacao += deltaTime * 10;
        posicaoMovimentoCanoHorizontal -= deltaTime * 200;
        velocidadeQueda++;


        if (variacao >2) variacao = 0;

        if ( Gdx.input.justTouched() ){
            velocidadeQueda = -15;

        }

        if (posicaoInicialVertical > 0 || velocidadeQueda < 0)
        posicaoInicialVertical += - velocidadeQueda;

        //verificação do movimento do cano
        if (posicaoMovimentoCanoHorizontal < - canoTopo.getWidth()){
            posicaoMovimentoCanoHorizontal = larguraDispositivo;
            alturaRngCanos = numeroRng.nextInt(400) - 200;

        }


	    batch.begin();

	    batch.draw(fundo,0,0,larguraDispositivo,alturaDispositivo);
        batch.draw(canoTopo, posicaoMovimentoCanoHorizontal,alturaDispositivo / 2 + espacoEntreCanos + alturaRngCanos); //( espacoEntreCanos / 2)
        batch.draw(canoBaixo, posicaoMovimentoCanoHorizontal,alturaDispositivo / 2 - canoBaixo.getHeight() - espacoEntreCanos + alturaRngCanos); //( espacoEntreCanos / 2)
        batch.draw(passaros [(int) variacao], 30,posicaoInicialVertical);

	    batch.end();


	}

}
