package com.piano;

import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Synthesizer;
import javax.swing.JOptionPane;

/**
 *
 * @author Smit BZ
 */
public class MusicaMDI {
    /** Instancia de clase principal
     * @param interfaz de piano
     * @return  sonidos digitales
    **/
    
    private static Synthesizer sys;
    private static MidiChannel canal;
    private static int instrumento = 0;
    
    /**
    * MidiSystem => Objeto que da conexion a los recursos MIDI y obtener Synthesizer y Sequencer
    * Synthesizer => Objeto que permite generar sonidos a partir de MIDI
    * MidiChannel => Objeto que permite que cada canal puede reproducir notas y cambiar de instrumento.
    * Thread => Sirve para dar el tiempo en ejecucion
    * @getSynthesizer() => Esta es una instacia de MIDI para generar sonido
    * @getChannels() => En esta instacia te permite  obtner los 16 canales de MIDI para reproducir los sonidos
    * @noteOn() => Indica que debe reproducir el sonido - Necesitas dos valores, la nota y la intensidad del sonido que es de 0 - 127
    * @sleep() => Permite dterminar el tiempo de reporducion para luego apagarse
    * @close() => Termina el programa
    */
    static{
        try{
            sys = MidiSystem.getSynthesizer();
            sys.open();
            canal = sys.getChannels()[0];
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    public static void sonidoEfecto(int nota){
        if(canal != null){
            canal.noteOn(nota, 100);
            new Thread(() ->{
                try{
                    Thread.sleep(1000);
                    canal.noteOff(nota);
                }catch(InterruptedException ignored){}
            }).start();
        }
    }
    
    public static void cambiarInstrumento(int num) {
        instrumento = num;
        if (canal != null) {
            canal.programChange(instrumento);
        }
    }
    
    public static void cerrar() {
        if (sys != null && sys.isOpen()){
            sys.close();
        }
    }
}
