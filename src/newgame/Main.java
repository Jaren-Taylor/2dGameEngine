package newgame;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import static newgame.EnemyMembers.BlueFlameInstance;
import static newgame.EnemyMembers.EnemyPlantInstance;
import static newgame.EnemyMembers.GrimInstance;
import static newgame.EnemyMembers.PurpleDragonInstance;
import static newgame.EnemyMembers.RedFlameInstance;
import static newgame.EnemyMembers.WormInstance;
import java.util.Scanner;

public class Main extends JFrame implements Runnable {

    public static int alpha = 0xFFFE00DC;
    private int titleState;

    private Canvas canvas = new Canvas();
    private RenderHandler renderer;
    //private BufferedImage testImage;
    //private Sprite testSprite;
    private SpriteSheet sheet;

    private Rectangle testRectangle = new Rectangle(30, 30, 100, 100);
    private Tiles tiles, titleTiles;
    private Map map;
    private GameObject[] objects;
    private KeyBoardListener keyListener = new KeyBoardListener(this);
    private Player player, player2;
    private MouseEventListener mouseListener = new MouseEventListener(this);

    private String dir = System.getProperty("user.dir");
    private final int GAME_ZOOM = 3;

    private int[][] playerCoords;

    public Main() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(0, 0, 1006, 835);
        setLocationRelativeTo(null);
        setTitle("Lost Duality");
        add(canvas);
        setVisible(true);
        setResizable(false);
        canvas.createBufferStrategy(3);
        renderer = new RenderHandler(getWidth(), getHeight());

        playerCoords = new int[11][2];

        //loads assets
        BufferedImage sheetImage = loadImage("Tiles1.png");
        sheet = new SpriteSheet(sheetImage);
        sheet.loadSprites(16, 16);

        BufferedImage playerSheetImage = loadImage("Character1.png");
        BufferedImage player2SheetImage = loadImage("Character2.png");
        SpriteSheet playerSheet = new SpriteSheet(playerSheetImage);
        SpriteSheet player2Sheet = new SpriteSheet(player2SheetImage);
        playerSheet.loadSprites(16, 24);
        player2Sheet.loadSprites(16, 24);

        //testing animated
        AnimatedSprite playerAnimations = new AnimatedSprite(playerSheet, 3);
        AnimatedSprite player2Animations = new AnimatedSprite(player2Sheet, 3);

        tiles = new Tiles(new File(dir + "\\src\\newgame\\Tiles"), sheet);
        map = new Map(new File(dir + "\\src\\newgame\\Map"), tiles);
        //testImage = loadImage("GrassTile.png");
        //testSprite = sheet.getSprite(4,1);
        testRectangle.generateGraphics(2, 1223);
        objects = new GameObject[2];
        player = new Player(playerAnimations, map, GAME_ZOOM, false);
        player2 = new Player(player2Animations, map, GAME_ZOOM, true);
        objects[0] = player;
        objects[1] = player2;
        //add listeners
        canvas.addKeyListener(keyListener);
        canvas.addMouseListener(mouseListener);
        canvas.addMouseMotionListener(mouseListener);

