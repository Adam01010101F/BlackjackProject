/* 
 * To change this license header, choose License Headers in Project Properties. 
 * To change this template file, choose Tools | Templates 
 * and open the template in the editor. 
 */ 
package mygame.gameState; 
 
import com.jme3.app.Application; 
import com.jme3.app.state.AbstractAppState; 
import com.jme3.app.SimpleApplication; 
import com.jme3.app.state.AppStateManager; 
import com.jme3.asset.AssetManager; 
import com.jme3.light.DirectionalLight; 
import com.jme3.material.Material; 
import com.jme3.math.FastMath; 
import com.jme3.math.Vector3f; 
import com.jme3.scene.Geometry; 
import com.jme3.scene.Node; 
import com.jme3.scene.Spatial; 
import com.jme3.scene.shape.Box; 
import com.simsilica.lemur.Axis; 
import com.simsilica.lemur.Button; 
import com.simsilica.lemur.Command; 
import com.simsilica.lemur.Container; 
import com.simsilica.lemur.FillMode; 
import com.simsilica.lemur.GuiGlobals; 
import com.simsilica.lemur.Label; 
import com.simsilica.lemur.component.BorderLayout; 
import com.simsilica.lemur.component.BoxLayout; 
import com.simsilica.lemur.style.BaseStyles; 
 
 
/** 
 * 
 * @author adam 
 */ 
public class playState extends AbstractAppState { 
    private final Node rootNode; 
    private final Node guiNode; 
    private final Node localRootNode = new Node("main game"); 
    private final AssetManager assetManager; 
     
    protected Spatial card; 
    protected Spatial pokerChip1; 
    protected Spatial pokerChip2; 
    protected Spatial pokerChip3; 
    protected Spatial pokerChip4; 
     
     
    public playState(SimpleApplication app){ 
            rootNode  =  app.getRootNode(); 
            guiNode  =  app.getGuiNode(); 
            assetManager = app.getAssetManager(); 
    } 
     
    @Override 
    public void initialize(AppStateManager stateManager, Application app){ 
        super.initialize(stateManager, app); 
        rootNode.attachChild(localRootNode); 
         
         
        //flyCam.setEnabled(paused); 
        //setDisplayFps(false); 
        //setDisplayStatView(false); 
         
        GuiGlobals.initialize((Application) app); 
        BaseStyles.loadGlassStyle(); 
        GuiGlobals.getInstance().getStyles().setDefaultStyle("glass"); 
         
        Container mainWindow = new Container(new BorderLayout()); 
        guiNode.attachChild(mainWindow); 
        guiNode.scale(3.0f); 
        mainWindow.setLocalTranslation(30, 100, 0); 
        //betWindow.setLocalTranslation(380, 300,0); 
         
        Container actWinMain = new Container(new BorderLayout()); 
        Container actionWindow = new Container(new BoxLayout(Axis.X, FillMode.Even)); 
        actWinMain.addChild(new Label("Actions"), BorderLayout.Position.North); 
        actWinMain.addChild(actionWindow, BorderLayout.Position.Center); 
        mainWindow.addChild(actWinMain, BorderLayout.Position.West); 
        Button hit = actionWindow.addChild(new Button("HIT")); 
        Button stand = actionWindow.addChild(new Button("STAND")); 
        Button split = actionWindow.addChild(new Button("SPLIT")); 
        Button dbl = actionWindow.addChild(new Button("DOUBLE")); 
         
        Container wallet  =  new Container (new BorderLayout()); 
        wallet.addChild(new Label("Wallet"), BorderLayout.Position.North); 
        Label walNum = wallet.addChild(new Label("$ 10,000"), BorderLayout.Position.South); 
        wallet.setLocalTranslation(0, 340, 0); 
        guiNode.attachChild(wallet); 
         
        Container betWinMain =  new Container(new BorderLayout()); 
        Container betWindow = new Container(new BoxLayout(Axis.X, FillMode.Even)); 
        betWinMain.addChild(betWindow, BorderLayout.Position.Center); 
        betWinMain.addChild(new Label("Bet"), BorderLayout.Position.North); 
        mainWindow.addChild(betWinMain, BorderLayout.Position.East); 
        Button fiveK = betWindow.addChild(new Button("$ 5,000")); 
        Button tenK = betWindow.addChild(new Button("$ 10,000")); 
        Button ofK = betWindow.addChild(new Button("$ 15,000")); 
        hit.addClickCommands(new Command<Button>(){ 
            @Override 
            public void execute(Button source){ 
                System.out.println("This world is yours."); 
            } 
        }); 
        fiveK.addClickCommands(new Command<Button>(){ 
           @Override 
           public void execute(Button source){ 
               //String temp = "$ "; 
               //String digits = CharMatcher.DIGIT.retainFrom(walNum.getText); 
           } 
            
        }); 
         
        
         
         
       createTable(); 
       createLight(); 
         
        Node pivot = new Node("pivot"); 
        rootNode.attachChild(pivot); 
         
        String cardName = "Textures/Cards/"; 
        cardName = cardName.concat("2_of_spades.png"); 
        card = assetManager.loadModel("Models/basicCard.j3o"); 
        Material cardMat = assetManager.loadMaterial("Materials/CardMat.j3m"); 
        cardMat.setTexture("ColorMap2", assetManager.loadTexture(cardName)); 
        card.setMaterial(cardMat); 
        card.setLocalTranslation(-1.0f, 2.5f, 0.0f); 
        pivot.attachChild(card); 
        pivot.scale(0.60f); 
    } 
     
