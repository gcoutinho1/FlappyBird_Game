package com.coutinhodeveloper;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

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
    private Texture gameOver;
    private Random numeroRng;
    private BitmapFont fonte;
    private BitmapFont mensagemRestart;
    private Circle passaroCirculo;
    private Rectangle retanguloCanoTopo;
    private Rectangle retanguloCanoBaixo;
    // private ShapeRenderer shape;

    //atributos de configurações do jogo
    private float larguraDispositivo;
    private float alturaDispositivo;
    private int estadoJogo=0;
    private int pontuacao=0;
    private float variacao = 0;
    private float velocidadeQueda=0;
    private float posicaoInicialVertical;
    private float posicaoMovimentoCanoHorizontal;
    private float espacoEntreCanos;
    private float deltaTime;
    private float alturaRngCanos;
    private boolean fezPonto;

    // ajuste de resoluções
    private OrthographicCamera camera;
    private Viewport viewport;
    private final float VIRTUAL_WIDTH = 768;
    private final float VIRTUAL_HEIGHT = 1024;



	
	@Override
	public void create () {

	    batch = new SpriteBatch();
	    numeroRng = new Random();
	    passaroCirculo = new Circle();
	    /*retanguloCanoBaixo = new Rectangle();
	    retanguloCanoTopo = new Rectangle();
	    shape = new ShapeRenderer(); */
	    fonte = new BitmapFont();
	    fonte.setColor(Color.BLUE);
	    fonte.getData().setScale(6);

	    mensagemRestart = new BitmapFont();
	    mensagemRestart.setColor(Color.BLUE);
	    mensagemRestart.getData().setScale(3);



	    passaros = new Texture[3];
	    passaros[0] = new Texture("passaro1.png");
	    passaros[1] = new Texture("passaro2.png");
	    passaros[2] = new Texture("passaro3.png");

        fundo = new Texture("fundo.png");
        canoBaixo = new Texture("cano_baixo.png");
        canoTopo = new Texture("cano_topo.png");
        gameOver = new Texture("game_over.png");

        // Cfg de resoluções
        camera = new OrthographicCamera();
        camera.position.set(VIRTUAL_WIDTH / 2, VIRTUAL_HEIGHT / 2, 0);
        viewport = new StretchViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT, camera);


        larguraDispositivo = VIRTUAL_WIDTH;   //Gdx.graphics.getWidth()
        alturaDispositivo = VIRTUAL_HEIGHT;   // Gdx.graphics.getHeight()
        posicaoInicialVertical = alturaDispositivo / 2;
        posicaoMovimentoCanoHorizontal = larguraDispositivo;
        espacoEntreCanos = 300;




	}

	@Override
	public void render () {

	    camera.update();
	    //limpar frames anteriores para melhorar desempenho do jogo
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);



        deltaTime = Gdx.graphics.getDeltaTime();
        variacao += deltaTime * 10;
        if (variacao > 2) variacao = 0;

	    if ( estadoJogo == 0){
	        if ( Gdx.input.justTouched() ){
	            estadoJogo = 1;

            }

        }else {

            velocidadeQueda++;
            if (posicaoInicialVertical > 0 || velocidadeQueda < 0)
                posicaoInicialVertical += -velocidadeQueda;

	        if (estadoJogo == 1){

                posicaoMovimentoCanoHorizontal -= deltaTime * 200;

                if (Gdx.input.justTouched()) {
                    velocidadeQueda = -15;



                }

                //verificação do movimento do cano - sair completamente da tela
                if (posicaoMovimentoCanoHorizontal < -canoTopo.getWidth()) {
                    posicaoMovimentoCanoHorizontal = larguraDispositivo;
                    alturaRngCanos = numeroRng.nextInt(400) - 200;
                    fezPonto = false;

                }

                //verificação da pontuação
                if (posicaoMovimentoCanoHorizontal < 120){
                    if ( !fezPonto ){
                        pontuacao++;
                        fezPonto = true;
                    }


                }


            }else {// gameover
                if (Gdx.input.justTouched()){

                    estadoJogo = 0;
                    pontuacao = 0;
                    velocidadeQueda = 0;
                    posicaoInicialVertical = alturaDispositivo / 2;
                    posicaoMovimentoCanoHorizontal = larguraDispositivo;

                }


            }
        }

        batch.setProjectionMatrix(camera.combined);

         batch.begin();

	    batch.draw(fundo,0,0,larguraDispositivo,alturaDispositivo);
        batch.draw(canoTopo, posicaoMovimentoCanoHorizontal,alturaDispositivo / 2 + espacoEntreCanos / 2 + alturaRngCanos); //( espacoEntreCanos / 2)
        batch.draw(canoBaixo, posicaoMovimentoCanoHorizontal,alturaDispositivo / 2 - canoBaixo.getHeight() - espacoEntreCanos / 2 + alturaRngCanos); //( espacoEntreCanos / 2)
        batch.draw(passaros [(int) variacao], 120,posicaoInicialVertical);
        fonte.draw(batch, String.valueOf(pontuacao), larguraDispositivo / 2, alturaDispositivo - 50);

        if (estadoJogo == 2){
            batch.draw(gameOver,larguraDispositivo / 2 - gameOver.getWidth() / 2,alturaDispositivo /2);
            mensagemRestart.draw(batch,"Tente de novo NOOB",larguraDispositivo / 2 - 200, alturaDispositivo / 2 - gameOver.getHeight() / 2);

        }

	    batch.end();

	    passaroCirculo.set(120 + passaros[0].getWidth() / 2, posicaoInicialVertical + passaros[0].getWidth() / 2, passaros[0].getWidth() /2);
	    retanguloCanoBaixo = new Rectangle(
                posicaoMovimentoCanoHorizontal, alturaDispositivo / 2 - canoBaixo.getHeight() - espacoEntreCanos / 2 + alturaRngCanos,
                canoBaixo.getWidth(),canoBaixo.getHeight()

        );

	    retanguloCanoTopo = new Rectangle(
	            posicaoMovimentoCanoHorizontal, alturaDispositivo / 2 + espacoEntreCanos / 2 + alturaRngCanos,
                canoTopo.getWidth(), canoTopo.getHeight()

        );

	     //Desenhar as colisões somente para visualização
        /*
        shape.begin( ShapeRenderer.ShapeType.Filled );
        shape.circle( passaroCirculo.x, passaroCirculo.y, passaroCirculo.radius );
        shape.rect(retanguloCanoBaixo.x,retanguloCanoBaixo.y,retanguloCanoBaixo.width,retanguloCanoBaixo.height);
        shape.rect(retanguloCanoTopo.x,retanguloCanoTopo.y,retanguloCanoTopo.width,retanguloCanoTopo.height);
        shape.setColor(Color.RED);
	    shape.end(); */


	    // teste de colisão
        if (Intersector.overlaps(passaroCirculo,retanguloCanoBaixo) || Intersector.overlaps(passaroCirculo,retanguloCanoTopo)
                || posicaoInicialVertical <= 0 || posicaoInicialVertical >= alturaDispositivo){
            estadoJogo = 2;

        }




	}

    @Override
    public void resize(int width, int height) {
        viewport.update(width,height);
    }
}
