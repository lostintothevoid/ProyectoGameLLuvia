package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class Lluvia {
	//private Array<Rectangle> rainDropsPos;
	//private Array<Integer> rainDropsType;
    private Array <Gota> gotas;
	private long lastDropTime;
    //private Texture gotaBuena;
    //private Texture gotaMala;
    //private Sound dropSound;
    private Music rainMusic;
	   
    public Lluvia() {
		rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));
	}
	
	public void crear() {
		gotas = new Array <Gota> ();
		crearGotaDeLluvia();
	    rainMusic.setLooping(true);
	    rainMusic.play();
	}
	
	private void crearGotaDeLluvia() {
	      if (MathUtils.random(1,10)<5) {	 	  
	    	GotaAzul raindrop = new GotaAzul();
	      	gotas.add(raindrop);
	      }else{ 
	    	GotaRoja raindrop = new GotaRoja();
	      	gotas.add(raindrop);
	      }	
	      
	      lastDropTime = TimeUtils.nanoTime();
	   }
	
   public boolean actualizarMovimiento(Tarro tarro) {
	   // generar gotas de lluvia 
	   if(TimeUtils.nanoTime() - lastDropTime > 100000000) crearGotaDeLluvia();
	  
	   
	   // revisar si las gotas cayeron al suelo o chocaron con el tarro
	   for (int i=0; i < gotas.size; i++ ) {
		  Gota raindrop = gotas.get(i);
		  
		  //cae al suelo y se elimina
	      if(raindrop.actualizarPosicion() == false) {
	    	  gotas.removeIndex(i);
	      }
	      
	      if(raindrop.choca(tarro)) {
	    	  gotas.removeIndex(i);
	      }
	      
	      if (tarro.getVidas() <= 0) {
	    	  return false; //se queda sin vidas
	      } 
	   }
	   return true;
   }
   
   public void actualizarDibujoLluvia(SpriteBatch batch) { 
	   
	  for (int i=0; i < gotas.size; i++ ) {		  
	      Gota raindrop = gotas.get(i);
	      raindrop.dibujar(batch);
	   }
   }
   public void destruir() {

      for(int i=0; i < gotas.size; i++ ) {
    	  gotas.get(i).dispose();
      }
	  
	  rainMusic.dispose();
   }
   public void pausar() {
	  rainMusic.stop();
   }
   public void continuar() {
	  rainMusic.play();
   }
   
}