    @Override 
    public void cleanup(){ 
        rootNode.detachChild(localRootNode); 
        super.cleanup(); 
    } 
     
        public void createTable(){ 
        Box box = new Box(15, .2f, 15); 
        Geometry tableTop =  new Geometry("the table", box); 
        tableTop.setLocalTranslation(0, 0, -5); 
        tableTop.rotate(FastMath.HALF_PI,0,0); 
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md"); 
        mat.setTexture("ColorMap", assetManager.loadTexture("Textures/tabletop-casino.JPG")); 
        tableTop.setMaterial(mat); 
        tableTop.scale(.65f); 
        rootNode.attachChild(tableTop); 
         
         
    } 
     
    public void createLight(){ 
         DirectionalLight sun = new DirectionalLight(); 
        sun.setDirection(new Vector3f(-0.1f, -0.7f, -1.0f)); 
        rootNode.addLight(sun); 
    } 
     
    public void createGui(){ 
         
    } 
//        public Spatial createChip(){
//          pokerChip1 = assetManager.loadModel("Models/PokerChip.j3o");
//          Material pokerMat1 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
//          pokerMat1.setTexture("ColorMap", assetManager.loadTexture("Textures/green.jpg"));
//          pokerChip1.setMaterial(pokerMat1);
//          pokerChip1.setLocalTranslation(-5.0f, 0.0f, 0.0f);
//          pokerChip1.rotate(-5.0f,0.0f,0.0f);
//          pivot.attachChild(pokerChip1);
//        
//          pokerChip2 = assetManager.loadModel("Models/PokerChip.j3o");
//          Material pokerMat2 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
//          pokerMat2.setTexture("ColorMap", assetManager.loadTexture("Textures/black.jpg"));
//          pokerChip2.setMaterial(pokerMat2);
//          pokerChip2.setLocalTranslation(-2.5f, 0.0f, 0.0f);
//          pokerChip2.rotate(-5.0f,0.0f,0.0f);
//          pivot.attachChild(pokerChip2);
//            
//          pokerChip3 = assetManager.loadModel("Models/PokerChip.j3o");
//          Material pokerMat3 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
//          pokerMat3.setTexture("ColorMap", assetManager.loadTexture("Textures/red.jpg"));
//          pokerChip3.setMaterial(pokerMat3);
//          pokerChip3.setLocalTranslation(0.0f, 0.0f, 0.0f);
//          pokerChip3.rotate(-5.0f,0.0f,0.0f);
//          pivot.attachChild(pokerChip3);
//        
//          pokerChip4 = assetManager.loadModel("Models/PokerChip.j3o");
//          Material pokerMat4 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
//          pokerMat4.setTexture("ColorMap", assetManager.loadTexture("Textures/blue.jpg"));
//          pokerChip4.setMaterial(pokerMat4);
//          pokerChip4.setLocalTranslation(2.5f, 0.0f, 0.0f);
//          pokerChip4.rotate(-5.0f,0.0f,0.0f);
//          pivot.attachChild(pokerChip4);
//    }
} 