        BufferedImage titleSheetImage = loadImage("TitleSheet.png");
        SpriteSheet titleSheet = new SpriteSheet(titleSheetImage);
        titleSheet.loadSprites(100, 80);
        titleTiles = new Tiles(new File(dir + "\\src\\newgame\\TitleTiles"), titleSheet);
        titleState = 0;
    }

    public void update() {
        for (int i = 0; i < objects.length; i++) {
            objects[i].update(this);
        }

    }

    private BufferedImage loadImage(String path) {
        try {
            BufferedImage loadedImage = ImageIO.read(Main.class.getResource(path));
            BufferedImage formattedImage = new BufferedImage(loadedImage.getWidth(), loadedImage.getHeight(), BufferedImage.TYPE_INT_RGB);
            formattedImage.getGraphics().drawImage(loadedImage, 0, 0, null);
            return formattedImage;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    public void render() {

        int mouseX, mouseY;
        BufferStrategy bufferStrategy = canvas.getBufferStrategy();
        Graphics g = bufferStrategy.getDrawGraphics();
        super.paint(g);
        //renderer.renderSprite(testSprite,0,0,5,5);
        if (titleState == 0) {
            titleTiles.renderTile(0, renderer, 0, 0, 10, 10);
            mouseX = MouseInfo.getPointerInfo().getLocation().x - getX();
            mouseY = MouseInfo.getPointerInfo().getLocation().y - getY();
            if ((mouseX >= 303) && (mouseX <= 653) && (mouseY >= 552) && (mouseY <= 592)) {
                titleTiles.renderTile(2, renderer, 0, 0, 10, 10);
            } else if ((mouseX >= 303) && (mouseX <= 653) && (mouseY >= 622) && (mouseY <= 662)) {
                titleTiles.renderTile(3, renderer, 0, 0, 10, 10);
            } else if ((mouseX >= 403) && (mouseX <= 553) && (mouseY >= 692) && (mouseY <= 732)) {
                titleTiles.renderTile(4, renderer, 0, 0, 10, 10);
            } else {
                titleTiles.renderTile(1, renderer, 0, 0, 10, 10);
            }
        } else {
            tiles.renderTile(3, renderer, 0, 0, GAME_ZOOM, GAME_ZOOM);
            map.render(renderer, GAME_ZOOM, GAME_ZOOM);

            for (int i = 0; i < objects.length; i++) {
                objects[i].render(renderer, GAME_ZOOM, GAME_ZOOM);
            }
        }

//            renderer.renderSprite(playerAnimations, 30, 30,xZoom,yZoom);
        //renderer.renderRectangle(testRectangle,1,1);
        renderer.render(g);

        g.dispose();
        bufferStrategy.show();
        renderer.clear();
    }

    public void run() {
        BufferStrategy bufferStrategy = canvas.getBufferStrategy();
        int i = 0;
        int x = 0;
        long lastTime = System.nanoTime(); // long store more data 2^63
        double nanoSecondConversion = 1000000000.0 / 60;//frames per second
        double changeSeconds = 0;

        while (titleState == 0) {
            render();
        }
        while (true) {
            long now = System.nanoTime();
            changeSeconds += (now - lastTime) / nanoSecondConversion;

            while (changeSeconds >= 1) {
                update();
                changeSeconds = 0;

            }
            render();
            lastTime = now;
        }

    }

    public void paint(Graphics g) {
        super.paint(g);

    }

    public void handleTitleScreen() {

    }

    public int[][] getCoords() {
        return playerCoords;
    }

    public void setCoords(int x, int y) {
        for (int i = playerCoords.length - 1; i > 0; i--) {
            playerCoords[i][0] = playerCoords[i - 1][0];
            playerCoords[i][1] = playerCoords[i - 1][1];
        }
        playerCoords[0][0] = x;
        playerCoords[0][1] = y;
    }

    public int getTitleState() {
        return titleState;
    }

    public void setTitleState(int newState) {
        titleState = newState;
    }

    public KeyBoardListener getKeyListener() {
        return keyListener;

    }

    public MouseEventListener getMouseListener() {
        return mouseListener;

    }

    public RenderHandler getRenderer() {
        return renderer;
    }

    //BattleSystem stuff
    static String BF_URL = "C:\\Users\\jman4\\OneDrive\\Documents\\NetBeansProjects\\Engine V8\\Engine\\extra sprites\\Enemies-20190403T005226Z-001\\Enemies";
    static String EP_URL = "";
    static String G_URL = "";
    static String PD_URL = "";
    static String RF_URL = "";
    static String W_URL = "";

    public static BattleSystemGUI GUI = new BattleSystemGUI("Battle Screen");
    public static Scanner sc = new Scanner(System.in);
    public static CastMembers target;//target for a move to be executed
    public static int TurnCounter = 0;
    public static boolean battling = true;
    public static int numOfSteps = 0;
    public static CastMembers currEnemy;//current enemy in battle
    public static CastMembers moveableChar;

    //Instances of MCs
    public static CastMembers LUCIA_INSTANCE = CastMembers.Lucia;
    public static CastMembers ROURKE_INSTANCE = CastMembers.Rourke;

    public static CastMembers[] enemyArray = {BlueFlameInstance,
        EnemyPlantInstance,
        GrimInstance,
        PurpleDragonInstance,
        RedFlameInstance,
        WormInstance};

    //overloaded fns for battleSystemPrototype, two target parameters signifies a basic atk.
    public static void LuciasAtks(String choice, CastMembers target) {
        switch (choice) {
            case "1":
                CastMembers.rushDown(target);
                break;
            case "2":
                CastMembers.Lucretia(target);
                break;
            case "3":
                CastMembers.Perpetuation(target);
                break;
            case "4":
                CastMembers.Sacrifice(target);
                break;

        }

    }

    public static void LuciasAtks(String choice, CastMembers target, CastMembers target2) {
        switch (choice) {
            case "5":
                CastMembers.physAtk(LUCIA_INSTANCE, target);
                break;

        }
    }

    public static void LuciasAtks(String choice) {
        switch (choice) {
            case "6":
                CastMembers.defend(LUCIA_INSTANCE);

        }
    }

    //Rourkes attacks
    public static void RourkesAtks(String choice, CastMembers target) {
        switch (choice) {
            case "1":
                CastMembers.Solemn(target);
                break;

            case "3":
                CastMembers.cast(target);
                break;
            case "4":
                CastMembers.TheEnd(target);
                break;

        }

    }

    public static void RourkesAtks(String choice) {
        switch (choice) {

            case "6":
                CastMembers.defend(ROURKE_INSTANCE);

        }
    }

    public static void RourkesAtks(String choice, CastMembers target, CastMembers target2) {
        switch (choice) {
            case "2":
                CastMembers.Expulsion(target, target2);//target will be chosen to nullify stats changes, target 2 will be the attack target
                break;

            case "5":
                CastMembers.physAtk(ROURKE_INSTANCE, target2);
                break;

        }
    }

    public static CastMembers chooseTarget() {
        String input = "";
        System.out.println("Choose your target for this move, think about the consequences though!\n"
                + "select: \n"
                + "1 for Lucia\n"
                + "2 for Rourke\n"
                + "3 for the current enemy\n");

        input = sc.next();
        switch (input) {
            case "1":
                target = LUCIA_INSTANCE;
                break;

            case "2":
                target = ROURKE_INSTANCE;
                break;

            case "3":
                target = currEnemy;
                break;

        }
        return target;
    }

    public static void startBattle() {

        if (numOfSteps == 10040 ) {

            numOfSteps = 0;
            battling = true;

        }
    }

    public static void main(String[] args) {
        Main main = new Main();
        Thread gameThread = new Thread(main);
        gameThread.start();
    }
}
