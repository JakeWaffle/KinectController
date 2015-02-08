//http://www.thinkplexx.com/blog/simple-apache-commons-cli-example-java-command-line-arguments-parsing
package com.lcsc.hackathon;

import com.lcsc.hackathon.events.EventFactory;
import org.apache.commons.cli.CommandLine;
import org.apache.log4j.Logger;

import java.io.Console;

import edu.ufl.digitalworlds.j4k.J4KSDK;
import edu.ufl.digitalworlds.j4k.Skeleton;

public class Main extends J4KSDK {
    static Logger log = Logger.getRootLogger();

    private CommandLine arguments;
    
    private EsperHandler eHandler;
    private ConfigParser cParser;
    private EventFactory eFactory;
    
    Skeleton skeletons[];
    
    public static void main(String[] args) {
        Main main = new Main(args);
        main.run();
        
        //This has the values for the KeyEvents
        //http://docs.oracle.com/javase/7/docs/api/constant-values.html#java.awt.event.KeyEvent.VK_PAGE_DOWN
        //VK_W = 87
        //eHandler.setPattern("pat1", "select 87 as keyID from AngleRule");
        //eHandler.addListener("pat1", new KeyPress());
        
        //eHandler.setPattern("pat1", "select 87 as keyID from AngleRule");
        //eHandler.addListener("pat1", new KeyPress());
        //eHandler.sendEvent(new AngleRule(1, 1, 1));
    }
    
    public Main(String[] args) {
        this.arguments = new Cli(args).parse();
        
        this.eHandler = new EsperHandler();
        this.cParser = new ConfigParser();
        this.eFactory = this.cParser.parseConfigFile(arguments.getOptionValue("f"), this.eHandler);
    }
    
    public void run() {
        this.start(J4KSDK.NONE|J4KSDK.NONE|J4KSDK.SKELETON);
		this.skeletons = new Skeleton[6];
        
        if (this.arguments.hasOption('d')) {
            this.showViewerDialog();
        }
        
		Console console = System.console();
        boolean done = false;
        while (!done) {
            String input = console.readLine("Enter quit: ");
            if (input.equals("quit")) {
                done = true;
            }
        }
        System.out.println("Goodbye");
		this.stop();
    }
    
    @Override
	public void onSkeletonFrameEvent(boolean[] skeleton_tracked, float[] positions, float[] orientations, byte[] joint_status) {
		for (int i=0; i<this.skeletons.length;i++){
			this.skeletons[i]=Skeleton.getSkeleton(i,skeleton_tracked,positions, orientations, joint_status,this);
			if (this.skeletons[i].isTracked()){
				this.eFactory.getEventData(this.skeletons[i], this.eHandler);
			}
		}
	}
    
    @Override
	public void onColorFrameEvent(byte[] color_frame) {
		//We are not using this!
	}

	@Override
	public void onDepthFrameEvent(short[] depth_frame, byte[] body_index, float[] xyz, float[] uv) {
		//We are not using this!
	}
}