package com.runescape.client;

import com.runescape.client.io.Decompression;
import com.runescape.client.io.ISAACCipher;
import com.runescape.client.io.Stream;
import com.runescape.client.io.StreamLoader;
import com.runescape.client.signlink.Signlink;
import com.runescape.client.util.*;
import com.runescape.client.util.node.Node;
import com.runescape.client.util.node.NodeList;

import java.applet.AppletContext;
import java.awt.*;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.*;

public final class Client extends RSApplet {

    private static final int DEFAULT_WIDTH = 503;
    private static final int DEFAULT_HEIGHT = 765;
    private static int anInt849;
    private static int anInt854;
    private static int anInt924;
    private static int anInt940;
    private static int nodeId = 10;
    public static int port = 43594;
    private static int webPort = 80;
    private static boolean isMembers = true;
    private static boolean lowMem;
    private static int anInt986;
    private static boolean aBoolean993;
    static final int[][] anIntArrayArray1003 = {
        {
            6798, 107, 10283, 16, 4797, 7744, 5799, 4634, 33697, 22433,
            2983, 54193
        }, {
            8741, 12, 64030, 43162, 7735, 8404, 1701, 38430, 24094, 10153,
            56621, 4783, 1341, 16578, 35003, 25239
        }, {
            25238, 8742, 12, 64030, 43162, 7735, 8404, 1701, 38430, 24094,
            10153, 56621, 4783, 1341, 16578, 35003
        }, {
            4626, 11146, 6439, 12, 4758, 10270
        }, {
            4550, 4537, 5681, 5673, 5790, 6806, 8076, 4574
        }
    };
    private static int anInt1005;
    private static final int[] anIntArray1019;
    private static int anInt1051;
    private static int drawCycle;
    private static int anInt1097;
    private static int anInt1117;
    public static Player myPlayer;
    private static int anInt1134;
    private static int anInt1142;
    private static int anInt1155;
    private static boolean displayStats;
    static int loopCycle;
    private static final String VALID_PASSWORD_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!\"\243$%^&*()-_=+[{]};:'@#~,<.>/?\\| ";
    private static int anInt1175;
    private static int anInt1188;
    static final int[] anIntArray1204 = {
        9104, 10275, 7595, 3610, 7975, 8526, 918, 38802, 24466, 10145,
        58654, 5027, 1457, 16565, 34991, 25486
    };
    private static boolean flagged;
    private static int anInt1226;
    public static int[] anIntArray1232;
    private static int anInt1288;

    static {
        anIntArray1019 = new int[99];
        int i = 0;

        for (int j = 0; j < 99; j++) {
            int l = j + 1;
            int i1 = (int) ((double) l + 300D * Math.pow(2D, (double) l / 7D));
            i += i1;
            anIntArray1019[j] = i / 4;
        }
        anIntArray1232 = new int[32];
        i = 2;

        for (int k = 0; k < 32; k++) {
            anIntArray1232[k] = i - 1;
            i += i;
        }
    }

    public static void main(String args[]) {
        try {
            System.out.println("RuneScape 2 Client (revision #317)");
            System.out.println("This project is on GitHub - https://github.com/PureCS/rs317-client");

            if (args.length != 6) {
                printUsage();
                return;
            }
            port = Integer.parseInt(args[1]);
            nodeId = Integer.parseInt(args[2]);

            switch (args[3]) {
                case "lowmem":
                    setLowMemMode();
                    break;
                case "highmem":
                    setHighMemMode();
                    break;
                default:
                    printUsage();
                    return;
            }
            isMembers = args[4].equals("members");
            
            Signlink.storeid = Integer.parseInt(args[5]);
            Signlink.startSignlink(InetAddress.getByName(args[0]));
            Client c = new Client();
            c.createClientFrame(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        } catch (NumberFormatException | UnknownHostException ex) {
        }
    }

    private static void printUsage() {
        System.out.println("Usage: <ip-address> <port> <node-id> <highmem/lowmem> <members/non-members> <store-id>");
        System.out.println("Example Usage: 127.0.0.1 43594 10 highmem members 32");
    }

    private static String getValuePostfixColoured(int value) {
        String s = String.valueOf(value);

        for (int k = s.length() - 3; k > 0; k -= 3) {
            s = s.substring(0, k) + "," + s.substring(k);
        }

        if (s.length() > 8) {
            s = "@gre@" + s.substring(0, s.length() - 8) + " million @whi@(" + s + ")";
        } else if (s.length() > 4) {
            s = "@cya@" + s.substring(0, s.length() - 4) + "K @whi@(" + s + ")";
        }
        return " " + s;
    }

    private static String getValuePostfix(int value) {
        if (value < 0x186a0) {
            return String.valueOf(value);
        }

        if (value < 0x989680) {
            return value / 1000 + "K";
        } else {
            return value / 0xf4240 + "M";
        }
    }

    private static void setHighMemMode() {
        WorldController.lowMem = false;
        Texture.lowMem = false;
        lowMem = false;
        ObjectManager.lowMem = false;
        ObjectDef.lowMem = false;
    }

    private static void setLowMemMode() {
        WorldController.lowMem = true;
        Texture.lowMem = true;
        lowMem = true;
        ObjectManager.lowMem = true;
        ObjectDef.lowMem = true;
    }

    private static String combatDiffColor(int i, int j) {
        int k = i - j;
        
        if (k < -9) {
            return "@red@";
        }
        if (k < -6) {
            return "@or3@";
        }
        if (k < -3) {
            return "@or2@";
        }
        if (k < 0) {
            return "@or1@";
        }
        if (k > 9) {
            return "@gre@";
        }
        if (k > 6) {
            return "@gr3@";
        }
        if (k > 3) {
            return "@gr2@";
        }
        if (k > 0) {
            return "@gr1@";
        } else {
            return "@yel@";
        }
    }

    private int ignoreCount;
    private long aLong824;
    private int[][] anIntArrayArray825;
    private int[] friendsNodeIDs;
    private NodeList[][][] groundArray;
    private int[] anIntArray828;
    private int[] anIntArray829;
    private volatile boolean aBoolean831;
    private Socket jagGrabSocket;
    private int loginScreenState;
    private Stream aStream_834;
    private NPC[] npcArray;
    private int npcCount;
    private int[] npcIndices;
    private int anInt839;
    private int[] removeFromUpdatingPlayersList;
    private int anInt841;
    private int anInt842;
    private int anInt843;
    private String aString844;
    private int privateChatMode;
    private Stream aStream_847;
    private boolean aBoolean848;
    private int[] anIntArray850;
    private int[] anIntArray851;
    private int[] anIntArray852;
    private int[] anIntArray853;
    private int iconType;
    private int openInterfaceID;
    private int xCameraPos;
    private int zCameraPos;
    private int yCameraPos;
    private int yCameraCurve;
    private int xCameraCurve;
    private int myPrivilege;
    private final int[] currentExperience;
    private Background redStone1_3;
    private Background redStone2_3;
    private Background redStone3_2;
    private Background redStone1_4;
    private Background redStone2_4;
    private Sprite mapFlag;
    private Sprite mapMarker;
    private boolean aBoolean872;
    private final int[] anIntArray873;
    private int anInt874;
    private final boolean[] aBooleanArray876;
    private int weight;
    private MouseDetection mouseDetection;
    private volatile boolean drawFlames;
    private String reportAbuseInput;
    private int playerListIndex;
    private boolean menuOpen;
    private int anInt886;
    private String inputString;
    private final int maxPlayers;
    private final int myPlayerIndex;
    private Player[] playerArray;
    private int playerCount;
    private int[] playerIndices;
    private int anInt893;
    private int[] updatingPlayersList;
    private Stream[] cachedPlayerUpdateBuffers;
    private int anInt896;
    private int anInt897;
    private int friendsCount;
    private int friendsListLoadStatus;
    private int[][] anIntArrayArray901;
    private final int anInt902;
    private RSImageProducer backLeftIP1;
    private RSImageProducer backLeftIP2;
    private RSImageProducer backRightIP1;
    private RSImageProducer backRightIP2;
    private RSImageProducer backTopIP1;
    private RSImageProducer backVmidIP1;
    private RSImageProducer backVmidIP2;
    private RSImageProducer backVmidIP3;
    private RSImageProducer backVmidIP2_2;
    private byte[] aByteArray912;
    private int anInt913;
    private int crossX;
    private int crossY;
    private int crossIndex;
    private int crossType;
    private int plane;
    private final int[] currentLevels;
    private final long[] ignoreListAsLongs;
    private boolean loadingError;
    private final int anInt927;
    private final int[] anIntArray928;
    private int[][] anIntArrayArray929;
    private Sprite aClass30_Sub2_Sub1_Sub1_931;
    private Sprite aClass30_Sub2_Sub1_Sub1_932;
    private int iconPlayerTargetId;
    private int iconX;
    private int iconY;
    private int iconDrawHeight;
    private int anInt937;
    private int anInt938;
    private final int[] chatTypes;
    private final String[] chatNames;
    private final String[] chatMessages;
    private int anInt945;
    private WorldController worldController;
    private Background[] sideIcons;
    private int menuScreenArea;
    private int menuOffsetX;
    private int menuOffsetY;
    private int menuWidth;
    private int anInt952;
    private long aLong953;
    private boolean aBoolean954;
    private long[] friendsListAsLongs;
    private int currentSong;
    private volatile boolean drawingFlames;
    private int spriteDrawX;
    private int spriteDrawY;
    private final int[] anIntArray965 = {
        0xffff00, 0xff0000, 65280, 65535, 0xff00ff, 0xffffff
    };
    private Background titleBoxBackground;
    private Background buttonBackground;
    private final int[] anIntArray968;
    private final int[] anIntArray969;
    final Decompression[] decompressors;
    public int[] currentUserSetting;
    private boolean aBoolean972;
    private final int anInt975;
    private final int[] anIntArray976;
    private final int[] anIntArray977;
    private final int[] anIntArray978;
    private final int[] anIntArray979;
    private final int[] anIntArray980;
    private final int[] anIntArray981;
    private final int[] anIntArray982;
    private final String[] aStringArray983;
    private int anInt984;
    private int anInt985;
    private Sprite[] hitMarks;
    private int anInt988;
    private int anInt989;
    private final int[] anIntArray990;
    private final boolean aBoolean994;
    private int anInt995;
    private int anInt996;
    private int anInt997;
    private int anInt998;
    private int anInt999;
    private ISAACCipher encryption;
    private Sprite mapEdge;
    private final int anInt1002;
    private String amountOrNameInput;
    private int daysSinceLastLogin;
    private int packetLength;
    private int packetOpcode;
    private int ticksSincelastPacketReceived;
    private int anInt1010;
    private int anInt1011;
    private NodeList aClass19_1013;
    private int anInt1014;
    private int anInt1015;
    private int anInt1016;
    private boolean aBoolean1017;
    private int currentInterfaceId;
    /**
     * Minimap state.
     * 0 - Active: Clickable and viewable.
     * 1 - Locked: viewable but not clickable.
     * 2 - Blacked-out: Minimap is replaced with black background.
     */
    private int minimapState;
    private int anInt1022;
    private int loadingStage;
    private Background scrollBar1;
    private Background scrollBar2;
    private int anInt1026;
    private Background backBase1;
    private Background backBase2;
    private Background backHmid1;
    private final int[] anIntArray1030;
    private boolean aBoolean1031;
    private Sprite[] mapFunctions;
    private int baseX;
    private int baseY;
    private int anInt1036;
    private int anInt1037;
    private int loginFailures;
    private int anInt1039;
    private int anInt1040;
    private int anInt1041;
    private int dialogID;
    private final int[] maxStats;
    private final int[] defaultUserSetting;
    /**
     * Membership flag.
     * 1: member
     * 0: free
     */
    private int membershipFlag;
    private boolean aBoolean1047;
    private int anInt1048;
    private String loadingText;
    private final int[] anIntArray1052;
    private StreamLoader titleStreamLoader;
    private int flashSidebarId;
    /**
     * Denotes whether or not the player is in a multi-combat zone (subsequently
     * drawn as the skull and bones on the bottom-right of the viewport).
     * 
     * 0: not in a multi-combat zone.
     * 1: in a multi-zone combat.
     */
    private int multiCombatFlag;
    private NodeList aClass19_1056;
    private final int[] anIntArray1057;
    private final RSInterface aClass9_1059;
    private Background[] mapScenes;
    private int soundSlot;
    private final int anInt1063;
    private int friendsListAction;
    private final int[] anIntArray1065;
    private int mouseInvInterfaceIndex;
    private int lastActiveInvInterface;
    private OnDemandFetcher onDemandFetcher;
    private int anInt1069;
    private int anInt1070;
    private int anInt1071;
    private int[] anIntArray1072;
    private int[] anIntArray1073;
    private Sprite mapDotItem;
    private Sprite mapDotNPC;
    private Sprite mapDotPlayer;
    private Sprite mapDotFriend;
    private Sprite mapDotTeam;
    private int loadingPercentage;
    private boolean aBoolean1080;
    private String[] friendsList;
    private Stream inStream;
    private int anInt1084;
    private int anInt1085;
    private int activeInterfaceType;
    private int anInt1087;
    private int anInt1088;
    private int anInt1089;
    private final int[] expectedCRCs;
    private int[] menuActionCmd2;
    private int[] menuActionCmd3;
    private int[] menuActionID;
    private int[] menuActionCmd1;
    private Sprite[] headIcons;
    private int cameraPositionX;
    private int cameraPositionY;
    private int cameraPositionZ;
    private int anInt1101;
    private int anInt1102;
    private boolean tabAreaAltered;
    private int timeUntilSystemUpdate;
    private RSImageProducer aRSImageProducer_1107;
    private RSImageProducer aRSImageProducer_1108;
    private RSImageProducer aRSImageProducer_1109;
    private RSImageProducer leftFlameImageProducer;
    private RSImageProducer rightFlameImageProducer;
    private RSImageProducer aRSImageProducer_1112;
    private RSImageProducer aRSImageProducer_1113;
    private RSImageProducer aRSImageProducer_1114;
    private RSImageProducer aRSImageProducer_1115;
    private int memberWarning;
    private String aString1121;
    private Sprite compass;
    private RSImageProducer aRSImageProducer_1123;
    private RSImageProducer aRSImageProducer_1124;
    private RSImageProducer aRSImageProducer_1125;
    private final String[] atPlayerActions;
    private final boolean[] atPlayerArray;
    private final int[][][] mapRegionPalette;
    private final int[] tabInterfaceIDs = {
        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
        -1, -1, -1, -1, -1
    };
    private int anInt1131;
    private int anInt1132;
    private int menuActionRow;
    private int spellSelected;
    private int anInt1137;
    private int spellUsableOn;
    private String spellTooltip;
    private Sprite[] aClass30_Sub2_Sub1_Sub1Array1140;
    private boolean aBoolean1141;
    private Background redStone1;
    private Background redStone2;
    private Background redStone3;
    private Background redStone1_2;
    private Background redStone2_2;
    private int runEnergy;
    private boolean aBoolean1149;
    private Sprite[] crosses;
    private boolean musicEnabled;
    private Background[] aBackgroundArray1152s;
    private boolean needDrawTabArea;
    private int unreadMessages;
    public boolean loggedIn;
    private boolean canMute;
    private boolean aBoolean1159;
    private boolean aBoolean1160;
    private RSImageProducer aRSImageProducer_1163;
    private RSImageProducer aRSImageProducer_1164;
    private RSImageProducer aRSImageProducer_1165;
    private RSImageProducer aRSImageProducer_1166;
    private int daysSinceRecoveryChange;
    private RSSocket socketStream;
    private int anInt1169;
    private int minimapInt3;
    private int anInt1171;
    private long aLong1172;
    private String myUsername;
    private String myPassword;
    private boolean genericLoadingError;
    private final int[] anIntArray1177 = {
        0, 0, 0, 0, 1, 1, 1, 1, 1, 2,
        2, 2, 2, 2, 2, 2, 2, 2, 2, 2,
        2, 2, 3
    };
    private int reportAbuseInterfaceID;
    private NodeList aClass19_1179;
    private int[] anIntArray1180;
    private int[] anIntArray1181;
    private int[] anIntArray1182;
    private byte[][] aByteArrayArray1183;
    private int anInt1184;
    private int minimapInt1;
    private int anInt1186;
    private int anInt1187;
    private int invOverlayInterfaceID;
    private int[] anIntArray1190;
    private int[] anIntArray1191;
    private Stream stream;
    private int lastLoggedIp;
    private int splitPrivateChat;
    private Background invBack;
    private Background mapBack;
    private Background chatBack;
    private String[] menuActionName;
    private Sprite leftFlameSprite;
    private Sprite rightFlameSprite;
    private final int[] anIntArray1203;
    private final int[] soundIds;
    private int anInt1208;
    private int minimapInt2;
    private int anInt1210;
    private int anInt1211;
    private String promptInput;
    private int anInt1213;
    private int[][][] intGroundArray;
    private long serverSessionKey;
    private int loginScreenCursorPos;
    private final Background[] modIcons;
    private long aLong1220;
    private int tabID;
    private int iconTargetNpcId;
    private boolean inputTaken;
    private int inputDialogState;
    private int nextSong;
    private boolean songChanging;
    private final int[] anIntArray1229;
    private ClipMap[] clipMap;
    private boolean aBoolean1233;
    private int[] anIntArray1234;
    private int[] anIntArray1235;
    private int[] anIntArray1236;
    private int anInt1237;
    private int anInt1238;
    private final int[] anIntArray1240;
    private final int[] soundVolume;
    private boolean aBoolean1242;
    private int atInventoryLoopCycle;
    private int atInventoryInterface;
    private int atInventoryIndex;
    private int atInventoryInterfaceType;
    private byte[][] aByteArrayArray1247;
    private int tradeMode;
    private int anInt1249;
    private final int[] soundFinishTime;
    private int anInt1251;
    private final boolean rsAlreadyLoaded;
    private int anInt1253;
    private int anInt1254;
    private boolean welcomeScreenRaised;
    private boolean messagePromptRaised;
    private int anInt1257;
    private byte[][][] byteGroundArray;
    private int prevSong;
    private int destX;
    private int destY;
    private Sprite sprite;
    private int anInt1264;
    private int anInt1265;
    private String loginMessage1;
    private String loginMessage2;
    private int x1;
    private int y1;
    private TextDrawingArea aTextDrawingArea_1270;
    private TextDrawingArea loadingTextArea;
    private TextDrawingArea chatTextDrawingArea;
    private int anInt1275;
    private int backDialogID;
    private int anInt1278;
    private int anInt1279;
    private int[] bigX;
    private int[] bigY;
    private int itemSelected;
    private int anInt1283;
    private int anInt1284;
    private int anInt1285;
    private String selectedItemName;
    private int publicChatMode;
    private int anInt1289;

    private Client() {
        anIntArrayArray825 = new int[104][104];
        friendsNodeIDs = new int[200];
        groundArray = new NodeList[4][104][104];
        aBoolean831 = false;
        aStream_834 = new Stream(new byte[5000]);
        npcArray = new NPC[16384];
        npcIndices = new int[16384];
        removeFromUpdatingPlayersList = new int[1000];
        aStream_847 = Stream.fetch();
        aBoolean848 = true;
        openInterfaceID = -1;
        currentExperience = new int[SkillConstants.COUNT];
        aBoolean872 = false;
        anIntArray873 = new int[5];
        anInt874 = -1;
        aBooleanArray876 = new boolean[5];
        drawFlames = false;
        reportAbuseInput = "";
        playerListIndex = -1;
        menuOpen = false;
        inputString = "";
        maxPlayers = 2048;
        myPlayerIndex = 2047;
        playerArray = new Player[maxPlayers];
        playerIndices = new int[maxPlayers];
        updatingPlayersList = new int[maxPlayers];
        cachedPlayerUpdateBuffers = new Stream[maxPlayers];
        anInt897 = 1;
        anIntArrayArray901 = new int[104][104];
        anInt902 = 0x766654;
        aByteArray912 = new byte[16384];
        currentLevels = new int[SkillConstants.COUNT];
        ignoreListAsLongs = new long[100];
        loadingError = false;
        anInt927 = 0x332d25;
        anIntArray928 = new int[5];
        anIntArrayArray929 = new int[104][104];
        chatTypes = new int[100];
        chatNames = new String[100];
        chatMessages = new String[100];
        sideIcons = new Background[13];
        aBoolean954 = true;
        friendsListAsLongs = new long[200];
        currentSong = -1;
        drawingFlames = false;
        spriteDrawX = -1;
        spriteDrawY = -1;
        anIntArray968 = new int[33];
        anIntArray969 = new int[256];
        decompressors = new Decompression[5];
        currentUserSetting = new int[2000];
        aBoolean972 = false;
        anInt975 = 50;
        anIntArray976 = new int[anInt975];
        anIntArray977 = new int[anInt975];
        anIntArray978 = new int[anInt975];
        anIntArray979 = new int[anInt975];
        anIntArray980 = new int[anInt975];
        anIntArray981 = new int[anInt975];
        anIntArray982 = new int[anInt975];
        aStringArray983 = new String[anInt975];
        anInt985 = -1;
        hitMarks = new Sprite[20];
        anIntArray990 = new int[5];
        aBoolean994 = false;
        anInt1002 = 0x23201b;
        amountOrNameInput = "";
        aClass19_1013 = new NodeList();
        aBoolean1017 = false;
        currentInterfaceId = -1;
        anIntArray1030 = new int[5];
        aBoolean1031 = false;
        mapFunctions = new Sprite[100];
        dialogID = -1;
        maxStats = new int[SkillConstants.COUNT];
        defaultUserSetting = new int[2000];
        aBoolean1047 = true;
        anIntArray1052 = new int[151];
        flashSidebarId = -1;
        aClass19_1056 = new NodeList();
        anIntArray1057 = new int[33];
        aClass9_1059 = new RSInterface();
        mapScenes = new Background[100];
        anInt1063 = 0x4d4233;
        anIntArray1065 = new int[7];
        anIntArray1072 = new int[1000];
        anIntArray1073 = new int[1000];
        aBoolean1080 = false;
        friendsList = new String[200];
        inStream = Stream.fetch();
        expectedCRCs = new int[9];
        menuActionCmd2 = new int[500];
        menuActionCmd3 = new int[500];
        menuActionID = new int[500];
        menuActionCmd1 = new int[500];
        headIcons = new Sprite[20];
        tabAreaAltered = false;
        aString1121 = "";
        atPlayerActions = new String[5];
        atPlayerArray = new boolean[5];
        mapRegionPalette = new int[4][13][13];
        anInt1132 = 2;
        aClass30_Sub2_Sub1_Sub1Array1140 = new Sprite[1000];
        aBoolean1141 = false;
        aBoolean1149 = false;
        crosses = new Sprite[8];
        musicEnabled = true;
        needDrawTabArea = false;
        loggedIn = false;
        canMute = false;
        aBoolean1159 = false;
        aBoolean1160 = false;
        anInt1171 = 1;
        myUsername = "mopar";
        myPassword = "bob";
        genericLoadingError = false;
        reportAbuseInterfaceID = -1;
        aClass19_1179 = new NodeList();
        anInt1184 = 128;
        invOverlayInterfaceID = -1;
        stream = Stream.fetch();
        menuActionName = new String[500];
        anIntArray1203 = new int[5];
        soundIds = new int[50];
        anInt1210 = 2;
        anInt1211 = 78;
        promptInput = "";
        modIcons = new Background[2];
        tabID = 3;
        inputTaken = false;
        songChanging = true;
        anIntArray1229 = new int[151];
        clipMap = new ClipMap[4];
        aBoolean1233 = false;
        anIntArray1240 = new int[100];
        soundVolume = new int[50];
        aBoolean1242 = false;
        soundFinishTime = new int[50];
        rsAlreadyLoaded = false;
        welcomeScreenRaised = false;
        messagePromptRaised = false;
        loginMessage1 = "";
        loginMessage2 = "";
        backDialogID = -1;
        anInt1279 = 2;
        bigX = new int[4000];
        bigY = new int[4000];
        anInt1289 = -1;
    }

    private void stopMidi() {
        Signlink.midifade = 0;
        Signlink.midiName = "stop";
    }

    private void connectServer() {
        /*      int j = 5;
         expectedCRCs[8] = 0;
         int k = 0;
         while(expectedCRCs[8] == 0)
         {
         String s = "Unknown problem";
         drawLoadingText(20, (byte)4, "Connecting to web server");
         try
         {
         DataInputStream datainputstream = openJagGrabInputStream("crc" + (int)(Math.random() * 99999999D) + "-" + 317);
         Stream class30_sub2_sub2 = new Stream(new byte[40], 891);
         datainputstream.readFully(class30_sub2_sub2.buffer, 0, 40);
         datainputstream.close();
         for(int i1 = 0; i1 < 9; i1++)
         expectedCRCs[i1] = class30_sub2_sub2.readDWord();
         int j1 = class30_sub2_sub2.readDWord();
         int k1 = 1234;
         for(int l1 = 0; l1 < 9; l1++)
         k1 = (k1 << 1) + expectedCRCs[l1];
         if(j1 != k1)
         {
         s = "checksum problem";
         expectedCRCs[8] = 0;
         }
         }
         catch(EOFException _ex)
         {
         s = "EOF problem";
         expectedCRCs[8] = 0;
         }
         catch(IOException _ex)
         {
         s = "connection problem";
         expectedCRCs[8] = 0;
         }
         catch(Exception _ex)
         {
         s = "logic problem";
         expectedCRCs[8] = 0;
         if(!signlink.reporterror)
         return;
         }
         if(expectedCRCs[8] == 0)
         {
         k++;
         for(int l = j; l > 0; l--)
         {
         if(k >= 10)
         {
         drawLoadingText(10, (byte)4, "Game updated - please reload page");
         l = 10;
         } else
         {
         drawLoadingText(10, (byte)4, s + " - Will retry in " + l + " secs.");
         }
         try
         {
         Thread.sleep(1000L);
         }
         catch(Exception _ex) { }
         }
         j *= 2;
         if(j > 60)
         j = 60;
         aBoolean872 = !aBoolean872;
         }
         }
         */
    }

    private boolean menuHasAddFriend(int id) {
        if (id < 0) {
            return false;
        }
        int menuAction = menuActionID[id];
        
        if (menuAction >= 2000) {
            menuAction -= 2000;
        }
        return menuAction == 337;
    }

    private void drawChatArea() {
        aRSImageProducer_1166.initDrawingArea();
        Texture.anIntArray1472 = anIntArray1180;
        chatBack.draw(0, 0);
        
        if (messagePromptRaised) {
            chatTextDrawingArea.drawText(0, aString1121, 40, 239);
            chatTextDrawingArea.drawText(128, promptInput + "*", 60, 239);
        } else if (inputDialogState == 1) {
            chatTextDrawingArea.drawText(0, "Enter amount:", 40, 239);
            chatTextDrawingArea.drawText(128, amountOrNameInput + "*", 60, 239);
        } else if (inputDialogState == 2) {
            chatTextDrawingArea.drawText(0, "Enter name:", 40, 239);
            chatTextDrawingArea.drawText(128, amountOrNameInput + "*", 60, 239);
        } else if (aString844 != null) {
            chatTextDrawingArea.drawText(0, aString844, 40, 239);
            chatTextDrawingArea.drawText(128, "Click to continue", 60, 239);
        } else if (backDialogID != -1) {
            drawInterface(0, 0, RSInterface.interfaceCache[backDialogID], 0);
        } else if (dialogID != -1) {
            drawInterface(0, 0, RSInterface.interfaceCache[dialogID], 0);
        } else {
            TextDrawingArea textDrawingArea = loadingTextArea;
            int j = 0;
            DrawingArea.setDrawingArea(77, 0, 463, 0);
            
            for (int k = 0; k < 100; k++) {
                if (chatMessages[k] != null) {
                    int l = chatTypes[k];
                    int i1 = (70 - j * 14) + anInt1089;
                    String s1 = chatNames[k];
                    byte byte0 = 0;
                    
                    if (s1 != null && s1.startsWith("@cr1@")) {
                        s1 = s1.substring(5);
                        byte0 = 1;
                    }
                    
                    if (s1 != null && s1.startsWith("@cr2@")) {
                        s1 = s1.substring(5);
                        byte0 = 2;
                    }
                    
                    if (l == 0) {
                        if (i1 > 0 && i1 < 110) {
                            textDrawingArea.method385(0, chatMessages[k], i1, 4);
                        }
                        j++;
                    }
                    
                    if ((l == 1 || l == 2) && (l == 1 || publicChatMode == 0 || publicChatMode == 1 && isFriendOrSelf(s1))) {
                        if (i1 > 0 && i1 < 110) {
                            int j1 = 4;
                            
                            if (byte0 == 1) {
                                modIcons[0].draw(j1, i1 - 12);
                                j1 += 14;
                            }
                            
                            if (byte0 == 2) {
                                modIcons[1].draw(j1, i1 - 12);
                                j1 += 14;
                            }
                            textDrawingArea.method385(0, s1 + ":", i1, j1);
                            j1 += textDrawingArea.getTextWidthWithoutColourCodes(s1) + 8;
                            textDrawingArea.method385(255, chatMessages[k], i1, j1);
                        }
                        j++;
                    }
                    
                    if ((l == 3 || l == 7) && splitPrivateChat == 0
                            && (l == 7 || privateChatMode == 0 || privateChatMode == 1 && isFriendOrSelf(s1))) {
                        if (i1 > 0 && i1 < 110) {
                            int k1 = 4;
                            textDrawingArea.method385(0, "From", i1, k1);
                            k1 += textDrawingArea.getTextWidthWithoutColourCodes("From ");
                            
                            if (byte0 == 1) {
                                modIcons[0].draw(k1, i1 - 12);
                                k1 += 14;
                            }
                            
                            if (byte0 == 2) {
                                modIcons[1].draw(k1, i1 - 12);
                                k1 += 14;
                            }
                            textDrawingArea.method385(0, s1 + ":", i1, k1);
                            k1 += textDrawingArea.getTextWidthWithoutColourCodes(s1) + 8;
                            textDrawingArea.method385(0x800000, chatMessages[k], i1, k1);
                        }
                        j++;
                    }
                    
                    if (l == 4 && (tradeMode == 0 || tradeMode == 1 && isFriendOrSelf(s1))) {
                        if (i1 > 0 && i1 < 110) {
                            textDrawingArea.method385(0x800080, s1 + " " + chatMessages[k], i1, 4);
                        }
                        j++;
                    }
                    
                    if (l == 5 && splitPrivateChat == 0 && privateChatMode < 2) {
                        if (i1 > 0 && i1 < 110) {
                            textDrawingArea.method385(0x800000, chatMessages[k], i1, 4);
                        }
                        j++;
                    }
                    
                    if (l == 6 && splitPrivateChat == 0 && privateChatMode < 2) {
                        if (i1 > 0 && i1 < 110) {
                            textDrawingArea.method385(0, "To " + s1 + ":", i1, 4);
                            textDrawingArea.method385(0x800000, chatMessages[k], i1, 12 + textDrawingArea.getTextWidthWithoutColourCodes("To " + s1));
                        }
                        j++;
                    }
                    
                    if (l == 8 && (tradeMode == 0 || tradeMode == 1 && isFriendOrSelf(s1))) {
                        if (i1 > 0 && i1 < 110) {
                            textDrawingArea.method385(0x7e3200, s1 + " " + chatMessages[k], i1, 4);
                        }
                        j++;
                    }
                }
            }
            DrawingArea.resetSize();
            anInt1211 = j * 14 + 7;
            
            if (anInt1211 < 78) {
                anInt1211 = 78;
            }
            method30(77, anInt1211 - anInt1089 - 77, 0, 463, anInt1211);
            String s;
            
            if (myPlayer != null && myPlayer.name != null) {
                s = myPlayer.name;
            } else {
                s = StringHelper.fixName(myUsername);
            }
            textDrawingArea.method385(0, s + ":", 90, 4);
            textDrawingArea.method385(255, inputString + "*", 90, 6 + textDrawingArea.getTextWidthWithoutColourCodes(s + ": "));
            DrawingArea.method339(77, 0, 479, 0);
        }
        
        if (menuOpen && menuScreenArea == 2) {
            drawMenu();
        }
        aRSImageProducer_1166.drawGraphics(super.graphics, 17, 357);
        aRSImageProducer_1165.initDrawingArea();
        Texture.anIntArray1472 = anIntArray1182;
    }

    /**
     * The constructor for the web-applet.
     */
    public void init() {
        nodeId = Integer.parseInt(getParameter("nodeid"));
        port = Integer.parseInt(getParameter("port"));
        String s = getParameter("lowmem");
        
        if (s != null && s.equals("1")) {
            setLowMemMode();
        } else {
            setHighMemMode();
        }
        String s1 = getParameter("free");
        isMembers = !(s1 != null && s1.equals("1"));
        initClientFrame(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    public void startRunnable(Runnable runnable, int i) {
        if (i > 10) {
            i = 10;
        }
        if (Signlink.mainapp != null) {
            Signlink.startThread(runnable, i);
        } else {
            super.startRunnable(runnable, i);
        }
    }

    public Socket openSocket(int i) throws IOException {
        if (Signlink.mainapp != null) {
            return Signlink.getSocket(i);
        } else {
            return new Socket(InetAddress.getByName(getCodeBase().getHost()), i);
        }
    }

    private void processMenuClick() {
        if (activeInterfaceType != 0) {
            return;
        }
        int clickMode = super.clickMode3;
        
        if (spellSelected == 1 && super.saveClickX >= 516 && super.saveClickY >= 160
                && super.saveClickX <= 765 && super.saveClickY <= 205) {
            clickMode = 0;
        }
        
        if (menuOpen) {
            if (clickMode != 1) {
                int mouseX = super.mouseX;
                int mouseY = super.mouseY;
                
                if (menuScreenArea == 0) {
                    mouseX -= 4;
                    mouseY -= 4;
                }
                
                if (menuScreenArea == 1) {
                    mouseX -= 553;
                    mouseY -= 205;
                }
                
                if (menuScreenArea == 2) {
                    mouseX -= 17;
                    mouseY -= 357;
                }
                
                if (mouseX < menuOffsetX - 10 || mouseX > menuOffsetX + menuWidth + 10
                        || mouseY < menuOffsetY - 10 || mouseY > menuOffsetY + anInt952 + 10) {
                    menuOpen = false;
                    
                    if (menuScreenArea == 1) {
                        needDrawTabArea = true;
                    }
                    
                    if (menuScreenArea == 2) {
                        inputTaken = true;
                    }
                }
            }
            
            if (clickMode == 1) {
                int mouseClickX = super.saveClickX;
                int mouseClickY = super.saveClickY;
                
                if (menuScreenArea == 0) {
                    mouseClickX -= 4;
                    mouseClickY -= 4;
                }
                
                if (menuScreenArea == 1) {
                    mouseClickX -= 553;
                    mouseClickY -= 205;
                }
                
                if (menuScreenArea == 2) {
                    mouseClickX -= 17;
                    mouseClickY -= 357;
                }
                int selectedRowIdx = -1;
                
                for (int rowIdx = 0; rowIdx < menuActionRow; rowIdx++) {
                    int k3 = menuOffsetY + 31 + (menuActionRow - 1 - rowIdx) * 15;
                    
                    if (mouseClickX > menuOffsetX && mouseClickX < menuOffsetX + menuWidth
                            && mouseClickY > k3 - 13 && mouseClickY < k3 + 3) {
                        selectedRowIdx = rowIdx;
                    }
                }

                if (selectedRowIdx != -1) {
                    doAction(selectedRowIdx);
                }
                menuOpen = false;
                
                if (menuScreenArea == 1) {
                    needDrawTabArea = true;
                }
                
                if (menuScreenArea == 2) {
                    inputTaken = true;
                }
            }
        } else {
            if (clickMode == 1 && menuActionRow > 0) {
                int menuAction = menuActionID[menuActionRow - 1];
                
                if (menuAction == 632 || menuAction == 78 || menuAction == 867 || menuAction == 431 || menuAction == 53
                        || menuAction == 74 || menuAction == 454 || menuAction == 539 || menuAction == 493
                        || menuAction == 847 || menuAction == 447 || menuAction == 1125) {
                    int l1 = menuActionCmd2[menuActionRow - 1];
                    int interfaceId = menuActionCmd3[menuActionRow - 1];
                    RSInterface rsi = RSInterface.interfaceCache[interfaceId];
                    
                    if (rsi.aBoolean259 || rsi.aBoolean235) {
                        aBoolean1242 = false;
                        anInt989 = 0;
                        anInt1084 = interfaceId;
                        anInt1085 = l1;
                        activeInterfaceType = 2;
                        anInt1087 = super.saveClickX;
                        anInt1088 = super.saveClickY;
                        
                        if (RSInterface.interfaceCache[interfaceId].parentID == openInterfaceID) {
                            activeInterfaceType = 1;
                        }
                        
                        if (RSInterface.interfaceCache[interfaceId].parentID == backDialogID) {
                            activeInterfaceType = 3;
                        }
                        return;
                    }
                }
            }
            
            if (clickMode == 1 && (anInt1253 == 1 || menuHasAddFriend(menuActionRow - 1)) && menuActionRow > 2) {
                clickMode = 2;
            }
            
            if (clickMode == 1 && menuActionRow > 0) {
                doAction(menuActionRow - 1);
            }
            
            if (clickMode == 2 && menuActionRow > 0) {
                determineMenuSize();
            }
        }
    }

    private void saveMidi(boolean flag, byte buf[]) {
        Signlink.midifade = flag ? 1 : 0;
        Signlink.saveMidi(buf, buf.length);
    }

    private void method22() {
        try {
            anInt985 = -1;
            aClass19_1056.removeAll();
            aClass19_1013.removeAll();
            Texture.method366();
            unlinkMRUNodes();
            worldController.initToNull();
            System.gc();
            
            for (int i = 0; i < 4; i++) {
                clipMap[i].init();
            }

            for (int l = 0; l < 4; l++) {
                for (int k1 = 0; k1 < 104; k1++) {
                    for (int j2 = 0; j2 < 104; j2++) {
                        byteGroundArray[l][k1][j2] = 0;
                    }
                }
            }
            ObjectManager objectManager = new ObjectManager(byteGroundArray, intGroundArray);
            int k2 = aByteArrayArray1183.length;
            stream.writePacketHeaderEnc(0);
            
            if (!aBoolean1159) {
                for (int i3 = 0; i3 < k2; i3++) {
                    int i4 = (anIntArray1234[i3] >> 8) * 64 - baseX;
                    int k5 = (anIntArray1234[i3] & 0xff) * 64 - baseY;
                    byte abyte0[] = aByteArrayArray1183[i3];
                    
                    if (abyte0 != null) {
                        objectManager.method180(abyte0, k5, i4, (anInt1069 - 6) * 8, (anInt1070 - 6) * 8, clipMap);
                    }
                }

                for (int j4 = 0; j4 < k2; j4++) {
                    int l5 = (anIntArray1234[j4] >> 8) * 64 - baseX;
                    int k7 = (anIntArray1234[j4] & 0xff) * 64 - baseY;
                    byte abyte2[] = aByteArrayArray1183[j4];
                    
                    if (abyte2 == null && anInt1070 < 800) {
                        objectManager.method174(k7, 64, 64, l5);
                    }
                }
                anInt1097++;
                
                if (anInt1097 > 160) {
                    anInt1097 = 0;
                    stream.writePacketHeaderEnc(238);
                    stream.writeByte(96);
                }
                stream.writePacketHeaderEnc(0);
                
                for (int i6 = 0; i6 < k2; i6++) {
                    byte abyte1[] = aByteArrayArray1247[i6];
                    
                    if (abyte1 != null) {
                        int l8 = (anIntArray1234[i6] >> 8) * 64 - baseX;
                        int k9 = (anIntArray1234[i6] & 0xff) * 64 - baseY;
                        objectManager.method190(l8, clipMap, k9, worldController, abyte1);
                    }
                }
            }
            
            if (aBoolean1159) {
                for (int z = 0; z < 4; z++) {
                    for (int x = 0; x < 13; x++) {
                        for (int y = 0; y < 13; y++) {
                            int l7 = mapRegionPalette[z][x][y];
                            
                            if (l7 != -1) {
                                int i9 = l7 >> 24 & 3;
                                int l9 = l7 >> 1 & 3;
                                int j10 = l7 >> 14 & 0x3ff;
                                int l10 = l7 >> 3 & 0x7ff;
                                int j11 = (j10 / 8 << 8) + l10 / 8;
                                
                                for (int l11 = 0; l11 < anIntArray1234.length; l11++) {
                                    if (anIntArray1234[l11] != j11 || aByteArrayArray1183[l11] == null) {
                                        continue;
                                    }
                                    objectManager.method179(i9, l9, clipMap, x * 8, (j10 & 7) * 8, aByteArrayArray1183[l11], (l10 & 7) * 8, z, y * 8);
                                    break;
                                }
                            }
                        }
                    }
                }

                for (int l4 = 0; l4 < 13; l4++) {
                    for (int k6 = 0; k6 < 13; k6++) {
                        int i8 = mapRegionPalette[0][l4][k6];
                        if (i8 == -1) {
                            objectManager.method174(k6 * 8, 8, 8, l4 * 8);
                        }
                    }
                }
                stream.writePacketHeaderEnc(0);
                
                for (int l6 = 0; l6 < 4; l6++) {
                    for (int j8 = 0; j8 < 13; j8++) {
                        for (int j9 = 0; j9 < 13; j9++) {
                            int i10 = mapRegionPalette[l6][j8][j9];
                            if (i10 != -1) {
                                int k10 = i10 >> 24 & 3;
                                int i11 = i10 >> 1 & 3;
                                int k11 = i10 >> 14 & 0x3ff;
                                int i12 = i10 >> 3 & 0x7ff;
                                int j12 = (k11 / 8 << 8) + i12 / 8;
                                
                                for (int k12 = 0; k12 < anIntArray1234.length; k12++) {
                                    if (anIntArray1234[k12] != j12 || aByteArrayArray1247[k12] == null) {
                                        continue;
                                    }
                                    objectManager.method183(clipMap, worldController, k10, j8 * 8, (i12 & 7) * 8, l6, aByteArrayArray1247[k12], (k11 & 7) * 8, i11, j9 * 8);
                                    break;
                                }
                            }
                        }
                    }
                }
            }
            stream.writePacketHeaderEnc(0);
            objectManager.method171(clipMap, worldController);
            aRSImageProducer_1165.initDrawingArea();
            stream.writePacketHeaderEnc(0);
            int k3 = ObjectManager.anInt145;
            if (k3 > plane) {
                k3 = plane;
            }
            if (k3 < plane - 1) {
                k3 = plane - 1;
            }
            if (lowMem) {
                worldController.method275(ObjectManager.anInt145);
            } else {
                worldController.method275(0);
            }
            for (int i5 = 0; i5 < 104; i5++) {
                for (int i7 = 0; i7 < 104; i7++) {
                    spawnGroundItem(i5, i7);
                }

            }

            anInt1051++;
            if (anInt1051 > 98) {
                anInt1051 = 0;
                stream.writePacketHeaderEnc(150);
            }
            method63();
        } catch (Exception exception) {
        }
        ObjectDef.mruNodes1.unlinkAll();

        if (super.gameFrame != null) {
            stream.writePacketHeaderEnc(210);
            stream.writeInt(0x3f008edd);
        }

        if (lowMem && Signlink.cache_dat != null) {
            int j = onDemandFetcher.getVersionCount(0);

            for (int i1 = 0; i1 < j; i1++) {
                int l1 = onDemandFetcher.getModelIndex(i1);

                if ((l1 & 0x79) == 0) {
                    Model.method461(i1);
                }
            }
        }
        System.gc();
        Texture.method367();
        onDemandFetcher.clearOnDemandDataNodeList();
        int k = (anInt1069 - 6) / 8 - 1;
        int j1 = (anInt1069 + 6) / 8 + 1;
        int i2 = (anInt1070 - 6) / 8 - 1;
        int l2 = (anInt1070 + 6) / 8 + 1;
        if (aBoolean1141) {
            k = 49;
            j1 = 50;
            i2 = 49;
            l2 = 50;
        }
        for (int l3 = k; l3 <= j1; l3++) {
            for (int j5 = i2; j5 <= l2; j5++) {
                if (l3 == k || l3 == j1 || j5 == i2 || j5 == l2) {
                    int j7 = onDemandFetcher.method562(0, j5, l3);
                    if (j7 != -1) {
                        onDemandFetcher.method560(j7, 3);
                    }
                    int k8 = onDemandFetcher.method562(1, j5, l3);
                    if (k8 != -1) {
                        onDemandFetcher.method560(k8, 3);
                    }
                }
            }

        }
    }

    private void unlinkMRUNodes() {
        ObjectDef.mruNodes1.unlinkAll();
        ObjectDef.mruNodes2.unlinkAll();
        EntityDef.mruNodes.unlinkAll();
        ItemDef.mruNodes2.unlinkAll();
        ItemDef.mruNodes1.unlinkAll();
        Player.mruNodes.unlinkAll();
        StaticAnimation.aMRUNodes_415.unlinkAll();
    }

    private void method24(int z) {
        int ai[] = sprite.spritePixels;
        int j = ai.length;
        
        for (int k = 0; k < j; k++) {
            ai[k] = 0;
        }

        for (int y = 1; y < 103; y++) {
            int i1 = 24628 + (103 - y) * 512 * 4;
            
            for (int x = 1; x < 103; x++) {
                if ((byteGroundArray[z][x][y] & 0x18) == 0) {
                    worldController.method309(ai, i1, z, x, y);
                }
                
                if (z < 3 && (byteGroundArray[z + 1][x][y] & 8) != 0) {
                    worldController.method309(ai, i1, z + 1, x, y);
                }
                i1 += 4;
            }
        }
        int j1 = ((238 + (int) (Math.random() * 20D)) - 10 << 16)
                + ((238 + (int) (Math.random() * 20D)) - 10 << 8) + ((238 + (int) (Math.random() * 20D)) - 10);
        int l1 = (238 + (int) (Math.random() * 20D)) - 10 << 16;
        sprite.initDrawingAreaForSprite();
        
        for (int y2 = 1; y2 < 103; y2++) {
            for (int x1 = 1; x1 < 103; x1++) {
                if ((byteGroundArray[z][x1][y2] & 0x18) == 0) {
                    method50(y2, j1, x1, l1, z);
                }
                
                if (z < 3 && (byteGroundArray[z + 1][x1][y2] & 8) != 0) {
                    method50(y2, j1, x1, l1, z + 1);
                }
            }
        }
        aRSImageProducer_1165.initDrawingArea();
        anInt1071 = 0;
        
        for (int k2 = 0; k2 < 104; k2++) {
            for (int l2 = 0; l2 < 104; l2++) {
                int objId = worldController.getObject3Uid(plane, k2, l2);
                
                if (objId != 0) {
                    objId = objId >> 14 & 0x7fff;
                    int j3 = ObjectDef.forID(objId).anInt746;
                    
                    if (j3 >= 0) {
                        int k3 = k2;
                        int l3 = l2;
                        
                        if (j3 != 22 && j3 != 29 && j3 != 34 && j3 != 36 && j3 != 46 && j3 != 47 && j3 != 48) {
                            byte byte0 = 104;
                            byte byte1 = 104;
                            int ai1[][] = clipMap[plane].mapFlags;
                            
                            for (int i4 = 0; i4 < 10; i4++) {
                                int j4 = (int) (Math.random() * 4D);
                                
                                if (j4 == 0 && k3 > 0 && k3 > k2 - 3 && (ai1[k3 - 1][l3] & 0x1280108) == 0) {
                                    k3--;
                                }
                                
                                if (j4 == 1 && k3 < byte0 - 1 && k3 < k2 + 3 && (ai1[k3 + 1][l3] & 0x1280180) == 0) {
                                    k3++;
                                }
                                
                                if (j4 == 2 && l3 > 0 && l3 > l2 - 3 && (ai1[k3][l3 - 1] & 0x1280102) == 0) {
                                    l3--;
                                }
                                
                                if (j4 == 3 && l3 < byte1 - 1 && l3 < l2 + 3 && (ai1[k3][l3 + 1] & 0x1280120) == 0) {
                                    l3++;
                                }
                            }
                        }
                        aClass30_Sub2_Sub1_Sub1Array1140[anInt1071] = mapFunctions[j3];
                        anIntArray1072[anInt1071] = k3;
                        anIntArray1073[anInt1071] = l3;
                        anInt1071++;
                    }
                }
            }
        }
    }

    private void spawnGroundItem(int x, int y) {
        NodeList nodeList = groundArray[plane][x][y];
        
        if (nodeList == null) {
            worldController.deleteObject4(plane, x, y);
            return;
        }
        int k = 0xfa0a1f01;
        Object obj = null;
        
        for (Item item = (Item) nodeList.reverseGetFirst();
                item != null;
                item = (Item) nodeList.reverseGetNext()) {
            ItemDef itemDef = ItemDef.forID(item.ID);
            int l = itemDef.value;
            
            if (itemDef.stackable) {
                l *= item.anInt1559 + 1;
            }
            // notifyItemSpawn(item, i + baseX, j + baseY);

            if (l > k) {
                k = l;
                obj = item;
            }
        }
        nodeList.insertTail(((Node) obj));
        Object obj1 = null;
        Object obj2 = null;
        
        for (Item item = (Item) nodeList.reverseGetFirst(); item != null; item = (Item) nodeList.reverseGetNext()) {
            if (item.ID != ((Item) (obj)).ID && obj1 == null) {
                obj1 = item;
            }
            
            if (item.ID != ((Item) (obj)).ID && item.ID != ((Item) obj1).ID && obj2 == null) {
                obj2 = item;
            }
        }
        int i1 = x + (y << 7) + 0x60000000;
        worldController.method281(x, i1, ((Animable) (obj1)), method42(plane, y * 128 + 64, x * 128 + 64), ((Animable) (obj2)), ((Animable) (obj)), plane, y);
    }

    private void method26(boolean flag) {
        for (int j = 0; j < npcCount; j++) {
            NPC npc = npcArray[npcIndices[j]];
            int k = 0x20000000 + (npcIndices[j] << 14);
            if (npc == null || !npc.isVisible() || npc.desc.aBoolean93 != flag) {
                continue;
            }
            int l = npc.x >> 7;
            int i1 = npc.y >> 7;
            if (l < 0 || l >= 104 || i1 < 0 || i1 >= 104) {
                continue;
            }
            if (npc.anInt1540 == 1 && (npc.x & 0x7f) == 64 && (npc.y & 0x7f) == 64) {
                if (anIntArrayArray929[l][i1] == anInt1265) {
                    continue;
                }
                anIntArrayArray929[l][i1] = anInt1265;
            }
            if (!npc.desc.aBoolean84) {
                k += 0x80000000;
            }
            worldController.method285(plane, npc.anInt1552, method42(plane, npc.y, npc.x), k, npc.y, (npc.anInt1540 - 1) * 64 + 60, npc.x, npc, npc.aBoolean1541);
        }
    }

    private boolean replayWave() {
        return Signlink.replayWave();
    }

    private void loadError() {
        String s = "ondemand";//was a constant parameter
        System.out.println(s);
        
        try {
            getAppletContext().showDocument(new URL(getCodeBase(), "loaderror_" + s + ".html"));
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        
        do {
            try {
                Thread.sleep(1000L);
            } catch (Exception _ex) {
            }
        } while (true);
    }

    private void buildInterfaceMenu(int i, RSInterface rsi, int k, int l, int i1, int j1) {
        if (rsi.type != 0 || rsi.children == null || rsi.aBoolean266) {
            return;
        }
        if (k < i || i1 < l || k > i + rsi.width || i1 > l + rsi.height) {
            return;
        }
        int interfaceChildrenLength = rsi.children.length;
        
        for (int childId = 0; childId < interfaceChildrenLength; childId++) {
            int i2 = rsi.childX[childId] + i;
            int j2 = (rsi.childY[childId] + l) - j1;
            RSInterface childInterface = RSInterface.interfaceCache[rsi.children[childId]];
            i2 += childInterface.offsetX;
            j2 += childInterface.offsetY;
            if ((childInterface.anInt230 >= 0 || childInterface.anInt216 != 0) && k >= i2 && i1 >= j2 && k < i2 + childInterface.width && i1 < j2 + childInterface.height) {
                if (childInterface.anInt230 >= 0) {
                    anInt886 = childInterface.anInt230;
                } else {
                    anInt886 = childInterface.id;
                }
            }
            if (childInterface.type == 0) {
                buildInterfaceMenu(i2, childInterface, k, j2, i1, childInterface.scrollPosition);
                
                if (childInterface.maximumScrollPosition > childInterface.height) {
                    method65(i2 + childInterface.width, childInterface.height, k, i1, childInterface, j2, true, childInterface.maximumScrollPosition);
                }
            } else {
                if (childInterface.atActionType == 1 && k >= i2 && i1 >= j2 && k < i2 + childInterface.width && i1 < j2 + childInterface.height) {
                    boolean flag = false;
                    
                    if (childInterface.anInt214 != 0) {
                        flag = buildFriendsListMenu(childInterface);
                    }
                    if (!flag) {
                        //System.out.println("1"+class9_1.tooltip + ", " + class9_1.interfaceID);
                        menuActionName[menuActionRow] = childInterface.tooltip + ", " + childInterface.id;
                        menuActionID[menuActionRow] = 315;
                        menuActionCmd3[menuActionRow] = childInterface.id;
                        menuActionRow++;
                    }
                }
                if (childInterface.atActionType == 2 && spellSelected == 0 && k >= i2 && i1 >= j2 && k < i2 + childInterface.width && i1 < j2 + childInterface.height) {
                    String s = childInterface.selectedActionName;
                    if (s.indexOf(" ") != -1) {
                        s = s.substring(0, s.indexOf(" "));
                    }
                    menuActionName[menuActionRow] = s + " @gre@" + childInterface.spellName;
                    menuActionID[menuActionRow] = 626;
                    menuActionCmd3[menuActionRow] = childInterface.id;
                    menuActionRow++;
                }
                if (childInterface.atActionType == 3 && k >= i2 && i1 >= j2 && k < i2 + childInterface.width && i1 < j2 + childInterface.height) {
                    menuActionName[menuActionRow] = "Close";
                    menuActionID[menuActionRow] = 200;
                    menuActionCmd3[menuActionRow] = childInterface.id;
                    menuActionRow++;
                }
                if (childInterface.atActionType == 4 && k >= i2 && i1 >= j2 && k < i2 + childInterface.width && i1 < j2 + childInterface.height) {
                    //System.out.println("2"+class9_1.tooltip + ", " + class9_1.interfaceID);
                    menuActionName[menuActionRow] = childInterface.tooltip + ", " + childInterface.id;
                    menuActionID[menuActionRow] = 169;
                    menuActionCmd3[menuActionRow] = childInterface.id;
                    menuActionRow++;
                }
                if (childInterface.atActionType == 5 && k >= i2 && i1 >= j2 && k < i2 + childInterface.width && i1 < j2 + childInterface.height) {
                    //System.out.println("3"+class9_1.tooltip + ", " + class9_1.interfaceID);
                    menuActionName[menuActionRow] = childInterface.tooltip + ", " + childInterface.id;
                    menuActionID[menuActionRow] = 646;
                    menuActionCmd3[menuActionRow] = childInterface.id;
                    menuActionRow++;
                }
                if (childInterface.atActionType == 6 && !aBoolean1149 && k >= i2 && i1 >= j2 && k < i2 + childInterface.width && i1 < j2 + childInterface.height) {
                    //System.out.println("4"+class9_1.tooltip + ", " + class9_1.interfaceID);
                    menuActionName[menuActionRow] = childInterface.tooltip + ", " + childInterface.id;
                    menuActionID[menuActionRow] = 679;
                    menuActionCmd3[menuActionRow] = childInterface.id;
                    menuActionRow++;
                }
                if (childInterface.type == 2) {
                    int k2 = 0;
                    for (int l2 = 0; l2 < childInterface.height; l2++) {
                        for (int i3 = 0; i3 < childInterface.width; i3++) {
                            int j3 = i2 + i3 * (32 + childInterface.invSpritePadX);
                            int k3 = j2 + l2 * (32 + childInterface.invSpritePadY);
                            if (k2 < 20) {
                                j3 += childInterface.spritesX[k2];
                                k3 += childInterface.spritesY[k2];
                            }
                            if (k >= j3 && i1 >= k3 && k < j3 + 32 && i1 < k3 + 32) {
                                mouseInvInterfaceIndex = k2;
                                lastActiveInvInterface = childInterface.id;
                                if (childInterface.inv[k2] > 0) {
                                    ItemDef itemDef = ItemDef.forID(childInterface.inv[k2] - 1);
                                    if (itemSelected == 1 && childInterface.isInventoryInterface) {
                                        if (childInterface.id != anInt1284 || k2 != anInt1283) {
                                            menuActionName[menuActionRow] = "Use " + selectedItemName + " with @lre@" + itemDef.name;
                                            menuActionID[menuActionRow] = 870;
                                            menuActionCmd1[menuActionRow] = itemDef.id;
                                            menuActionCmd2[menuActionRow] = k2;
                                            menuActionCmd3[menuActionRow] = childInterface.id;
                                            menuActionRow++;
                                        }
                                    } else if (spellSelected == 1 && childInterface.isInventoryInterface) {
                                        if ((spellUsableOn & 0x10) == 16) {
                                            menuActionName[menuActionRow] = spellTooltip + " @lre@" + itemDef.name;
                                            menuActionID[menuActionRow] = 543;
                                            menuActionCmd1[menuActionRow] = itemDef.id;
                                            menuActionCmd2[menuActionRow] = k2;
                                            menuActionCmd3[menuActionRow] = childInterface.id;
                                            menuActionRow++;
                                        }
                                    } else {
                                        if (childInterface.isInventoryInterface) {
                                            for (int l3 = 4; l3 >= 3; l3--) {
                                                if (itemDef.actions != null && itemDef.actions[l3] != null) {
                                                    menuActionName[menuActionRow] = itemDef.actions[l3] + " @lre@" + itemDef.name;
                                                    if (l3 == 3) {
                                                        menuActionID[menuActionRow] = 493;
                                                    }
                                                    if (l3 == 4) {
                                                        menuActionID[menuActionRow] = 847;
                                                    }
                                                    menuActionCmd1[menuActionRow] = itemDef.id;
                                                    menuActionCmd2[menuActionRow] = k2;
                                                    menuActionCmd3[menuActionRow] = childInterface.id;
                                                    menuActionRow++;
                                                } else if (l3 == 4) {
                                                    menuActionName[menuActionRow] = "Drop @lre@" + itemDef.name;
                                                    menuActionID[menuActionRow] = 847;
                                                    menuActionCmd1[menuActionRow] = itemDef.id;
                                                    menuActionCmd2[menuActionRow] = k2;
                                                    menuActionCmd3[menuActionRow] = childInterface.id;
                                                    menuActionRow++;
                                                }
                                            }
                                        }
                                        
                                        if (childInterface.usableItemInterface) {
                                            menuActionName[menuActionRow] = "Use @lre@" + itemDef.name;
                                            menuActionID[menuActionRow] = 447;
                                            menuActionCmd1[menuActionRow] = itemDef.id;
                                            menuActionCmd2[menuActionRow] = k2;
                                            menuActionCmd3[menuActionRow] = childInterface.id;
                                            menuActionRow++;
                                        }
                                        
                                        if (childInterface.isInventoryInterface && itemDef.actions != null) {
                                            for (int i4 = 2; i4 >= 0; i4--) {
                                                if (itemDef.actions[i4] != null) {
                                                    menuActionName[menuActionRow] = itemDef.actions[i4] + " @lre@" + itemDef.name;
                                                    if (i4 == 0) {
                                                        menuActionID[menuActionRow] = 74;
                                                    }
                                                    if (i4 == 1) {
                                                        menuActionID[menuActionRow] = 454;
                                                    }
                                                    if (i4 == 2) {
                                                        menuActionID[menuActionRow] = 539;
                                                    }
                                                    menuActionCmd1[menuActionRow] = itemDef.id;
                                                    menuActionCmd2[menuActionRow] = k2;
                                                    menuActionCmd3[menuActionRow] = childInterface.id;
                                                    menuActionRow++;
                                                }
                                            }
                                        }
                                        if (childInterface.actions != null) {
                                            for (int j4 = 4; j4 >= 0; j4--) {
                                                if (childInterface.actions[j4] != null) {
                                                    menuActionName[menuActionRow] = childInterface.actions[j4] + " @lre@" + itemDef.name;
                                                    if (j4 == 0) {
                                                        menuActionID[menuActionRow] = 632;
                                                    }
                                                    if (j4 == 1) {
                                                        menuActionID[menuActionRow] = 78;
                                                    }
                                                    if (j4 == 2) {
                                                        menuActionID[menuActionRow] = 867;
                                                    }
                                                    if (j4 == 3) {
                                                        menuActionID[menuActionRow] = 431;
                                                    }
                                                    if (j4 == 4) {
                                                        menuActionID[menuActionRow] = 53;
                                                    }
                                                    menuActionCmd1[menuActionRow] = itemDef.id;
                                                    menuActionCmd2[menuActionRow] = k2;
                                                    menuActionCmd3[menuActionRow] = childInterface.id;
                                                    menuActionRow++;
                                                }
                                            }
                                        }
                                        menuActionName[menuActionRow] = "Examine @lre@" + itemDef.name + " @gre@(@whi@" + (childInterface.inv[k2] - 1) + "@gre@)";
                                        menuActionID[menuActionRow] = 1125;
                                        menuActionCmd1[menuActionRow] = itemDef.id;
                                        menuActionCmd2[menuActionRow] = k2;
                                        menuActionCmd3[menuActionRow] = childInterface.id;
                                        menuActionRow++;
                                    }
                                }
                            }
                            k2++;
                        }
                    }
                }
            }
        }
    }

    private void method30(int j, int k, int l, int i1, int j1) {
        scrollBar1.draw(i1, l);
        scrollBar2.draw(i1, (l + j) - 16);
        DrawingArea.fillPixelsReverseOrder(j - 32, l + 16, i1, anInt1002, 16);
        int k1 = ((j - 32) * j) / j1;
        if (k1 < 8) {
            k1 = 8;
        }
        int l1 = ((j - 32 - k1) * k) / (j1 - j);
        DrawingArea.fillPixelsReverseOrder(k1, l + 16 + l1, i1, anInt1063, 16);
        DrawingArea.method341(l + 16 + l1, anInt902, k1, i1);
        DrawingArea.method341(l + 16 + l1, anInt902, k1, i1 + 1);
        DrawingArea.method339(l + 16 + l1, anInt902, 16, i1);
        DrawingArea.method339(l + 17 + l1, anInt902, 16, i1);
        DrawingArea.method341(l + 16 + l1, anInt927, k1, i1 + 15);
        DrawingArea.method341(l + 17 + l1, anInt927, k1 - 1, i1 + 14);
        DrawingArea.method339(l + 15 + l1 + k1, anInt927, 16, i1);
        DrawingArea.method339(l + 14 + l1 + k1, anInt927, 15, i1 + 1);
    }

    private void updateNPCs(Stream stream, int packetLength) {
        anInt839 = 0;
        anInt893 = 0;
        method139(stream);
        method46(packetLength, stream);
        updateNpcByFlag(stream);
        
        for (int k = 0; k < anInt839; k++) {
            int l = removeFromUpdatingPlayersList[k];
            
            if (npcArray[l].loopCycle != loopCycle) {
                npcArray[l].desc = null;
                npcArray[l] = null;
            }
        }

        if (stream.currentOffset != packetLength) {
            Signlink.printError(myUsername + " size mismatch in getnpcpos - pos:" + stream.currentOffset + " psize:" + packetLength);
            throw new RuntimeException("eek");
        }
        
        for (int i1 = 0; i1 < npcCount; i1++) {
            if (npcArray[npcIndices[i1]] == null) {
                Signlink.printError(myUsername + " null entry in npc list - pos:" + i1 + " size:" + npcCount);
                throw new RuntimeException("eek");
            }
        }
    }

    private void processChatModeClick() {
        if (super.clickMode3 == 1) {
            if (super.saveClickX >= 6 && super.saveClickX <= 106 && super.saveClickY >= 467 && super.saveClickY <= 499) {
                publicChatMode = (publicChatMode + 1) % 4;
                aBoolean1233 = true;
                inputTaken = true;
                stream.writePacketHeaderEnc(95);
                stream.writeByte(publicChatMode);
                stream.writeByte(privateChatMode);
                stream.writeByte(tradeMode);
            }
            
            if (super.saveClickX >= 135 && super.saveClickX <= 235 && super.saveClickY >= 467 && super.saveClickY <= 499) {
                privateChatMode = (privateChatMode + 1) % 3;
                aBoolean1233 = true;
                inputTaken = true;
                stream.writePacketHeaderEnc(95);
                stream.writeByte(publicChatMode);
                stream.writeByte(privateChatMode);
                stream.writeByte(tradeMode);
            }
            
            if (super.saveClickX >= 273 && super.saveClickX <= 373 && super.saveClickY >= 467 && super.saveClickY <= 499) {
                tradeMode = (tradeMode + 1) % 3;
                aBoolean1233 = true;
                inputTaken = true;
                stream.writePacketHeaderEnc(95);
                stream.writeByte(publicChatMode);
                stream.writeByte(privateChatMode);
                stream.writeByte(tradeMode);
            }
            
            if (super.saveClickX >= 412 && super.saveClickX <= 512 && super.saveClickY >= 467 && super.saveClickY <= 499) {
                if (openInterfaceID == -1) {
                    clearTopInterfaces();
                    reportAbuseInput = "";
                    canMute = false;
                    
                    for (int i = 0; i < RSInterface.interfaceCache.length; i++) {
                        if (RSInterface.interfaceCache[i] == null || RSInterface.interfaceCache[i].anInt214 != 600) {
                            continue;
                        }
                        reportAbuseInterfaceID = openInterfaceID = RSInterface.interfaceCache[i].parentID;
                        break;
                    }
                } else {
                    pushMessage("Please close the interface you have open before using 'report abuse'", 0, "");
                }
            }
            anInt940++;
            
            if (anInt940 > 1386) {
                anInt940 = 0;
                stream.writePacketHeaderEnc(165);
                stream.writeByte(0);
                int j = stream.currentOffset;
                stream.writeByte(139);
                stream.writeByte(150);
                stream.writeShort(32131);
                stream.writeByte((int) (Math.random() * 256D));
                stream.writeShort(3250);
                stream.writeByte(177);
                stream.writeShort(24859);
                stream.writeByte(119);
                
                if ((int) (Math.random() * 2D) == 0) {
                    stream.writeShort(47234);
                }
                
                if ((int) (Math.random() * 2D) == 0) {
                    stream.writeByte(21);
                }
                stream.writeByteXXX(stream.currentOffset - j);
            }
        }
    }

    private void handleSettingId(int settingId) {
        int opcode = Varp.cache[settingId].anInt709;
        
        if (opcode == 0) {
            return;
        }
        int value = currentUserSetting[settingId];
        
        if (opcode == 1) {
            if (value == 1) {
                Texture.method372(0.90000000000000002D);
            }
            
            if (value == 2) {
                Texture.method372(0.80000000000000004D);
            }
            
            if (value == 3) {
                Texture.method372(0.69999999999999996D);
            }
            
            if (value == 4) {
                Texture.method372(0.59999999999999998D);
            }
            ItemDef.mruNodes1.unlinkAll();
            welcomeScreenRaised = true;
        }
        
        if (opcode == 3) {
            boolean flag1 = musicEnabled;
            
            if (value == 0) {
                adjustVolume(musicEnabled, 0);
                musicEnabled = true;
            }
            
            if (value == 1) {
                adjustVolume(musicEnabled, -400);
                musicEnabled = true;
            }
            
            if (value == 2) {
                adjustVolume(musicEnabled, -800);
                musicEnabled = true;
            }
            
            if (value == 3) {
                adjustVolume(musicEnabled, -1200);
                musicEnabled = true;
            }
            
            if (value == 4) {
                musicEnabled = false;
            }
            
            if (musicEnabled != flag1 && !lowMem) {
                if (musicEnabled) {
                    nextSong = currentSong;
                    songChanging = true;
                    onDemandFetcher.fetchItem(2, nextSong);
                } else {
                    stopMidi();
                }
                prevSong = 0;
            }
        }
        
        if (opcode == 4) {
            if (value == 0) {
                aBoolean848 = true;
                setWaveVolume(0);
            }
            
            if (value == 1) {
                aBoolean848 = true;
                setWaveVolume(-400);
            }
            
            if (value == 2) {
                aBoolean848 = true;
                setWaveVolume(-800);
            }
            
            if (value == 3) {
                aBoolean848 = true;
                setWaveVolume(-1200);
            }
            
            if (value == 4) {
                aBoolean848 = false;
            }
        }
        
        if (opcode == 5) {
            anInt1253 = value;
        }
        
        if (opcode == 6) {
            anInt1249 = value;
        }
        
        if (opcode == 8) {
            splitPrivateChat = value;
            inputTaken = true;
        }
        
        if (opcode == 9) {
            anInt913 = value;
        }
    }

    private void updateEntities() {
        try {
            int anInt974 = 0;
            for (int j = -1; j < playerCount + npcCount; j++) {
                Object obj;
                if (j == -1) {
                    obj = myPlayer;
                } else if (j < playerCount) {
                    obj = playerArray[playerIndices[j]];
                } else {
                    obj = npcArray[npcIndices[j - playerCount]];
                }
                if (obj == null || !((Entity) (obj)).isVisible()) {
                    continue;
                }
                if (obj instanceof NPC) {
                    EntityDef entityDef = ((NPC) obj).desc;
                    if (entityDef.childrenIDs != null) {
                        entityDef = entityDef.method161();
                    }
                    if (entityDef == null) {
                        continue;
                    }
                }
                if (j < playerCount) {
                    int l = 30;
                    Player player = (Player) obj;
                    if (player.headIcon != 0) {
                        npcScreenPos(((Entity) (obj)), ((Entity) (obj)).height + 15);
                        if (spriteDrawX > -1) {
                            for (int i2 = 0; i2 < 8; i2++) {
                                if ((player.headIcon & 1 << i2) != 0) {
                                    headIcons[i2].drawSprite(spriteDrawX - 12, spriteDrawY - l);
                                    l -= 25;
                                }
                            }

                        }
                    }
                    if (j >= 0 && iconType == 10 && iconPlayerTargetId == playerIndices[j]) {
                        npcScreenPos(((Entity) (obj)), ((Entity) (obj)).height + 15);
                        if (spriteDrawX > -1) {
                            headIcons[7].drawSprite(spriteDrawX - 12, spriteDrawY - l);
                        }
                    }
                } else {
                    EntityDef entityDef_1 = ((NPC) obj).desc;
                    if (entityDef_1.anInt75 >= 0 && entityDef_1.anInt75 < headIcons.length) {
                        npcScreenPos(((Entity) (obj)), ((Entity) (obj)).height + 15);
                        if (spriteDrawX > -1) {
                            headIcons[entityDef_1.anInt75].drawSprite(spriteDrawX - 12, spriteDrawY - 30);
                        }
                    }
                    if (iconType == 1 && iconTargetNpcId == npcIndices[j - playerCount] && loopCycle % 20 < 10) {
                        npcScreenPos(((Entity) (obj)), ((Entity) (obj)).height + 15);
                        if (spriteDrawX > -1) {
                            headIcons[2].drawSprite(spriteDrawX - 12, spriteDrawY - 28);
                        }
                    }
                }
                if (((Entity) (obj)).textSpoken != null && (j >= playerCount || publicChatMode == 0 || publicChatMode == 3 || publicChatMode == 1 && isFriendOrSelf(((Player) obj).name))) {
                    npcScreenPos(((Entity) (obj)), ((Entity) (obj)).height);
                    if (spriteDrawX > -1 && anInt974 < anInt975) {
                        anIntArray979[anInt974] = chatTextDrawingArea.getTextWidth(((Entity) (obj)).textSpoken) / 2;
                        anIntArray978[anInt974] = chatTextDrawingArea.anInt1497;
                        anIntArray976[anInt974] = spriteDrawX;
                        anIntArray977[anInt974] = spriteDrawY;
                        anIntArray980[anInt974] = ((Entity) (obj)).chatAttributes;
                        anIntArray981[anInt974] = ((Entity) (obj)).anInt1531;
                        anIntArray982[anInt974] = ((Entity) (obj)).textCycle;
                        aStringArray983[anInt974++] = ((Entity) (obj)).textSpoken;
                        if (anInt1249 == 0 && ((Entity) (obj)).anInt1531 >= 1 && ((Entity) (obj)).anInt1531 <= 3) {
                            anIntArray978[anInt974] += 10;
                            anIntArray977[anInt974] += 5;
                        }
                        if (anInt1249 == 0 && ((Entity) (obj)).anInt1531 == 4) {
                            anIntArray979[anInt974] = 60;
                        }
                        if (anInt1249 == 0 && ((Entity) (obj)).anInt1531 == 5) {
                            anIntArray978[anInt974] += 5;
                        }
                    }
                }
                if (((Entity) (obj)).loopCycleStatus > loopCycle) {
                    try {
                        npcScreenPos(((Entity) (obj)), ((Entity) (obj)).height + 15);
                        if (spriteDrawX > -1) {
                            int i1 = (((Entity) (obj)).currentHealth * 30) / ((Entity) (obj)).maxHealth;
                            if (i1 > 30) {
                                i1 = 30;
                            }
                            DrawingArea.fillPixelsReverseOrder(5, spriteDrawY - 3, spriteDrawX - 15, 65280, i1);
                            DrawingArea.fillPixelsReverseOrder(5, spriteDrawY - 3, (spriteDrawX - 15) + i1, 0xff0000, 30 - i1);
                        }
                    } catch (Exception e) {
                    }
                }
                for (int j1 = 0; j1 < 4; j1++) {
                    if (((Entity) (obj)).hitsLoopCycle[j1] > loopCycle) {
                        npcScreenPos(((Entity) (obj)), ((Entity) (obj)).height / 2);
                        if (spriteDrawX > -1) {
                            if (j1 == 1) {
                                spriteDrawY -= 20;
                            }
                            if (j1 == 2) {
                                spriteDrawX -= 15;
                                spriteDrawY -= 10;
                            }
                            if (j1 == 3) {
                                spriteDrawX += 15;
                                spriteDrawY -= 10;
                            }
                            hitMarks[((Entity) (obj)).hitMarkTypes[j1]].drawSprite(spriteDrawX - 12, spriteDrawY - 12);
                            aTextDrawingArea_1270.drawText(0, String.valueOf(((Entity) (obj)).hitArray[j1]), spriteDrawY + 4, spriteDrawX);
                            aTextDrawingArea_1270.drawText(0xffffff, String.valueOf(((Entity) (obj)).hitArray[j1]), spriteDrawY + 3, spriteDrawX - 1);
                        }
                    }
                }

            }
            for (int k = 0; k < anInt974; k++) {
                int k1 = anIntArray976[k];
                int l1 = anIntArray977[k];
                int j2 = anIntArray979[k];
                int k2 = anIntArray978[k];
                boolean flag = true;
                while (flag) {
                    flag = false;
                    for (int l2 = 0; l2 < k; l2++) {
                        if (l1 + 2 > anIntArray977[l2] - anIntArray978[l2] && l1 - k2 < anIntArray977[l2] + 2 && k1 - j2 < anIntArray976[l2] + anIntArray979[l2] && k1 + j2 > anIntArray976[l2] - anIntArray979[l2] && anIntArray977[l2] - anIntArray978[l2] < l1) {
                            l1 = anIntArray977[l2] - anIntArray978[l2];
                            flag = true;
                        }
                    }

                }
                spriteDrawX = anIntArray976[k];
                spriteDrawY = anIntArray977[k] = l1;
                String s = aStringArray983[k];
                if (anInt1249 == 0) {
                    int i3 = 0xffff00;
                    if (anIntArray980[k] < 6) {
                        i3 = anIntArray965[anIntArray980[k]];
                    }
                    if (anIntArray980[k] == 6) {
                        i3 = anInt1265 % 20 >= 10 ? 0xffff00 : 0xff0000;
                    }
                    if (anIntArray980[k] == 7) {
                        i3 = anInt1265 % 20 >= 10 ? 65535 : 255;
                    }
                    if (anIntArray980[k] == 8) {
                        i3 = anInt1265 % 20 >= 10 ? 0x80ff80 : 45056;
                    }
                    if (anIntArray980[k] == 9) {
                        int j3 = 150 - anIntArray982[k];
                        if (j3 < 50) {
                            i3 = 0xff0000 + 1280 * j3;
                        } else if (j3 < 100) {
                            i3 = 0xffff00 - 0x50000 * (j3 - 50);
                        } else if (j3 < 150) {
                            i3 = 65280 + 5 * (j3 - 100);
                        }
                    }
                    if (anIntArray980[k] == 10) {
                        int k3 = 150 - anIntArray982[k];
                        if (k3 < 50) {
                            i3 = 0xff0000 + 5 * k3;
                        } else if (k3 < 100) {
                            i3 = 0xff00ff - 0x50000 * (k3 - 50);
                        } else if (k3 < 150) {
                            i3 = (255 + 0x50000 * (k3 - 100)) - 5 * (k3 - 100);
                        }
                    }
                    if (anIntArray980[k] == 11) {
                        int l3 = 150 - anIntArray982[k];
                        if (l3 < 50) {
                            i3 = 0xffffff - 0x50005 * l3;
                        } else if (l3 < 100) {
                            i3 = 65280 + 0x50005 * (l3 - 50);
                        } else if (l3 < 150) {
                            i3 = 0xffffff - 0x50000 * (l3 - 100);
                        }
                    }
                    if (anIntArray981[k] == 0) {
                        chatTextDrawingArea.drawText(0, s, spriteDrawY + 1, spriteDrawX);
                        chatTextDrawingArea.drawText(i3, s, spriteDrawY, spriteDrawX);
                    }
                    if (anIntArray981[k] == 1) {
                        chatTextDrawingArea.method386(0, s, spriteDrawX, anInt1265, spriteDrawY + 1);
                        chatTextDrawingArea.method386(i3, s, spriteDrawX, anInt1265, spriteDrawY);
                    }
                    if (anIntArray981[k] == 2) {
                        chatTextDrawingArea.method387(spriteDrawX, s, anInt1265, spriteDrawY + 1, 0);
                        chatTextDrawingArea.method387(spriteDrawX, s, anInt1265, spriteDrawY, i3);
                    }
                    if (anIntArray981[k] == 3) {
                        chatTextDrawingArea.method388(150 - anIntArray982[k], s, anInt1265, spriteDrawY + 1, spriteDrawX, 0);
                        chatTextDrawingArea.method388(150 - anIntArray982[k], s, anInt1265, spriteDrawY, spriteDrawX, i3);
                    }
                    if (anIntArray981[k] == 4) {
                        int i4 = chatTextDrawingArea.getTextWidth(s);
                        int k4 = ((150 - anIntArray982[k]) * (i4 + 100)) / 150;
                        DrawingArea.setDrawingArea(334, spriteDrawX - 50, spriteDrawX + 50, 0);
                        chatTextDrawingArea.method385(0, s, spriteDrawY + 1, (spriteDrawX + 50) - k4);
                        chatTextDrawingArea.method385(i3, s, spriteDrawY, (spriteDrawX + 50) - k4);
                        DrawingArea.resetSize();
                    }
                    if (anIntArray981[k] == 5) {
                        int j4 = 150 - anIntArray982[k];
                        int l4 = 0;
                        if (j4 < 25) {
                            l4 = j4 - 25;
                        } else if (j4 > 125) {
                            l4 = j4 - 125;
                        }
                        DrawingArea.setDrawingArea(spriteDrawY + 5, 0, 512, spriteDrawY - chatTextDrawingArea.anInt1497 - 1);
                        chatTextDrawingArea.drawText(0, s, spriteDrawY + 1 + l4, spriteDrawX);
                        chatTextDrawingArea.drawText(i3, s, spriteDrawY + l4, spriteDrawX);
                        DrawingArea.resetSize();
                    }
                } else {
                    chatTextDrawingArea.drawText(0, s, spriteDrawY + 1, spriteDrawX);
                    chatTextDrawingArea.drawText(0xffff00, s, spriteDrawY, spriteDrawX);
                }
            }
        } catch (Exception e) {
        }
    }

    private void delFriend(long l) {
        try {
            if (l == 0L) {
                return;
            }
            for (int i = 0; i < friendsCount; i++) {
                if (friendsListAsLongs[i] != l) {
                    continue;
                }
                friendsCount--;
                needDrawTabArea = true;
                for (int j = i; j < friendsCount; j++) {
                    friendsList[j] = friendsList[j + 1];
                    friendsNodeIDs[j] = friendsNodeIDs[j + 1];
                    friendsListAsLongs[j] = friendsListAsLongs[j + 1];
                }

                stream.writePacketHeaderEnc(215);
                stream.writeLong(l);
                break;
            }
        } catch (RuntimeException runtimeexception) {
            Signlink.printError("18622, " + false + ", " + l + ", " + runtimeexception.toString());
            throw new RuntimeException();
        }
    }

    private void drawTabArea() {
        aRSImageProducer_1163.initDrawingArea();
        Texture.anIntArray1472 = anIntArray1181;
        invBack.draw(0, 0);
        if (invOverlayInterfaceID != -1) {
            drawInterface(0, 0, RSInterface.interfaceCache[invOverlayInterfaceID], 0);
        } else if (tabInterfaceIDs[tabID] != -1) {
            drawInterface(0, 0, RSInterface.interfaceCache[tabInterfaceIDs[tabID]], 0);
        }
        if (menuOpen && menuScreenArea == 1) {
            drawMenu();
        }
        aRSImageProducer_1163.drawGraphics(super.graphics, 553, 205);
        aRSImageProducer_1165.initDrawingArea();
        Texture.anIntArray1472 = anIntArray1182;
    }

    private void method37(int j) {
        if (!lowMem) {
            if (Texture.anIntArray1480[17] >= j) {
                Background background = Texture.aBackgroundArray1474s[17];
                int k = background.bgWidth * background.bgHeight - 1;
                int j1 = background.bgWidth * anInt945 * 2;
                byte abyte0[] = background.bgPixels;
                byte abyte3[] = aByteArray912;
                for (int i2 = 0; i2 <= k; i2++) {
                    abyte3[i2] = abyte0[i2 - j1 & k];
                }

                background.bgPixels = abyte3;
                aByteArray912 = abyte0;
                Texture.method370(17);
                anInt854++;
                if (anInt854 > 1235) {
                    anInt854 = 0;
                    stream.writePacketHeaderEnc(226);
                    stream.writeByte(0);
                    int l2 = stream.currentOffset;
                    stream.writeShort(58722);
                    stream.writeByte(240);
                    stream.writeShort((int) (Math.random() * 65536D));
                    stream.writeByte((int) (Math.random() * 256D));
                    if ((int) (Math.random() * 2D) == 0) {
                        stream.writeShort(51825);
                    }
                    stream.writeByte((int) (Math.random() * 256D));
                    stream.writeShort((int) (Math.random() * 65536D));
                    stream.writeShort(7130);
                    stream.writeShort((int) (Math.random() * 65536D));
                    stream.writeShort(61657);
                    stream.writeByteXXX(stream.currentOffset - l2);
                }
            }
            if (Texture.anIntArray1480[24] >= j) {
                Background background_1 = Texture.aBackgroundArray1474s[24];
                int l = background_1.bgWidth * background_1.bgHeight - 1;
                int k1 = background_1.bgWidth * anInt945 * 2;
                byte abyte1[] = background_1.bgPixels;
                byte abyte4[] = aByteArray912;
                for (int j2 = 0; j2 <= l; j2++) {
                    abyte4[j2] = abyte1[j2 - k1 & l];
                }

                background_1.bgPixels = abyte4;
                aByteArray912 = abyte1;
                Texture.method370(24);
            }
            if (Texture.anIntArray1480[34] >= j) {
                Background background_2 = Texture.aBackgroundArray1474s[34];
                int i1 = background_2.bgWidth * background_2.bgHeight - 1;
                int l1 = background_2.bgWidth * anInt945 * 2;
                byte abyte2[] = background_2.bgPixels;
                byte abyte5[] = aByteArray912;
                for (int k2 = 0; k2 <= i1; k2++) {
                    abyte5[k2] = abyte2[k2 - l1 & i1];
                }

                background_2.bgPixels = abyte5;
                aByteArray912 = abyte2;
                Texture.method370(34);
            }
        }
    }

    private void method38() {
        for (int i = -1; i < playerCount; i++) {
            int j;
            if (i == -1) {
                j = myPlayerIndex;
            } else {
                j = playerIndices[i];
            }
            Player player = playerArray[j];
            if (player != null && player.textCycle > 0) {
                player.textCycle--;
                if (player.textCycle == 0) {
                    player.textSpoken = null;
                }
            }
        }

        for (int k = 0; k < npcCount; k++) {
            int l = npcIndices[k];
            NPC npc = npcArray[l];
            if (npc != null && npc.textCycle > 0) {
                npc.textCycle--;
                if (npc.textCycle == 0) {
                    npc.textSpoken = null;
                }
            }
        }
    }

    private void calcCameraPos() {
        int i = cameraPositionX * 128 + 64;
        int j = cameraPositionY * 128 + 64;
        int k = method42(plane, j, i) - cameraPositionZ;
        if (xCameraPos < i) {
            xCameraPos += anInt1101 + ((i - xCameraPos) * anInt1102) / 1000;
            if (xCameraPos > i) {
                xCameraPos = i;
            }
        }
        if (xCameraPos > i) {
            xCameraPos -= anInt1101 + ((xCameraPos - i) * anInt1102) / 1000;
            if (xCameraPos < i) {
                xCameraPos = i;
            }
        }
        if (zCameraPos < k) {
            zCameraPos += anInt1101 + ((k - zCameraPos) * anInt1102) / 1000;
            if (zCameraPos > k) {
                zCameraPos = k;
            }
        }
        if (zCameraPos > k) {
            zCameraPos -= anInt1101 + ((zCameraPos - k) * anInt1102) / 1000;
            if (zCameraPos < k) {
                zCameraPos = k;
            }
        }
        if (yCameraPos < j) {
            yCameraPos += anInt1101 + ((j - yCameraPos) * anInt1102) / 1000;
            if (yCameraPos > j) {
                yCameraPos = j;
            }
        }
        if (yCameraPos > j) {
            yCameraPos -= anInt1101 + ((yCameraPos - j) * anInt1102) / 1000;
            if (yCameraPos < j) {
                yCameraPos = j;
            }
        }
        i = anInt995 * 128 + 64;
        j = anInt996 * 128 + 64;
        k = method42(plane, j, i) - anInt997;
        int l = i - xCameraPos;
        int i1 = k - zCameraPos;
        int j1 = j - yCameraPos;
        int k1 = (int) Math.sqrt(l * l + j1 * j1);
        int l1 = (int) (Math.atan2(i1, k1) * 325.94900000000001D) & 0x7ff;
        int i2 = (int) (Math.atan2(l, j1) * -325.94900000000001D) & 0x7ff;
        if (l1 < 128) {
            l1 = 128;
        }
        if (l1 > 383) {
            l1 = 383;
        }
        if (yCameraCurve < l1) {
            yCameraCurve += anInt998 + ((l1 - yCameraCurve) * anInt999) / 1000;
            if (yCameraCurve > l1) {
                yCameraCurve = l1;
            }
        }
        if (yCameraCurve > l1) {
            yCameraCurve -= anInt998 + ((yCameraCurve - l1) * anInt999) / 1000;
            if (yCameraCurve < l1) {
                yCameraCurve = l1;
            }
        }
        int j2 = i2 - xCameraCurve;
        if (j2 > 1024) {
            j2 -= 2048;
        }
        if (j2 < -1024) {
            j2 += 2048;
        }
        if (j2 > 0) {
            xCameraCurve += anInt998 + (j2 * anInt999) / 1000;
            xCameraCurve &= 0x7ff;
        }
        if (j2 < 0) {
            xCameraCurve -= anInt998 + (-j2 * anInt999) / 1000;
            xCameraCurve &= 0x7ff;
        }
        int k2 = i2 - xCameraCurve;
        if (k2 > 1024) {
            k2 -= 2048;
        }
        if (k2 < -1024) {
            k2 += 2048;
        }
        if (k2 < 0 && j2 > 0 || k2 > 0 && j2 < 0) {
            xCameraCurve = i2;
        }
    }

    private void drawMenu() {
        int i = menuOffsetX;
        int j = menuOffsetY;
        int k = menuWidth;
        int l = anInt952;
        int i1 = 0x5d5447;
        DrawingArea.fillPixelsReverseOrder(l, j, i, i1, k);
        DrawingArea.fillPixelsReverseOrder(16, j + 1, i + 1, 0, k - 2);
        DrawingArea.fillPixels(i + 1, k - 2, l - 19, 0, j + 18);
        chatTextDrawingArea.method385(i1, "Choose Option", j + 14, i + 3);
        int j1 = super.mouseX;
        int k1 = super.mouseY;
        if (menuScreenArea == 0) {
            j1 -= 4;
            k1 -= 4;
        }
        if (menuScreenArea == 1) {
            j1 -= 553;
            k1 -= 205;
        }
        if (menuScreenArea == 2) {
            j1 -= 17;
            k1 -= 357;
        }
        for (int l1 = 0; l1 < menuActionRow; l1++) {
            int i2 = j + 31 + (menuActionRow - 1 - l1) * 15;
            int j2 = 0xffffff;
            if (j1 > i && j1 < i + k && k1 > i2 - 13 && k1 < i2 + 3) {
                j2 = 0xffff00;
            }
            chatTextDrawingArea.drawText(true, i + 3, j2, menuActionName[l1], i2);
        }
    }

    private void addFriend(long l) {
        try {
            if (l == 0L) {
                return;
            }
            if (friendsCount >= 100 && membershipFlag != 1) {
                pushMessage("Your friendlist is full. Max of 100 for free users, and 200 for members", 0, "");
                return;
            }
            if (friendsCount >= 200) {
                pushMessage("Your friendlist is full. Max of 100 for free users, and 200 for members", 0, "");
                return;
            }
            String s = StringHelper.fixName(StringHelper.nameForLong(l));
            for (int i = 0; i < friendsCount; i++) {
                if (friendsListAsLongs[i] == l) {
                    pushMessage(s + " is already on your friend list", 0, "");
                    return;
                }
            }
            for (int j = 0; j < ignoreCount; j++) {
                if (ignoreListAsLongs[j] == l) {
                    pushMessage("Please remove " + s + " from your ignore list first", 0, "");
                    return;
                }
            }

            if (s.equals(myPlayer.name)) {
                return;
            } else {
                friendsList[friendsCount] = s;
                friendsListAsLongs[friendsCount] = l;
                friendsNodeIDs[friendsCount] = 0;
                friendsCount++;
                needDrawTabArea = true;
                stream.writePacketHeaderEnc(188);
                stream.writeLong(l);
                return;
            }
        } catch (RuntimeException runtimeexception) {
            Signlink.printError("15283, " + (byte) 68 + ", " + l + ", " + runtimeexception.toString());
        }
        throw new RuntimeException();
    }

    private int method42(int plane, int y, int x) {
        int tmpX = x >> 7;
        int tmpY = y >> 7;
        
        if (tmpX < 0 || tmpY < 0 || tmpX > 103 || tmpY > 103) {
            return 0;
        }
        int tmpPlane = plane;
        
        if (tmpPlane < 3 && (byteGroundArray[1][tmpX][tmpY] & 2) == 2) {
            tmpPlane++;
        }
        int k1 = x & 0x7f;
        int l1 = y & 0x7f;
        int i2 = intGroundArray[tmpPlane][tmpX][tmpY] * (128 - k1) + intGroundArray[tmpPlane][tmpX + 1][tmpY] * k1 >> 7;
        int j2 = intGroundArray[tmpPlane][tmpX][tmpY + 1] * (128 - k1) + intGroundArray[tmpPlane][tmpX + 1][tmpY + 1] * k1 >> 7;
        return i2 * (128 - l1) + j2 * l1 >> 7;
    }

    private void resetLogout() {
        try {
            if (socketStream != null) {
                socketStream.close();
            }
        } catch (Exception _ex) {
        }
        socketStream = null;
        loggedIn = false;
        loginScreenState = 0;
        // myUsername = "";
        // myPassword = "";
        unlinkMRUNodes();
        worldController.initToNull();
        
        for (int i = 0; i < 4; i++) {
            clipMap[i].init();
        }
        System.gc();
        stopMidi();
        currentSong = -1;
        nextSong = -1;
        prevSong = 0;
    }

    private void method45() {
        aBoolean1031 = true;
        for (int j = 0; j < 7; j++) {
            anIntArray1065[j] = -1;
            for (int k = 0; k < IDK.length; k++) {
                if (IDK.cache[k].aBoolean662 || IDK.cache[k].anInt657 != j + (aBoolean1047 ? 0 : 7)) {
                    continue;
                }
                anIntArray1065[j] = k;
                break;
            }

        }
    }

    private void method46(int i, Stream stream) {
        while (stream.bitPosition + 21 < i * 8) {
            int k = stream.readBits(14);
            if (k == 16383) {
                break;
            }
            if (npcArray[k] == null) {
                npcArray[k] = new NPC();
            }
            NPC npc = npcArray[k];
            npcIndices[npcCount++] = k;
            npc.loopCycle = loopCycle;
            int l = stream.readBits(5);
            if (l > 15) {
                l -= 32;
            }
            int i1 = stream.readBits(5);
            if (i1 > 15) {
                i1 -= 32;
            }
            int j1 = stream.readBits(1);
            npc.desc = EntityDef.forID(stream.readBits(12));
            int k1 = stream.readBits(1);
            if (k1 == 1) {
                updatingPlayersList[anInt893++] = k;
            }
            npc.anInt1540 = npc.desc.aByte68;
            npc.anInt1504 = npc.desc.anInt79;
            npc.anInt1554 = npc.desc.anInt67;
            npc.anInt1555 = npc.desc.anInt58;
            npc.anInt1556 = npc.desc.anInt83;
            npc.anInt1557 = npc.desc.anInt55;
            npc.anInt1511 = npc.desc.anInt77;
            npc.setPosition(myPlayer.smallX[0] + i1, myPlayer.smallY[0] + l, j1 == 1);
        }
        stream.finishBitAccess();
    }

    public void tick() {
        if (rsAlreadyLoaded || loadingError || genericLoadingError) {
            return;
        }
        loopCycle++;
        if (!loggedIn) {
            processLoginScreenInput();
        } else {
            mainGameProcessor();
        }
        processOnDemandQueue();
    }

    private void method47(boolean flag) {
        if (myPlayer.x >> 7 == destX && myPlayer.y >> 7 == destY) {
            destX = 0;
        }
        int j = playerCount;
        if (flag) {
            j = 1;
        }
        for (int l = 0; l < j; l++) {
            Player player;
            int i1;
            if (flag) {
                player = myPlayer;
                i1 = myPlayerIndex << 14;
            } else {
                player = playerArray[playerIndices[l]];
                i1 = playerIndices[l] << 14;
            }
            if (player == null || !player.isVisible()) {
                continue;
            }
            player.aBoolean1699 = (lowMem && playerCount > 50 || playerCount > 200) && !flag && player.anInt1517 == player.anInt1511;
            int j1 = player.x >> 7;
            int k1 = player.y >> 7;
            if (j1 < 0 || j1 >= 104 || k1 < 0 || k1 >= 104) {
                continue;
            }
            if (player.aModel_1714 != null && loopCycle >= player.anInt1707 && loopCycle < player.anInt1708) {
                player.aBoolean1699 = false;
                player.anInt1709 = method42(plane, player.y, player.x);
                worldController.method286(plane, player.y, player, player.anInt1552, player.anInt1722, player.x, player.anInt1709, player.anInt1719, player.anInt1721, i1, player.anInt1720);
                continue;
            }
            if ((player.x & 0x7f) == 64 && (player.y & 0x7f) == 64) {
                if (anIntArrayArray929[j1][k1] == anInt1265) {
                    continue;
                }
                anIntArrayArray929[j1][k1] = anInt1265;
            }
            player.anInt1709 = method42(plane, player.y, player.x);
            worldController.method285(plane, player.anInt1552, player.anInt1709, i1, player.y, 60, player.x, player, player.aBoolean1541);
        }
    }

    private boolean promptUserForInput(RSInterface class9) {
        int j = class9.anInt214;
        if (friendsListLoadStatus == 2) {
            if (j == 201) {
                inputTaken = true;
                inputDialogState = 0;
                messagePromptRaised = true;
                promptInput = "";
                friendsListAction = 1;
                aString1121 = "Enter name of friend to add to list";
            }
            if (j == 202) {
                inputTaken = true;
                inputDialogState = 0;
                messagePromptRaised = true;
                promptInput = "";
                friendsListAction = 2;
                aString1121 = "Enter name of friend to delete from list";
            }
        }
        if (j == 205) {
            anInt1011 = 250;
            return true;
        }
        if (j == 501) {
            inputTaken = true;
            inputDialogState = 0;
            messagePromptRaised = true;
            promptInput = "";
            friendsListAction = 4;
            aString1121 = "Enter name of player to add to list";
        }
        if (j == 502) {
            inputTaken = true;
            inputDialogState = 0;
            messagePromptRaised = true;
            promptInput = "";
            friendsListAction = 5;
            aString1121 = "Enter name of player to delete from list";
        }
        if (j >= 300 && j <= 313) {
            int k = (j - 300) / 2;
            int j1 = j & 1;
            int i2 = anIntArray1065[k];
            if (i2 != -1) {
                do {
                    if (j1 == 0 && --i2 < 0) {
                        i2 = IDK.length - 1;
                    }
                    if (j1 == 1 && ++i2 >= IDK.length) {
                        i2 = 0;
                    }
                } while (IDK.cache[i2].aBoolean662 || IDK.cache[i2].anInt657 != k + (aBoolean1047 ? 0 : 7));
                anIntArray1065[k] = i2;
                aBoolean1031 = true;
            }
        }
        if (j >= 314 && j <= 323) {
            int l = (j - 314) / 2;
            int k1 = j & 1;
            int j2 = anIntArray990[l];
            if (k1 == 0 && --j2 < 0) {
                j2 = anIntArrayArray1003[l].length - 1;
            }
            if (k1 == 1 && ++j2 >= anIntArrayArray1003[l].length) {
                j2 = 0;
            }
            anIntArray990[l] = j2;
            aBoolean1031 = true;
        }
        if (j == 324 && !aBoolean1047) {
            aBoolean1047 = true;
            method45();
        }
        if (j == 325 && aBoolean1047) {
            aBoolean1047 = false;
            method45();
        }
        if (j == 326) {
            stream.writePacketHeaderEnc(101);
            stream.writeByte(aBoolean1047 ? 0 : 1);
            for (int i1 = 0; i1 < 7; i1++) {
                stream.writeByte(anIntArray1065[i1]);
            }

            for (int l1 = 0; l1 < 5; l1++) {
                stream.writeByte(anIntArray990[l1]);
            }

            return true;
        }
        if (j == 613) {
            canMute = !canMute;
        }
        if (j >= 601 && j <= 612) {
            clearTopInterfaces();
            if (reportAbuseInput.length() > 0) {
                stream.writePacketHeaderEnc(218);
                stream.writeLong(StringHelper.longForName(reportAbuseInput));
                stream.writeByte(j - 601);
                stream.writeByte(canMute ? 1 : 0);
            }
        }
        return false;
    }

    private void flagBasedUpdatePlayer(Stream stream) {
        for (int idx = 0; idx < anInt893; idx++) {
            int playerId = updatingPlayersList[idx];
            Player player = playerArray[playerId];
            int mask = stream.readUByte();

            if ((mask & 0x40) != 0) {
                mask += stream.readUByte() << 8;
            }
            updatePlayerByFlag(mask, playerId, stream, player);
        }
    }

    private void method50(int i, int k, int l, int i1, int j1) {
        int k1 = worldController.getObject1Uid(j1, l, i);
        if (k1 != 0) {
            int l1 = worldController.method304(j1, l, i, k1);
            int k2 = l1 >> 6 & 3;
            int i3 = l1 & 0x1f;
            int k3 = k;
            if (k1 > 0) {
                k3 = i1;
            }
            int ai[] = sprite.spritePixels;
            int k4 = 24624 + l * 4 + (103 - i) * 512 * 4;
            int i5 = k1 >> 14 & 0x7fff;
            ObjectDef class46_2 = ObjectDef.forID(i5);
            if (class46_2.anInt758 != -1) {
                Background background_2 = mapScenes[class46_2.anInt758];
                if (background_2 != null) {
                    int i6 = (class46_2.anInt744 * 4 - background_2.bgWidth) / 2;
                    int j6 = (class46_2.anInt761 * 4 - background_2.bgHeight) / 2;
                    background_2.draw(48 + l * 4 + i6, 48 + (104 - i - class46_2.anInt761) * 4 + j6);
                }
            } else {
                if (i3 == 0 || i3 == 2) {
                    if (k2 == 0) {
                        ai[k4] = k3;
                        ai[k4 + 512] = k3;
                        ai[k4 + 1024] = k3;
                        ai[k4 + 1536] = k3;
                    } else if (k2 == 1) {
                        ai[k4] = k3;
                        ai[k4 + 1] = k3;
                        ai[k4 + 2] = k3;
                        ai[k4 + 3] = k3;
                    } else if (k2 == 2) {
                        ai[k4 + 3] = k3;
                        ai[k4 + 3 + 512] = k3;
                        ai[k4 + 3 + 1024] = k3;
                        ai[k4 + 3 + 1536] = k3;
                    } else if (k2 == 3) {
                        ai[k4 + 1536] = k3;
                        ai[k4 + 1536 + 1] = k3;
                        ai[k4 + 1536 + 2] = k3;
                        ai[k4 + 1536 + 3] = k3;
                    }
                }
                if (i3 == 3) {
                    if (k2 == 0) {
                        ai[k4] = k3;
                    } else if (k2 == 1) {
                        ai[k4 + 3] = k3;
                    } else if (k2 == 2) {
                        ai[k4 + 3 + 1536] = k3;
                    } else if (k2 == 3) {
                        ai[k4 + 1536] = k3;
                    }
                }
                if (i3 == 2) {
                    if (k2 == 3) {
                        ai[k4] = k3;
                        ai[k4 + 512] = k3;
                        ai[k4 + 1024] = k3;
                        ai[k4 + 1536] = k3;
                    } else if (k2 == 0) {
                        ai[k4] = k3;
                        ai[k4 + 1] = k3;
                        ai[k4 + 2] = k3;
                        ai[k4 + 3] = k3;
                    } else if (k2 == 1) {
                        ai[k4 + 3] = k3;
                        ai[k4 + 3 + 512] = k3;
                        ai[k4 + 3 + 1024] = k3;
                        ai[k4 + 3 + 1536] = k3;
                    } else if (k2 == 2) {
                        ai[k4 + 1536] = k3;
                        ai[k4 + 1536 + 1] = k3;
                        ai[k4 + 1536 + 2] = k3;
                        ai[k4 + 1536 + 3] = k3;
                    }
                }
            }
        }
        k1 = worldController.getObject5Uid(j1, l, i);
        if (k1 != 0) {
            int i2 = worldController.method304(j1, l, i, k1);
            int l2 = i2 >> 6 & 3;
            int j3 = i2 & 0x1f;
            int l3 = k1 >> 14 & 0x7fff;
            ObjectDef class46_1 = ObjectDef.forID(l3);
            if (class46_1.anInt758 != -1) {
                Background background_1 = mapScenes[class46_1.anInt758];
                if (background_1 != null) {
                    int j5 = (class46_1.anInt744 * 4 - background_1.bgWidth) / 2;
                    int k5 = (class46_1.anInt761 * 4 - background_1.bgHeight) / 2;
                    background_1.draw(48 + l * 4 + j5, 48 + (104 - i - class46_1.anInt761) * 4 + k5);
                }
            } else if (j3 == 9) {
                int l4 = 0xeeeeee;
                if (k1 > 0) {
                    l4 = 0xee0000;
                }
                int ai1[] = sprite.spritePixels;
                int l5 = 24624 + l * 4 + (103 - i) * 512 * 4;
                if (l2 == 0 || l2 == 2) {
                    ai1[l5 + 1536] = l4;
                    ai1[l5 + 1024 + 1] = l4;
                    ai1[l5 + 512 + 2] = l4;
                    ai1[l5 + 3] = l4;
                } else {
                    ai1[l5] = l4;
                    ai1[l5 + 512 + 1] = l4;
                    ai1[l5 + 1024 + 2] = l4;
                    ai1[l5 + 1536 + 3] = l4;
                }
            }
        }
        k1 = worldController.getObject3Uid(j1, l, i);
        if (k1 != 0) {
            int j2 = k1 >> 14 & 0x7fff;
            ObjectDef class46 = ObjectDef.forID(j2);
            if (class46.anInt758 != -1) {
                Background background = mapScenes[class46.anInt758];
                if (background != null) {
                    int i4 = (class46.anInt744 * 4 - background.bgWidth) / 2;
                    int j4 = (class46.anInt761 * 4 - background.bgHeight) / 2;
                    background.draw(48 + l * 4 + i4, 48 + (104 - i - class46.anInt761) * 4 + j4);
                }
            }
        }
    }

    private void loadTitleScreen() {
        titleBoxBackground = new Background(titleStreamLoader, "titlebox", 0);
        buttonBackground = new Background(titleStreamLoader, "titlebutton", 0);
        aBackgroundArray1152s = new Background[12];
        int j = 0;
        try {
            j = Integer.parseInt(getParameter("fl_icon"));
        } catch (Exception _ex) {
        }
        if (j == 0) {
            for (int k = 0; k < 12; k++) {
                aBackgroundArray1152s[k] = new Background(titleStreamLoader, "runes", k);
            }

        } else {
            for (int l = 0; l < 12; l++) {
                aBackgroundArray1152s[l] = new Background(titleStreamLoader, "runes", 12 + (l & 3));
            }

        }
        leftFlameSprite = new Sprite(128, 265);
        rightFlameSprite = new Sprite(128, 265);
        System.arraycopy(leftFlameImageProducer.pixels, 0, leftFlameSprite.spritePixels, 0, 33920);

        System.arraycopy(rightFlameImageProducer.pixels, 0, rightFlameSprite.spritePixels, 0, 33920);

        anIntArray851 = new int[256];
        for (int k1 = 0; k1 < 64; k1++) {
            anIntArray851[k1] = k1 * 0x40000;
        }

        for (int l1 = 0; l1 < 64; l1++) {
            anIntArray851[l1 + 64] = 0xff0000 + 1024 * l1;
        }

        for (int i2 = 0; i2 < 64; i2++) {
            anIntArray851[i2 + 128] = 0xffff00 + 4 * i2;
        }

        for (int j2 = 0; j2 < 64; j2++) {
            anIntArray851[j2 + 192] = 0xffffff;
        }

        anIntArray852 = new int[256];
        for (int k2 = 0; k2 < 64; k2++) {
            anIntArray852[k2] = k2 * 1024;
        }

        for (int l2 = 0; l2 < 64; l2++) {
            anIntArray852[l2 + 64] = 65280 + 4 * l2;
        }

        for (int i3 = 0; i3 < 64; i3++) {
            anIntArray852[i3 + 128] = 65535 + 0x40000 * i3;
        }

        for (int j3 = 0; j3 < 64; j3++) {
            anIntArray852[j3 + 192] = 0xffffff;
        }

        anIntArray853 = new int[256];
        for (int k3 = 0; k3 < 64; k3++) {
            anIntArray853[k3] = k3 * 4;
        }

        for (int l3 = 0; l3 < 64; l3++) {
            anIntArray853[l3 + 64] = 255 + 0x40000 * l3;
        }

        for (int i4 = 0; i4 < 64; i4++) {
            anIntArray853[i4 + 128] = 0xff00ff + 1024 * i4;
        }

        for (int j4 = 0; j4 < 64; j4++) {
            anIntArray853[j4 + 192] = 0xffffff;
        }

        anIntArray850 = new int[256];
        anIntArray1190 = new int[32768];
        anIntArray1191 = new int[32768];
        randomizeBackground(null);
        anIntArray828 = new int[32768];
        anIntArray829 = new int[32768];
        drawLoadingText(10, "Connecting to fileserver");
        if (!aBoolean831) {
            drawFlames = true;
            aBoolean831 = true;
            startRunnable(this, 2);
        }
    }

    private void loadingStages() {
        if (lowMem && loadingStage == 2 && ObjectManager.anInt131 != plane) {
            aRSImageProducer_1165.initDrawingArea();
            loadingTextArea.drawText(0, "Loading - please wait.", 151, 257);
            loadingTextArea.drawText(0xffffff, "Loading - please wait.", 150, 256);
            aRSImageProducer_1165.drawGraphics(super.graphics, 4, 4);
            loadingStage = 1;
            aLong824 = System.currentTimeMillis();
        }
        if (loadingStage == 1) {
            int j = method54();
            if (j != 0 && System.currentTimeMillis() - aLong824 > 0x57e40L) {
                Signlink.printError(myUsername + " glcfb " + serverSessionKey + "," + j + "," + lowMem + "," + decompressors[0] + "," + onDemandFetcher.getNodeCount() + "," + plane + "," + anInt1069 + "," + anInt1070);
                aLong824 = System.currentTimeMillis();
            }
        }
        if (loadingStage == 2 && plane != anInt985) {
            anInt985 = plane;
            method24(plane);
        }
    }

    private int method54() {
        for (int i = 0; i < aByteArrayArray1183.length; i++) {
            if (aByteArrayArray1183[i] == null && anIntArray1235[i] != -1) {
                return -1;
            }
            
            if (aByteArrayArray1247[i] == null && anIntArray1236[i] != -1) {
                return -2;
            }
        }
        boolean flag = true;
        
        for (int j = 0; j < aByteArrayArray1183.length; j++) {
            byte abyte0[] = aByteArrayArray1247[j];
            
            if (abyte0 != null) {
                int k = (anIntArray1234[j] >> 8) * 64 - baseX;
                int l = (anIntArray1234[j] & 0xff) * 64 - baseY;
                
                if (aBoolean1159) {
                    k = 10;
                    l = 10;
                }
                flag &= ObjectManager.method189(k, abyte0, l);
            }
        }

        if (!flag) {
            return -3;
        }
        
        if (aBoolean1080) {
            return -4;
        } else {
            loadingStage = 2;
            ObjectManager.anInt131 = plane;
            method22();
            stream.writePacketHeaderEnc(121);
            return 0;
        }
    }

    private void method55() {
        for (Animable_Sub4 anim = (Animable_Sub4) aClass19_1013.reverseGetFirst(); anim != null; anim = (Animable_Sub4) aClass19_1013.reverseGetNext()) {
            if (anim.anInt1597 != plane || loopCycle > anim.anInt1572) {
                anim.unlink();
            } else if (loopCycle >= anim.anInt1571) {
                if (anim.anInt1590 > 0) {
                    NPC npc = npcArray[anim.anInt1590 - 1];

                    if (npc != null && npc.x >= 0 && npc.x < 13312 && npc.y >= 0 && npc.y < 13312) {
                        anim.method455(loopCycle, npc.y, method42(anim.anInt1597, npc.y, npc.x) - anim.anInt1583, npc.x);
                    }
                }
                if (anim.anInt1590 < 0) {
                    int j = -anim.anInt1590 - 1;
                    Player player;

                    if (j == playerListIndex) {
                        player = myPlayer;
                    } else {
                        player = playerArray[j];
                    }

                    if (player != null && player.x >= 0 && player.x < 13312 && player.y >= 0 && player.y < 13312) {
                        anim.method455(loopCycle, player.y, method42(anim.anInt1597, player.y, player.x) - anim.anInt1583, player.x);
                    }
                }
                anim.method456(anInt945);
                worldController.method285(plane, anim.anInt1595, (int) anim.aDouble1587, -1, (int) anim.aDouble1586, 60, (int) anim.aDouble1585, anim, false);
            }
        }
    }

    public AppletContext getAppletContext() {
        if (Signlink.mainapp != null) {
            return Signlink.mainapp.getAppletContext();
        } else {
            return super.getAppletContext();
        }
    }

    private void drawLogo() {
        byte abyte0[] = titleStreamLoader.getDataForName("title.dat");
        Sprite sprite = new Sprite(abyte0, this);
        leftFlameImageProducer.initDrawingArea();
        sprite.method346(0, 0);
        rightFlameImageProducer.initDrawingArea();
        sprite.method346(-637, 0);
        aRSImageProducer_1107.initDrawingArea();
        sprite.method346(-128, 0);
        aRSImageProducer_1108.initDrawingArea();
        sprite.method346(-202, -371);
        aRSImageProducer_1109.initDrawingArea();
        sprite.method346(-202, -171);
        aRSImageProducer_1112.initDrawingArea();
        sprite.method346(0, -265);
        aRSImageProducer_1113.initDrawingArea();
        sprite.method346(-562, -265);
        aRSImageProducer_1114.initDrawingArea();
        sprite.method346(-128, -171);
        aRSImageProducer_1115.initDrawingArea();
        sprite.method346(-562, -171);
        int ai[] = new int[sprite.spriteWidth];
        
        for (int j = 0; j < sprite.spriteHeight; j++) {
            for (int k = 0; k < sprite.spriteWidth; k++) {
                ai[k] = sprite.spritePixels[(sprite.spriteWidth - k - 1) + sprite.spriteWidth * j];
            }
            System.arraycopy(ai, 0, sprite.spritePixels, sprite.spriteWidth * j, sprite.spriteWidth);
        }
        leftFlameImageProducer.initDrawingArea();
        sprite.method346(382, 0);
        rightFlameImageProducer.initDrawingArea();
        sprite.method346(-255, 0);
        aRSImageProducer_1107.initDrawingArea();
        sprite.method346(254, 0);
        aRSImageProducer_1108.initDrawingArea();
        sprite.method346(180, -371);
        aRSImageProducer_1109.initDrawingArea();
        sprite.method346(180, -171);
        aRSImageProducer_1112.initDrawingArea();
        sprite.method346(382, -265);
        aRSImageProducer_1113.initDrawingArea();
        sprite.method346(-180, -265);
        aRSImageProducer_1114.initDrawingArea();
        sprite.method346(254, -171);
        aRSImageProducer_1115.initDrawingArea();
        sprite.method346(-180, -171);
        sprite = new Sprite(titleStreamLoader, "logo", 0);
        aRSImageProducer_1107.initDrawingArea();
        sprite.drawSprite(382 - sprite.spriteWidth / 2 - 128, 18);
        sprite = null;
        System.gc();
    }

    private void processOnDemandQueue() {
        do {
            OnDemandData onDemandData;
            
            do {
                onDemandData = onDemandFetcher.getNextNode();
                
                if (onDemandData == null) {
                    return;
                }
                
                if (onDemandData.dataType == 0) {
                    Model.method460(onDemandData.buffer, onDemandData.ID);
                    
                    if ((onDemandFetcher.getModelIndex(onDemandData.ID) & 0x62) != 0) {
                        needDrawTabArea = true;

                        if (backDialogID != -1) {
                            inputTaken = true;
                        }
                    }
                }
                
                if (onDemandData.dataType == 1 && onDemandData.buffer != null) {
                    Class36.method529(onDemandData.buffer);
                }
                
                if (onDemandData.dataType == 2 && onDemandData.ID == nextSong && onDemandData.buffer != null) {
                    saveMidi(songChanging, onDemandData.buffer);
                }
                
                if (onDemandData.dataType == 3 && loadingStage == 1) {
                    for (int i = 0; i < aByteArrayArray1183.length; i++) {
                        if (anIntArray1235[i] == onDemandData.ID) {
                            aByteArrayArray1183[i] = onDemandData.buffer;
                            
                            if (onDemandData.buffer == null) {
                                anIntArray1235[i] = -1;
                            }
                            break;
                        }
                        
                        if (anIntArray1236[i] != onDemandData.ID) {
                            continue;
                        }
                        aByteArrayArray1247[i] = onDemandData.buffer;
                        
                        if (onDemandData.buffer == null) {
                            anIntArray1236[i] = -1;
                        }
                        break;
                    }

                }
            } while (onDemandData.dataType != 93 || !onDemandFetcher.method564(onDemandData.ID));
            ObjectManager.method173(new Stream(onDemandData.buffer), onDemandFetcher);
        } while (true);
    }

    private void calcFlamesPosition() {
        int c = 256;

        for (int j = 10; j < 117; j++) {
            int k = (int) (Math.random() * 100D);
            if (k < 50) {
                anIntArray828[j + (c - 2 << 7)] = 255;
            }
        }
        
        for (int l = 0; l < 100; l++) {
            int i1 = (int) (Math.random() * 124D) + 2;
            int k1 = (int) (Math.random() * 128D) + 128;
            int k2 = i1 + (k1 << 7);
            anIntArray828[k2] = 192;
        }

        for (int j1 = 1; j1 < c - 1; j1++) {
            for (int l1 = 1; l1 < 127; l1++) {
                int l2 = l1 + (j1 << 7);
                anIntArray829[l2] = (anIntArray828[l2 - 1] + anIntArray828[l2 + 1] + anIntArray828[l2 - 128] + anIntArray828[l2 + 128]) / 4;
            }
        }
        anInt1275 += 128;
        
        if (anInt1275 > anIntArray1190.length) {
            anInt1275 -= anIntArray1190.length;
            int i2 = (int) (Math.random() * 12D);
            randomizeBackground(aBackgroundArray1152s[i2]);
        }
        
        for (int j2 = 1; j2 < c - 1; j2++) {
            for (int i3 = 1; i3 < 127; i3++) {
                int k3 = i3 + (j2 << 7);
                int i4 = anIntArray829[k3 + 128] - anIntArray1190[k3 + anInt1275 & anIntArray1190.length - 1] / 5;
                
                if (i4 < 0) {
                    i4 = 0;
                }
                anIntArray828[k3] = i4;
            }
        }
        System.arraycopy(anIntArray969, 1, anIntArray969, 0, c - 1);

        anIntArray969[c - 1] = (int) (Math.sin((double) loopCycle / 14D) * 16D + Math.sin((double) loopCycle / 15D) * 14D + Math.sin((double) loopCycle / 16D) * 12D);

        if (anInt1040 > 0) {
            anInt1040 -= 4;
        }
        if (anInt1041 > 0) {
            anInt1041 -= 4;
        }
        if (anInt1040 == 0 && anInt1041 == 0) {
            int l3 = (int) (Math.random() * 2000D);
            if (l3 == 0) {
                anInt1040 = 1024;
            }
            if (l3 == 1) {
                anInt1041 = 1024;
            }
        }
    }

    private boolean saveWave(byte buf[], int length) {
        return buf == null || Signlink.saveWave(buf, length);
    }

    private void showWalkableInterface(int interfaceId) {
        RSInterface parentInterface = RSInterface.interfaceCache[interfaceId];
        
        for (int childId = 0; childId < parentInterface.children.length; childId++) {
            if (parentInterface.children[childId] == -1) {
                break;
            }
            RSInterface childInterface = RSInterface.interfaceCache[parentInterface.children[childId]];
            
            if (childInterface.type == 1) {
                showWalkableInterface(childInterface.id);
            }
            childInterface.anInt246 = 0;
            childInterface.anInt208 = 0;
        }
    }

    private void drawHeadIcon() {
        if (iconType != 2) {
            return;
        }
        calcEntityScreenPos((iconX - baseX << 7) + anInt937, iconDrawHeight * 2, (iconY - baseY << 7) + anInt938);

        if (spriteDrawX > -1 && loopCycle % 20 < 10) {
            headIcons[2].drawSprite(spriteDrawX - 12, spriteDrawY - 28);
        }
    }

    private void mainGameProcessor() {
        if (timeUntilSystemUpdate > 1) {
            timeUntilSystemUpdate--;
        }
        if (anInt1011 > 0) {
            anInt1011--;
        }
        for (int j = 0; j < 5; j++) {
            if (!parsePacket()) {
                break;
            }
        }

        if (!loggedIn) {
            return;
        }
        synchronized (mouseDetection.syncObject) {
            if (flagged) {
                if (super.clickMode3 != 0 || mouseDetection.coordsIndex >= 40) {
                    stream.writePacketHeaderEnc(45);
                    stream.writeByte(0);
                    int j2 = stream.currentOffset;
                    int j3 = 0;

                    for (int j4 = 0; j4 < mouseDetection.coordsIndex; j4++) {
                        if (j2 - stream.currentOffset >= 240) {
                            break;
                        }
                        j3++;
                        int l4 = mouseDetection.coordsY[j4];

                        if (l4 < 0) {
                            l4 = 0;
                        } else if (l4 > 502) {
                            l4 = 502;
                        }
                        int k5 = mouseDetection.coordsX[j4];

                        if (k5 < 0) {
                            k5 = 0;
                        } else if (k5 > 764) {
                            k5 = 764;
                        }
                        int i6 = l4 * 765 + k5;

                        if (mouseDetection.coordsY[j4] == -1 && mouseDetection.coordsX[j4] == -1) {
                            k5 = -1;
                            l4 = -1;
                            i6 = 0x7ffff;
                        }

                        if (k5 == anInt1237 && l4 == anInt1238) {
                            if (anInt1022 < 2047) {
                                anInt1022++;
                            }
                        } else {
                            int j6 = k5 - anInt1237;
                            anInt1237 = k5;
                            int k6 = l4 - anInt1238;
                            anInt1238 = l4;

                            if (anInt1022 < 8 && j6 >= -32 && j6 <= 31 && k6 >= -32 && k6 <= 31) {
                                j6 += 32;
                                k6 += 32;
                                stream.writeShort((anInt1022 << 12) + (j6 << 6) + k6);
                                anInt1022 = 0;
                            } else if (anInt1022 < 8) {
                                stream.writeTriByte(0x800000 + (anInt1022 << 19) + i6);
                                anInt1022 = 0;
                            } else {
                                stream.writeInt(0xc0000000 + (anInt1022 << 19) + i6);
                                anInt1022 = 0;
                            }
                        }
                    }
                    stream.writeByteXXX(stream.currentOffset - j2);

                    if (j3 >= mouseDetection.coordsIndex) {
                        mouseDetection.coordsIndex = 0;
                    } else {
                        mouseDetection.coordsIndex -= j3;
                        for (int i5 = 0; i5 < mouseDetection.coordsIndex; i5++) {
                            mouseDetection.coordsX[i5] = mouseDetection.coordsX[i5 + j3];
                            mouseDetection.coordsY[i5] = mouseDetection.coordsY[i5 + j3];
                        }

                    }
                }
            } else {
                mouseDetection.coordsIndex = 0;
            }
        }

        if (super.clickMode3 != 0) {
            long l = (super.aLong29 - aLong1220) / 50L;
            if (l > 4095L) {
                l = 4095L;
            }
            aLong1220 = super.aLong29;
            int k2 = super.saveClickY;
            if (k2 < 0) {
                k2 = 0;
            } else if (k2 > 502) {
                k2 = 502;
            }
            int k3 = super.saveClickX;
            if (k3 < 0) {
                k3 = 0;
            } else if (k3 > 764) {
                k3 = 764;
            }
            int k4 = k2 * 765 + k3;
            int j5 = 0;
            if (super.clickMode3 == 2) {
                j5 = 1;
            }
            int l5 = (int) l;
            stream.writePacketHeaderEnc(241);
            stream.writeInt((l5 << 20) + (j5 << 19) + k4);
        }
        if (anInt1016 > 0) {
            anInt1016--;
        }
        if (super.keyArray[1] == 1 || super.keyArray[2] == 1 || super.keyArray[3] == 1 || super.keyArray[4] == 1) {
            aBoolean1017 = true;
        }
        if (aBoolean1017 && anInt1016 <= 0) {
            anInt1016 = 20;
            aBoolean1017 = false;
            stream.writePacketHeaderEnc(86);
            stream.writeShort(anInt1184);
            stream.writeShortA(minimapInt1);
        }
        if (super.awtFocus && !aBoolean954) {
            aBoolean954 = true;
            stream.writePacketHeaderEnc(3);
            stream.writeByte(1);
        }
        if (!super.awtFocus && aBoolean954) {
            aBoolean954 = false;
            stream.writePacketHeaderEnc(3);
            stream.writeByte(0);
        }
        loadingStages();
        method115();
        method90();
        ticksSincelastPacketReceived++;

        if (ticksSincelastPacketReceived > 750) {
            connectionLost();
        }
        method114();
        method95();
        method38();
        anInt945++;
        if (crossType != 0) {
            crossIndex += 20;
            if (crossIndex >= 400) {
                crossType = 0;
            }
        }
        if (atInventoryInterfaceType != 0) {
            atInventoryLoopCycle++;
            if (atInventoryLoopCycle >= 15) {
                if (atInventoryInterfaceType == 2) {
                    needDrawTabArea = true;
                }
                if (atInventoryInterfaceType == 3) {
                    inputTaken = true;
                }
                atInventoryInterfaceType = 0;
            }
        }
        if (activeInterfaceType != 0) {
            anInt989++;
            if (super.mouseX > anInt1087 + 5 || super.mouseX < anInt1087 - 5 || super.mouseY > anInt1088 + 5 || super.mouseY < anInt1088 - 5) {
                aBoolean1242 = true;
            }
            if (super.clickMode2 == 0) {
                if (activeInterfaceType == 2) {
                    needDrawTabArea = true;
                }
                if (activeInterfaceType == 3) {
                    inputTaken = true;
                }
                activeInterfaceType = 0;
                if (aBoolean1242 && anInt989 >= 5) {
                    lastActiveInvInterface = -1;
                    processRightClick();
                    if (lastActiveInvInterface == anInt1084 && mouseInvInterfaceIndex != anInt1085) {
                        RSInterface class9 = RSInterface.interfaceCache[anInt1084];
                        int j1 = 0;
                        if (anInt913 == 1 && class9.anInt214 == 206) {
                            j1 = 1;
                        }
                        if (class9.inv[mouseInvInterfaceIndex] <= 0) {
                            j1 = 0;
                        }
                        if (class9.aBoolean235) {
                            int l2 = anInt1085;
                            int l3 = mouseInvInterfaceIndex;
                            class9.inv[l3] = class9.inv[l2];
                            class9.invStackSizes[l3] = class9.invStackSizes[l2];
                            class9.inv[l2] = -1;
                            class9.invStackSizes[l2] = 0;
                        } else if (j1 == 1) {
                            int i3 = anInt1085;
                            for (int i4 = mouseInvInterfaceIndex; i3 != i4;) {
                                if (i3 > i4) {
                                    class9.swapInventoryItems(i3, i3 - 1);
                                    i3--;
                                } else if (i3 < i4) {
                                    class9.swapInventoryItems(i3, i3 + 1);
                                    i3++;
                                }
                            }

                        } else {
                            class9.swapInventoryItems(anInt1085, mouseInvInterfaceIndex);
                        }
                        stream.writePacketHeaderEnc(214);
                        stream.writeShortLEA(anInt1084);
                        stream.writeByteC(j1);
                        stream.writeShortLEA(anInt1085);
                        stream.writeShortLE(mouseInvInterfaceIndex);
                    }
                } else if ((anInt1253 == 1 || menuHasAddFriend(menuActionRow - 1)) && menuActionRow > 2) {
                    determineMenuSize();
                } else if (menuActionRow > 0) {
                    doAction(menuActionRow - 1);
                }
                atInventoryLoopCycle = 10;
                super.clickMode3 = 0;
            }
        }
        if (WorldController.walkTargetX != -1) {
            int targetX = WorldController.walkTargetX;
            int targetY = WorldController.walkTargetY;
            boolean flag = walkTo(0, 0, 0, 0, myPlayer.smallY[0], 0, 0, targetY, myPlayer.smallX[0], true, targetX);
            WorldController.walkTargetX = -1;
            if (flag) {
                crossX = super.saveClickX;
                crossY = super.saveClickY;
                crossType = 1;
                crossIndex = 0;
            }
        }
        if (super.clickMode3 == 1 && aString844 != null) {
            aString844 = null;
            inputTaken = true;
            super.clickMode3 = 0;
        }
        processMenuClick();
        processMainScreenClick();
        processTabClick();
        processChatModeClick();
        if (super.clickMode2 == 1 || super.clickMode3 == 1) {
            anInt1213++;
        }
        if (loadingStage == 2) {
            method108();
        }
        if (loadingStage == 2 && aBoolean1160) {
            calcCameraPos();
        }
        for (int i1 = 0; i1 < 5; i1++) {
            anIntArray1030[i1]++;
        }

        method73();
        super.idleTime++;
        if (super.idleTime > 4500) {
            anInt1011 = 250;
            super.idleTime -= 500;
            stream.writePacketHeaderEnc(202);
        }
        anInt988++;
        if (anInt988 > 500) {
            anInt988 = 0;
            int l1 = (int) (Math.random() * 8D);
            if ((l1 & 1) == 1) {
                anInt1278 += anInt1279;
            }
            if ((l1 & 2) == 2) {
                anInt1131 += anInt1132;
            }
            if ((l1 & 4) == 4) {
                anInt896 += anInt897;
            }
        }
        if (anInt1278 < -50) {
            anInt1279 = 2;
        }
        if (anInt1278 > 50) {
            anInt1279 = -2;
        }
        if (anInt1131 < -55) {
            anInt1132 = 2;
        }
        if (anInt1131 > 55) {
            anInt1132 = -2;
        }
        if (anInt896 < -40) {
            anInt897 = 1;
        }
        if (anInt896 > 40) {
            anInt897 = -1;
        }
        anInt1254++;
        if (anInt1254 > 500) {
            anInt1254 = 0;
            int i2 = (int) (Math.random() * 8D);
            if ((i2 & 1) == 1) {
                minimapInt2 += anInt1210;
            }
            if ((i2 & 2) == 2) {
                minimapInt3 += anInt1171;
            }
        }
        if (minimapInt2 < -60) {
            anInt1210 = 2;
        }
        if (minimapInt2 > 60) {
            anInt1210 = -2;
        }
        if (minimapInt3 < -20) {
            anInt1171 = 1;
        }
        if (minimapInt3 > 10) {
            anInt1171 = -1;
        }
        anInt1010++;
        if (anInt1010 > 50) {
            stream.writePacketHeaderEnc(0);
        }

        try {
            if (socketStream != null && stream.currentOffset > 0) {
                socketStream.queueBytes(stream.currentOffset, stream.buffer);
                stream.currentOffset = 0;
                anInt1010 = 0;
            }
        } catch (IOException e) {
            connectionLost();
        } catch (Exception e) {
            resetLogout();
        }
    }

    private void method63() {
        Class30_Sub1 class30_sub1 = (Class30_Sub1) aClass19_1179.reverseGetFirst();
        for (; class30_sub1 != null; class30_sub1 = (Class30_Sub1) aClass19_1179.reverseGetNext()) {
            if (class30_sub1.anInt1294 == -1) {
                class30_sub1.anInt1302 = 0;
                method89(class30_sub1);
            } else {
                class30_sub1.unlink();
            }
        }
    }

    private void resetImageProducers() {
        if (aRSImageProducer_1107 != null) {
            return;
        }
        super.fullGameScreen = null;
        aRSImageProducer_1166 = null;
        aRSImageProducer_1164 = null;
        aRSImageProducer_1163 = null;
        aRSImageProducer_1165 = null;
        aRSImageProducer_1123 = null;
        aRSImageProducer_1124 = null;
        aRSImageProducer_1125 = null;
        leftFlameImageProducer = new RSImageProducer(128, 265, getGameComponent());
        DrawingArea.clear();
        rightFlameImageProducer = new RSImageProducer(128, 265, getGameComponent());
        DrawingArea.clear();
        aRSImageProducer_1107 = new RSImageProducer(509, 171, getGameComponent());
        DrawingArea.clear();
        aRSImageProducer_1108 = new RSImageProducer(360, 132, getGameComponent());
        DrawingArea.clear();
        aRSImageProducer_1109 = new RSImageProducer(360, 200, getGameComponent());
        DrawingArea.clear();
        aRSImageProducer_1112 = new RSImageProducer(202, 238, getGameComponent());
        DrawingArea.clear();
        aRSImageProducer_1113 = new RSImageProducer(203, 238, getGameComponent());
        DrawingArea.clear();
        aRSImageProducer_1114 = new RSImageProducer(74, 94, getGameComponent());
        DrawingArea.clear();
        aRSImageProducer_1115 = new RSImageProducer(75, 94, getGameComponent());
        DrawingArea.clear();

        if (titleStreamLoader != null) {
            drawLogo();
            loadTitleScreen();
        }
        welcomeScreenRaised = true;
    }

    void drawLoadingText(int percentage, String text) {
        loadingPercentage = percentage;
        loadingText = text;
        resetImageProducers();
        if (titleStreamLoader == null) {
            super.drawLoadingText(percentage, text);
            return;
        }
        aRSImageProducer_1109.initDrawingArea();
        char c = '\u0168';
        char c1 = '\310';
        byte byte1 = 20;
        chatTextDrawingArea.drawText(0xffffff, "RuneScape is loading - please wait...", c1 / 2 - 26 - byte1, c / 2);
        int j = c1 / 2 - 18 - byte1;
        DrawingArea.fillPixels(c / 2 - 152, 304, 34, 0x8c1111, j);
        DrawingArea.fillPixels(c / 2 - 151, 302, 32, 0, j + 1);
        DrawingArea.fillPixelsReverseOrder(30, j + 2, c / 2 - 150, 0x8c1111, percentage * 3);
        DrawingArea.fillPixelsReverseOrder(30, j + 2, (c / 2 - 150) + percentage * 3, 0, 300 - percentage * 3);
        chatTextDrawingArea.drawText(0xffffff, text, (c1 / 2 + 5) - byte1, c / 2);
        aRSImageProducer_1109.drawGraphics(super.graphics, 202, 171);

        if (welcomeScreenRaised) {
            welcomeScreenRaised = false;

            if (!aBoolean831) {
                leftFlameImageProducer.drawGraphics(super.graphics, 0, 0);
                rightFlameImageProducer.drawGraphics(super.graphics, 637, 0);
            }
            aRSImageProducer_1107.drawGraphics(super.graphics, 128, 0);
            aRSImageProducer_1108.drawGraphics(super.graphics, 202, 371);
            aRSImageProducer_1112.drawGraphics(super.graphics, 0, 265);
            aRSImageProducer_1113.drawGraphics(super.graphics, 562, 265);
            aRSImageProducer_1114.drawGraphics(super.graphics, 128, 171);
            aRSImageProducer_1115.drawGraphics(super.graphics, 562, 171);
        }
    }

    private void method65(int i, int j, int k, int l, RSInterface class9, int i1, boolean flag, int j1) {
        int anInt992;
        if (aBoolean972) {
            anInt992 = 32;
        } else {
            anInt992 = 0;
        }
        aBoolean972 = false;
        if (k >= i && k < i + 16 && l >= i1 && l < i1 + 16) {
            class9.scrollPosition -= anInt1213 * 4;
            if (flag) {
                needDrawTabArea = true;
            }
        } else if (k >= i && k < i + 16 && l >= (i1 + j) - 16 && l < i1 + j) {
            class9.scrollPosition += anInt1213 * 4;
            if (flag) {
                needDrawTabArea = true;
            }
        } else if (k >= i - anInt992 && k < i + 16 + anInt992 && l >= i1 + 16 && l < (i1 + j) - 16 && anInt1213 > 0) {
            int l1 = ((j - 32) * j) / j1;
            if (l1 < 8) {
                l1 = 8;
            }
            int i2 = l - i1 - 16 - l1 / 2;
            int j2 = j - 32 - l1;
            class9.scrollPosition = ((j1 - j) * i2) / j2;
            if (flag) {
                needDrawTabArea = true;
            }
            aBoolean972 = true;
        }
    }

    private boolean method66(int i, int j, int k) {
        int i1 = i >> 14 & 0x7fff;
        int j1 = worldController.method304(plane, k, j, i);
        if (j1 == -1) {
            return false;
        }
        int k1 = j1 & 0x1f;
        int l1 = j1 >> 6 & 3;
        if (k1 == 10 || k1 == 11 || k1 == 22) {
            ObjectDef class46 = ObjectDef.forID(i1);
            int i2;
            int j2;
            if (l1 == 0 || l1 == 2) {
                i2 = class46.anInt744;
                j2 = class46.anInt761;
            } else {
                i2 = class46.anInt761;
                j2 = class46.anInt744;
            }
            int k2 = class46.anInt768;
            if (l1 != 0) {
                k2 = (k2 << l1 & 0xf) + (k2 >> 4 - l1);
            }
            walkTo(2, 0, j2, 0, myPlayer.smallY[0], i2, k2, j, myPlayer.smallX[0], false, k);
        } else {
            walkTo(2, l1, 0, k1 + 1, myPlayer.smallY[0], 0, 0, j, myPlayer.smallX[0], false, k);
        }
        crossX = super.saveClickX;
        crossY = super.saveClickY;
        crossType = 2;
        crossIndex = 0;
        return true;
    }

    private StreamLoader streamLoaderForName(int i, String s, String s1, int j, int k) {
        byte buf[] = null;
        int l = 5;
        try {
            if (decompressors[0] != null) {
                buf = decompressors[0].decompress(i);
            }
        } catch (Exception _ex) {
        }
        if (buf != null) {
            //        aCRC32_930.reset();
            //        aCRC32_930.update(abyte0);
            //        int i1 = (int)aCRC32_930.getValue();
            //        if(i1 != j)
        }
        if (buf != null) {
            StreamLoader streamLoader = new StreamLoader(buf);
            return streamLoader;
        }
        int j1 = 0;
        while (buf == null) {
            String s2 = "Unknown error";
            drawLoadingText(k, "Requesting " + s);
            Object obj = null;
            try {
                int k1 = 0;
                DataInputStream datainputstream = openJagGrabInputStream(s1 + j);
                byte abyte1[] = new byte[6];
                datainputstream.readFully(abyte1, 0, 6);
                Stream stream = new Stream(abyte1);
                stream.currentOffset = 3;
                int i2 = stream.readUTriByte() + 6;
                int j2 = 6;
                buf = new byte[i2];
                System.arraycopy(abyte1, 0, buf, 0, 6);

                while (j2 < i2) {
                    int l2 = i2 - j2;
                    if (l2 > 1000) {
                        l2 = 1000;
                    }
                    int j3 = datainputstream.read(buf, j2, l2);
                    if (j3 < 0) {
                        s2 = "Length error: " + j2 + "/" + i2;
                        throw new IOException("EOF");
                    }
                    j2 += j3;
                    int k3 = (j2 * 100) / i2;
                    if (k3 != k1) {
                        drawLoadingText(k, "Loading " + s + " - " + k3 + "%");
                    }
                    k1 = k3;
                }
                datainputstream.close();
                
                try {
                    if (decompressors[0] != null) {
                        decompressors[0].method234(buf.length, buf, i);
                    }
                } catch (Exception _ex) {
                    decompressors[0] = null;
                }
                /* 
                 if(abyte0 != null)
                 {
                 aCRC32_930.reset();
                 aCRC32_930.update(abyte0);
                 int i3 = (int)aCRC32_930.getValue();
                 if(i3 != j)
                 {
                 abyte0 = null;
                 j1++;
                 s2 = "Checksum error: " + i3;
                 }
                 }
                 */
            } catch (IOException e) {
                if (s2.equals("Unknown error")) {
                    s2 = "Connection error";
                }
                buf = null;
            } catch (NullPointerException e) {
                s2 = "Null error";
                buf = null;
                
                if (!Signlink.reporterror) {
                    return null;
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                s2 = "Bounds error";
                buf = null;
                
                if (!Signlink.reporterror) {
                    return null;
                }
            } catch (Exception _ex) {
                s2 = "Unexpected error";
                buf = null;
                
                if (!Signlink.reporterror) {
                    return null;
                }
            }
            
            if (buf == null) {
                for (int time = l; time > 0; time--) {
                    if (j1 >= 3) {
                        drawLoadingText(k, "Game updated - please reload page");
                        time = 10;
                    } else {
                        drawLoadingText(k, s2 + " - Retrying in " + time);
                    }
                    
                    try {
                        Thread.sleep(1000L);
                    } catch (InterruptedException _ex) {
                    }
                }
                l *= 2;
                
                if (l > 60) {
                    l = 60;
                }
                aBoolean872 = !aBoolean872;
            }
        }
        return new StreamLoader(buf);
    }

    /**
     * Marks the client as having lost connection.
     */
    private void connectionLost() {
        if (anInt1011 > 0) {
            resetLogout();
            return;
        }
        aRSImageProducer_1165.initDrawingArea();
        loadingTextArea.drawText(0, "Connection lost", 144, 257);
        loadingTextArea.drawText(0xffffff, "Connection lost", 143, 256);
        loadingTextArea.drawText(0, "Please wait - attempting to reestablish", 159, 257);
        loadingTextArea.drawText(0xffffff, "Please wait - attempting to reestablish", 158, 256);
        aRSImageProducer_1165.drawGraphics(super.graphics, 4, 4);
        minimapState = 0;
        destX = 0;
        loggedIn = false;
        loginFailures = 0;
        login(myUsername, myPassword, true);
        
        if (!loggedIn) {
            resetLogout();
        }
        
        try {
            socketStream.close();
        } catch (Exception _ex) {
        }
    }

    private void doAction(int i) {
        if (i < 0) {
            return;
        }
        if (inputDialogState != 0) {
            inputDialogState = 0;
            inputTaken = true;
        }
        int j = menuActionCmd2[i];
        int k = menuActionCmd3[i];
        int l = menuActionID[i];
        int i1 = menuActionCmd1[i];
        if (l >= 2000) {
            l -= 2000;
        }
        if (l == 582) {
            NPC npc = npcArray[i1];
            if (npc != null) {
                walkTo(2, 0, 1, 0, myPlayer.smallY[0], 1, 0, npc.smallY[0], myPlayer.smallX[0], false, npc.smallX[0]);
                crossX = super.saveClickX;
                crossY = super.saveClickY;
                crossType = 2;
                crossIndex = 0;
                stream.writePacketHeaderEnc(57);
                stream.writeShortA(anInt1285);
                stream.writeShortA(i1);
                stream.writeShortLE(anInt1283);
                stream.writeShortA(anInt1284);
            }
        }
        if (l == 234) {
            boolean flag1 = walkTo(2, 0, 0, 0, myPlayer.smallY[0], 0, 0, k, myPlayer.smallX[0], false, j);
            if (!flag1) {
                flag1 = walkTo(2, 0, 1, 0, myPlayer.smallY[0], 1, 0, k, myPlayer.smallX[0], false, j);
            }
            crossX = super.saveClickX;
            crossY = super.saveClickY;
            crossType = 2;
            crossIndex = 0;
            stream.writePacketHeaderEnc(236);
            stream.writeShortLE(k + baseY);
            stream.writeShort(i1);
            stream.writeShortLE(j + baseX);
        }
        if (l == 62 && method66(i1, k, j)) {
            stream.writePacketHeaderEnc(192);
            stream.writeShort(anInt1284);
            stream.writeShortLE(i1 >> 14 & 0x7fff);
            stream.writeShortLEA(k + baseY);
            stream.writeShortLE(anInt1283);
            stream.writeShortLEA(j + baseX);
            stream.writeShort(anInt1285);
        }
        if (l == 511) {
            boolean flag2 = walkTo(2, 0, 0, 0, myPlayer.smallY[0], 0, 0, k, myPlayer.smallX[0], false, j);
            if (!flag2) {
                flag2 = walkTo(2, 0, 1, 0, myPlayer.smallY[0], 1, 0, k, myPlayer.smallX[0], false, j);
            }
            crossX = super.saveClickX;
            crossY = super.saveClickY;
            crossType = 2;
            crossIndex = 0;
            stream.writePacketHeaderEnc(25);
            stream.writeShortLE(anInt1284);
            stream.writeShortA(anInt1285);
            stream.writeShort(i1);
            stream.writeShortA(k + baseY);
            stream.writeShortLEA(anInt1283);
            stream.writeShort(j + baseX);
        }
        if (l == 74) {
            stream.writePacketHeaderEnc(122);
            stream.writeShortLEA(k);
            stream.writeShortA(j);
            stream.writeShortLE(i1);
            atInventoryLoopCycle = 0;
            atInventoryInterface = k;
            atInventoryIndex = j;
            atInventoryInterfaceType = 2;
            if (RSInterface.interfaceCache[k].parentID == openInterfaceID) {
                atInventoryInterfaceType = 1;
            }
            if (RSInterface.interfaceCache[k].parentID == backDialogID) {
                atInventoryInterfaceType = 3;
            }
        }
        if (l == 315) {
            RSInterface class9 = RSInterface.interfaceCache[k];
            boolean flag8 = true;
            if (class9.anInt214 > 0) {
                flag8 = promptUserForInput(class9);
            }
            if (flag8) {
                stream.writePacketHeaderEnc(185);
                stream.writeShort(k);
            }
        }
        if (l == 561) {
            Player player = playerArray[i1];
            if (player != null) {
                walkTo(2, 0, 1, 0, myPlayer.smallY[0], 1, 0, player.smallY[0], myPlayer.smallX[0], false, player.smallX[0]);
                crossX = super.saveClickX;
                crossY = super.saveClickY;
                crossType = 2;
                crossIndex = 0;
                anInt1188 += i1;
                if (anInt1188 >= 90) {
                    stream.writePacketHeaderEnc(136);
                    anInt1188 = 0;
                }
                stream.writePacketHeaderEnc(128);
                stream.writeShort(i1);
            }
        }
        if (l == 20) {
            NPC class30_sub2_sub4_sub1_sub1_1 = npcArray[i1];
            if (class30_sub2_sub4_sub1_sub1_1 != null) {
                walkTo(2, 0, 1, 0, myPlayer.smallY[0], 1, 0, class30_sub2_sub4_sub1_sub1_1.smallY[0], myPlayer.smallX[0], false, class30_sub2_sub4_sub1_sub1_1.smallX[0]);
                crossX = super.saveClickX;
                crossY = super.saveClickY;
                crossType = 2;
                crossIndex = 0;
                stream.writePacketHeaderEnc(155);
                stream.writeShortLE(i1);
            }
        }
        if (l == 779) {
            Player class30_sub2_sub4_sub1_sub2_1 = playerArray[i1];
            if (class30_sub2_sub4_sub1_sub2_1 != null) {
                walkTo(2, 0, 1, 0, myPlayer.smallY[0], 1, 0, class30_sub2_sub4_sub1_sub2_1.smallY[0], myPlayer.smallX[0], false, class30_sub2_sub4_sub1_sub2_1.smallX[0]);
                crossX = super.saveClickX;
                crossY = super.saveClickY;
                crossType = 2;
                crossIndex = 0;
                stream.writePacketHeaderEnc(153);
                stream.writeShortLE(i1);
            }
        }
        if (l == 516) {
            if (!menuOpen) {
                worldController.method312(super.saveClickY - 4, super.saveClickX - 4);
            } else {
                worldController.method312(k - 4, j - 4);
            }
        }
        if (l == 1062) {
            anInt924 += baseX;
            if (anInt924 >= 113) {
                stream.writePacketHeaderEnc(183);
                stream.writeTriByte(0xe63271);
                anInt924 = 0;
            }
            method66(i1, k, j);
            stream.writePacketHeaderEnc(228);
            stream.writeShortA(i1 >> 14 & 0x7fff);
            stream.writeShortA(k + baseY);
            stream.writeShort(j + baseX);
        }
        if (l == 679 && !aBoolean1149) {
            stream.writePacketHeaderEnc(40);
            stream.writeShort(k);
            aBoolean1149 = true;
        }
        if (l == 431) {
            stream.writePacketHeaderEnc(129);
            stream.writeShortA(j);
            stream.writeShort(k);
            stream.writeShortA(i1);
            atInventoryLoopCycle = 0;
            atInventoryInterface = k;
            atInventoryIndex = j;
            atInventoryInterfaceType = 2;
            if (RSInterface.interfaceCache[k].parentID == openInterfaceID) {
                atInventoryInterfaceType = 1;
            }
            if (RSInterface.interfaceCache[k].parentID == backDialogID) {
                atInventoryInterfaceType = 3;
            }
        }
        if (l == 337 || l == 42 || l == 792 || l == 322) {
            String playerName = menuActionName[i];
            int k1 = playerName.indexOf("@whi@");

            if (k1 != -1) {
                long name = StringHelper.longForName(playerName.substring(k1 + 5).trim());

                if (l == 337) {
                    addFriend(name);
                }
                if (l == 42) {
                    addIgnore(name);
                }
                if (l == 792) {
                    delFriend(name);
                }
                if (l == 322) {
                    removeIgnore(name);
                }
            }
        }
        if (l == 53) {
            stream.writePacketHeaderEnc(135);
            stream.writeShortLE(j);
            stream.writeShortA(k);
            stream.writeShortLE(i1);
            atInventoryLoopCycle = 0;
            atInventoryInterface = k;
            atInventoryIndex = j;
            atInventoryInterfaceType = 2;
            if (RSInterface.interfaceCache[k].parentID == openInterfaceID) {
                atInventoryInterfaceType = 1;
            }
            if (RSInterface.interfaceCache[k].parentID == backDialogID) {
                atInventoryInterfaceType = 3;
            }
        }
        if (l == 539) {
            stream.writePacketHeaderEnc(16);
            stream.writeShortA(i1);
            stream.writeShortLEA(j);
            stream.writeShortLEA(k);
            atInventoryLoopCycle = 0;
            atInventoryInterface = k;
            atInventoryIndex = j;
            atInventoryInterfaceType = 2;
            if (RSInterface.interfaceCache[k].parentID == openInterfaceID) {
                atInventoryInterfaceType = 1;
            }
            if (RSInterface.interfaceCache[k].parentID == backDialogID) {
                atInventoryInterfaceType = 3;
            }
        }
        if (l == 484 || l == 6) {
            String s1 = menuActionName[i];
            int l1 = s1.indexOf("@whi@");
            if (l1 != -1) {
                s1 = s1.substring(l1 + 5).trim();
                String s7 = StringHelper.fixName(StringHelper.nameForLong(StringHelper.longForName(s1)));
                boolean flag9 = false;
                for (int j3 = 0; j3 < playerCount; j3++) {
                    Player class30_sub2_sub4_sub1_sub2_7 = playerArray[playerIndices[j3]];
                    if (class30_sub2_sub4_sub1_sub2_7 == null || class30_sub2_sub4_sub1_sub2_7.name == null || !class30_sub2_sub4_sub1_sub2_7.name.equalsIgnoreCase(s7)) {
                        continue;
                    }
                    walkTo(2, 0, 1, 0, myPlayer.smallY[0], 1, 0, class30_sub2_sub4_sub1_sub2_7.smallY[0], myPlayer.smallX[0], false, class30_sub2_sub4_sub1_sub2_7.smallX[0]);
                    if (l == 484) {
                        stream.writePacketHeaderEnc(139);
                        stream.writeShortLE(playerIndices[j3]);
                    }
                    if (l == 6) {
                        anInt1188 += i1;
                        if (anInt1188 >= 90) {
                            stream.writePacketHeaderEnc(136);
                            anInt1188 = 0;
                        }
                        stream.writePacketHeaderEnc(128);
                        stream.writeShort(playerIndices[j3]);
                    }
                    flag9 = true;
                    break;
                }

                if (!flag9) {
                    pushMessage("Unable to find " + s7, 0, "");
                }
            }
        }
        if (l == 870) {
            stream.writePacketHeaderEnc(53);
            stream.writeShort(j);
            stream.writeShortA(anInt1283);
            stream.writeShortLEA(i1);
            stream.writeShort(anInt1284);
            stream.writeShortLE(anInt1285);
            stream.writeShort(k);
            atInventoryLoopCycle = 0;
            atInventoryInterface = k;
            atInventoryIndex = j;
            atInventoryInterfaceType = 2;
            if (RSInterface.interfaceCache[k].parentID == openInterfaceID) {
                atInventoryInterfaceType = 1;
            }
            if (RSInterface.interfaceCache[k].parentID == backDialogID) {
                atInventoryInterfaceType = 3;
            }
        }
        if (l == 847) {
            stream.writePacketHeaderEnc(87);
            stream.writeShortA(i1);
            stream.writeShort(k);
            stream.writeShortA(j);
            atInventoryLoopCycle = 0;
            atInventoryInterface = k;
            atInventoryIndex = j;
            atInventoryInterfaceType = 2;
            if (RSInterface.interfaceCache[k].parentID == openInterfaceID) {
                atInventoryInterfaceType = 1;
            }
            if (RSInterface.interfaceCache[k].parentID == backDialogID) {
                atInventoryInterfaceType = 3;
            }
        }
        if (l == 626) {
            RSInterface class9_1 = RSInterface.interfaceCache[k];
            spellSelected = 1;
            anInt1137 = k;
            spellUsableOn = class9_1.spellUsableOn;
            itemSelected = 0;
            needDrawTabArea = true;
            String s4 = class9_1.selectedActionName;
            if (s4.contains(" ")) {
                s4 = s4.substring(0, s4.indexOf(" "));
            }
            String s8 = class9_1.selectedActionName;
            if (s8.contains(" ")) {
                s8 = s8.substring(s8.indexOf(" ") + 1);
            }
            spellTooltip = s4 + " " + class9_1.spellName + " " + s8;
            if (spellUsableOn == 16) {
                needDrawTabArea = true;
                tabID = 3;
                tabAreaAltered = true;
            }
            return;
        }
        if (l == 78) {
            stream.writePacketHeaderEnc(117);
            stream.writeShortLEA(k);
            stream.writeShortLEA(i1);
            stream.writeShortLE(j);
            atInventoryLoopCycle = 0;
            atInventoryInterface = k;
            atInventoryIndex = j;
            atInventoryInterfaceType = 2;
            if (RSInterface.interfaceCache[k].parentID == openInterfaceID) {
                atInventoryInterfaceType = 1;
            }
            if (RSInterface.interfaceCache[k].parentID == backDialogID) {
                atInventoryInterfaceType = 3;
            }
        }
        if (l == 27) {
            Player player = playerArray[i1];
            if (player != null) {
                walkTo(2, 0, 1, 0, myPlayer.smallY[0], 1, 0, player.smallY[0], myPlayer.smallX[0], false, player.smallX[0]);
                crossX = super.saveClickX;
                crossY = super.saveClickY;
                crossType = 2;
                crossIndex = 0;
                anInt986 += i1;
                if (anInt986 >= 54) {
                    stream.writePacketHeaderEnc(189);
                    stream.writeByte(234);
                    anInt986 = 0;
                }
                stream.writePacketHeaderEnc(73);
                stream.writeShortLE(i1);
            }
        }
        if (l == 213) {
            boolean flag3 = walkTo(2, 0, 0, 0, myPlayer.smallY[0], 0, 0, k, myPlayer.smallX[0], false, j);
            if (!flag3) {
                flag3 = walkTo(2, 0, 1, 0, myPlayer.smallY[0], 1, 0, k, myPlayer.smallX[0], false, j);
            }
            crossX = super.saveClickX;
            crossY = super.saveClickY;
            crossType = 2;
            crossIndex = 0;
            stream.writePacketHeaderEnc(79);
            stream.writeShortLE(k + baseY);
            stream.writeShort(i1);
            stream.writeShortA(j + baseX);
        }
        if (l == 632) {
            stream.writePacketHeaderEnc(145);
            stream.writeShortA(k);
            stream.writeShortA(j);
            stream.writeShortA(i1);
            atInventoryLoopCycle = 0;
            atInventoryInterface = k;
            atInventoryIndex = j;
            atInventoryInterfaceType = 2;
            if (RSInterface.interfaceCache[k].parentID == openInterfaceID) {
                atInventoryInterfaceType = 1;
            }
            if (RSInterface.interfaceCache[k].parentID == backDialogID) {
                atInventoryInterfaceType = 3;
            }
        }
        if (l == 493) {
            stream.writePacketHeaderEnc(75);
            stream.writeShortLEA(k);
            stream.writeShortLE(j);
            stream.writeShortA(i1);
            atInventoryLoopCycle = 0;
            atInventoryInterface = k;
            atInventoryIndex = j;
            atInventoryInterfaceType = 2;
            if (RSInterface.interfaceCache[k].parentID == openInterfaceID) {
                atInventoryInterfaceType = 1;
            }
            if (RSInterface.interfaceCache[k].parentID == backDialogID) {
                atInventoryInterfaceType = 3;
            }
        }
        if (l == 652) {
            boolean flag4 = walkTo(2, 0, 0, 0, myPlayer.smallY[0], 0, 0, k, myPlayer.smallX[0], false, j);
            if (!flag4) {
                flag4 = walkTo(2, 0, 1, 0, myPlayer.smallY[0], 1, 0, k, myPlayer.smallX[0], false, j);
            }
            crossX = super.saveClickX;
            crossY = super.saveClickY;
            crossType = 2;
            crossIndex = 0;
            stream.writePacketHeaderEnc(156);
            stream.writeShortA(j + baseX);
            stream.writeShortLE(k + baseY);
            stream.writeShortLEA(i1);
        }
        if (l == 94) {
            boolean flag5 = walkTo(2, 0, 0, 0, myPlayer.smallY[0], 0, 0, k, myPlayer.smallX[0], false, j);
            if (!flag5) {
                flag5 = walkTo(2, 0, 1, 0, myPlayer.smallY[0], 1, 0, k, myPlayer.smallX[0], false, j);
            }
            crossX = super.saveClickX;
            crossY = super.saveClickY;
            crossType = 2;
            crossIndex = 0;
            stream.writePacketHeaderEnc(181);
            stream.writeShortLE(k + baseY);
            stream.writeShort(i1);
            stream.writeShortLE(j + baseX);
            stream.writeShortA(anInt1137);
        }
        if (l == 646) {
            stream.writePacketHeaderEnc(185);
            stream.writeShort(k);
            RSInterface class9_2 = RSInterface.interfaceCache[k];

            if (class9_2.valueIndexArray != null && class9_2.valueIndexArray[0][0] == 5) {
                int settingId = class9_2.valueIndexArray[0][1];

                if (currentUserSetting[settingId] != class9_2.anIntArray212[0]) {
                    currentUserSetting[settingId] = class9_2.anIntArray212[0];
                    handleSettingId(settingId);
                    needDrawTabArea = true;
                }
            }
        }
        if (l == 225) {
            NPC npc = npcArray[i1];
            if (npc != null) {
                walkTo(2, 0, 1, 0, myPlayer.smallY[0], 1, 0, npc.smallY[0], myPlayer.smallX[0], false, npc.smallX[0]);
                crossX = super.saveClickX;
                crossY = super.saveClickY;
                crossType = 2;
                crossIndex = 0;
                anInt1226 += i1;
                if (anInt1226 >= 85) {
                    stream.writePacketHeaderEnc(230);
                    stream.writeByte(239);
                    anInt1226 = 0;
                }
                stream.writePacketHeaderEnc(17);
                stream.writeShortLEA(i1);
            }
        }
        if (l == 965) {
            NPC npc = npcArray[i1];
            if (npc != null) {
                walkTo(2, 0, 1, 0, myPlayer.smallY[0], 1, 0, npc.smallY[0], myPlayer.smallX[0], false, npc.smallX[0]);
                crossX = super.saveClickX;
                crossY = super.saveClickY;
                crossType = 2;
                crossIndex = 0;
                anInt1134++;
                if (anInt1134 >= 96) {
                    stream.writePacketHeaderEnc(152);
                    stream.writeByte(88);
                    anInt1134 = 0;
                }
                stream.writePacketHeaderEnc(21);
                stream.writeShort(i1);
            }
        }
        if (l == 413) {
            NPC npc = npcArray[i1];
            if (npc != null) {
                walkTo(2, 0, 1, 0, myPlayer.smallY[0], 1, 0, npc.smallY[0], myPlayer.smallX[0], false, npc.smallX[0]);
                crossX = super.saveClickX;
                crossY = super.saveClickY;
                crossType = 2;
                crossIndex = 0;
                stream.writePacketHeaderEnc(131);
                stream.writeShortLEA(i1);
                stream.writeShortA(anInt1137);
            }
        }
        if (l == 200) {
            clearTopInterfaces();
        }
        if (l == 1025) {
            NPC npc = npcArray[i1];
            if (npc != null) {
                EntityDef entityDef = npc.desc;
                if (entityDef.childrenIDs != null) {
                    entityDef = entityDef.method161();
                }
                if (entityDef != null) {
                    String s9;
                    if (entityDef.description != null) {
                        s9 = new String(entityDef.description);
                    } else {
                        s9 = "It's a " + entityDef.name + ".";
                    }
                    pushMessage(s9, 0, "");
                }
            }
        }
        if (l == 900) {
            method66(i1, k, j);
            stream.writePacketHeaderEnc(252);
            stream.writeShortLEA(i1 >> 14 & 0x7fff);
            stream.writeShortLE(k + baseY);
            stream.writeShortA(j + baseX);
        }
        if (l == 412) {
            NPC npc = npcArray[i1];
            if (npc != null) {
                walkTo(2, 0, 1, 0, myPlayer.smallY[0], 1, 0, npc.smallY[0], myPlayer.smallX[0], false, npc.smallX[0]);
                crossX = super.saveClickX;
                crossY = super.saveClickY;
                crossType = 2;
                crossIndex = 0;
                stream.writePacketHeaderEnc(72);
                stream.writeShortA(i1);
            }
        }
        if (l == 365) {
            Player player = playerArray[i1];
            if (player != null) {
                walkTo(2, 0, 1, 0, myPlayer.smallY[0], 1, 0, player.smallY[0], myPlayer.smallX[0], false, player.smallX[0]);
                crossX = super.saveClickX;
                crossY = super.saveClickY;
                crossType = 2;
                crossIndex = 0;
                stream.writePacketHeaderEnc(249);
                stream.writeShortA(i1);
                stream.writeShortLE(anInt1137);
            }
        }
        if (l == 729) {
            Player player = playerArray[i1];
            if (player != null) {
                walkTo(2, 0, 1, 0, myPlayer.smallY[0], 1, 0, player.smallY[0], myPlayer.smallX[0], false, player.smallX[0]);
                crossX = super.saveClickX;
                crossY = super.saveClickY;
                crossType = 2;
                crossIndex = 0;
                stream.writePacketHeaderEnc(39);
                stream.writeShortLE(i1);
            }
        }
        if (l == 577) {
            Player player = playerArray[i1];
            if (player != null) {
                walkTo(2, 0, 1, 0, myPlayer.smallY[0], 1, 0, player.smallY[0], myPlayer.smallX[0], false, player.smallX[0]);
                crossX = super.saveClickX;
                crossY = super.saveClickY;
                crossType = 2;
                crossIndex = 0;
                stream.writePacketHeaderEnc(139);
                stream.writeShortLE(i1);
            }
        }
        if (l == 956 && method66(i1, k, j)) {
            stream.writePacketHeaderEnc(35);
            stream.writeShortLE(j + baseX);
            stream.writeShortA(anInt1137);
            stream.writeShortA(k + baseY);
            stream.writeShortLE(i1 >> 14 & 0x7fff);
        }
        if (l == 567) {
            boolean flag6 = walkTo(2, 0, 0, 0, myPlayer.smallY[0], 0, 0, k, myPlayer.smallX[0], false, j);
            if (!flag6) {
                flag6 = walkTo(2, 0, 1, 0, myPlayer.smallY[0], 1, 0, k, myPlayer.smallX[0], false, j);
            }
            crossX = super.saveClickX;
            crossY = super.saveClickY;
            crossType = 2;
            crossIndex = 0;
            stream.writePacketHeaderEnc(23);
            stream.writeShortLE(k + baseY);
            stream.writeShortLE(i1);
            stream.writeShortLE(j + baseX);
        }
        if (l == 867) {
            if ((i1 & 3) == 0) {
                anInt1175++;
            }
            if (anInt1175 >= 59) {
                stream.writePacketHeaderEnc(200);
                stream.writeShort(25501);
                anInt1175 = 0;
            }
            stream.writePacketHeaderEnc(43);
            stream.writeShortLE(k);
            stream.writeShortA(i1);
            stream.writeShortA(j);
            atInventoryLoopCycle = 0;
            atInventoryInterface = k;
            atInventoryIndex = j;
            atInventoryInterfaceType = 2;
            if (RSInterface.interfaceCache[k].parentID == openInterfaceID) {
                atInventoryInterfaceType = 1;
            }
            if (RSInterface.interfaceCache[k].parentID == backDialogID) {
                atInventoryInterfaceType = 3;
            }
        }
        if (l == 543) {
            stream.writePacketHeaderEnc(237);
            stream.writeShort(j);
            stream.writeShortA(i1);
            stream.writeShort(k);
            stream.writeShortA(anInt1137);
            atInventoryLoopCycle = 0;
            atInventoryInterface = k;
            atInventoryIndex = j;
            atInventoryInterfaceType = 2;
            if (RSInterface.interfaceCache[k].parentID == openInterfaceID) {
                atInventoryInterfaceType = 1;
            }
            if (RSInterface.interfaceCache[k].parentID == backDialogID) {
                atInventoryInterfaceType = 3;
            }
        }
        if (l == 606) {
            String s2 = menuActionName[i];
            int j2 = s2.indexOf("@whi@");
            if (j2 != -1) {
                if (openInterfaceID == -1) {
                    clearTopInterfaces();
                    reportAbuseInput = s2.substring(j2 + 5).trim();
                    canMute = false;
                    for (int i3 = 0; i3 < RSInterface.interfaceCache.length; i3++) {
                        if (RSInterface.interfaceCache[i3] == null || RSInterface.interfaceCache[i3].anInt214 != 600) {
                            continue;
                        }
                        reportAbuseInterfaceID = openInterfaceID = RSInterface.interfaceCache[i3].parentID;
                        break;
                    }

                } else {
                    pushMessage("Please close the interface you have open before using 'report abuse'", 0, "");
                }
            }
        }
        if (l == 491) {
            Player class30_sub2_sub4_sub1_sub2_6 = playerArray[i1];
            if (class30_sub2_sub4_sub1_sub2_6 != null) {
                walkTo(2, 0, 1, 0, myPlayer.smallY[0], 1, 0, class30_sub2_sub4_sub1_sub2_6.smallY[0], myPlayer.smallX[0], false, class30_sub2_sub4_sub1_sub2_6.smallX[0]);
                crossX = super.saveClickX;
                crossY = super.saveClickY;
                crossType = 2;
                crossIndex = 0;
                stream.writePacketHeaderEnc(14);
                stream.writeShortA(anInt1284);
                stream.writeShort(i1);
                stream.writeShort(anInt1285);
                stream.writeShortLE(anInt1283);
            }
        }
        if (l == 639) {
            String s3 = menuActionName[i];
            int k2 = s3.indexOf("@whi@");
            if (k2 != -1) {
                long l4 = StringHelper.longForName(s3.substring(k2 + 5).trim());
                int k3 = -1;
                for (int i4 = 0; i4 < friendsCount; i4++) {
                    if (friendsListAsLongs[i4] != l4) {
                        continue;
                    }
                    k3 = i4;
                    break;
                }

                if (k3 != -1 && friendsNodeIDs[k3] > 0) {
                    inputTaken = true;
                    inputDialogState = 0;
                    messagePromptRaised = true;
                    promptInput = "";
                    friendsListAction = 3;
                    aLong953 = friendsListAsLongs[k3];
                    aString1121 = "Enter message to send to " + friendsList[k3];
                }
            }
        }
        if (l == 454) {
            stream.writePacketHeaderEnc(41);
            stream.writeShort(i1);
            stream.writeShortA(j);
            stream.writeShortA(k);
            atInventoryLoopCycle = 0;
            atInventoryInterface = k;
            atInventoryIndex = j;
            atInventoryInterfaceType = 2;
            if (RSInterface.interfaceCache[k].parentID == openInterfaceID) {
                atInventoryInterfaceType = 1;
            }
            if (RSInterface.interfaceCache[k].parentID == backDialogID) {
                atInventoryInterfaceType = 3;
            }
        }
        if (l == 478) {
            NPC class30_sub2_sub4_sub1_sub1_7 = npcArray[i1];
            if (class30_sub2_sub4_sub1_sub1_7 != null) {
                walkTo(2, 0, 1, 0, myPlayer.smallY[0], 1, 0, class30_sub2_sub4_sub1_sub1_7.smallY[0], myPlayer.smallX[0], false, class30_sub2_sub4_sub1_sub1_7.smallX[0]);
                crossX = super.saveClickX;
                crossY = super.saveClickY;
                crossType = 2;
                crossIndex = 0;
                if ((i1 & 3) == 0) {
                    anInt1155++;
                }
                if (anInt1155 >= 53) {
                    stream.writePacketHeaderEnc(85);
                    stream.writeByte(66);
                    anInt1155 = 0;
                }
                stream.writePacketHeaderEnc(18);
                stream.writeShortLE(i1);
            }
        }
        if (l == 113) {
            method66(i1, k, j);
            stream.writePacketHeaderEnc(70);
            stream.writeShortLE(j + baseX);
            stream.writeShort(k + baseY);
            stream.writeShortLEA(i1 >> 14 & 0x7fff);
        }
        if (l == 872) {
            method66(i1, k, j);
            stream.writePacketHeaderEnc(234);
            stream.writeShortLEA(j + baseX);
            stream.writeShortA(i1 >> 14 & 0x7fff);
            stream.writeShortLEA(k + baseY);
        }
        if (l == 502) {
            method66(i1, k, j);
            stream.writePacketHeaderEnc(132);
            stream.writeShortLEA(j + baseX);
            stream.writeShort(i1 >> 14 & 0x7fff);
            stream.writeShortA(k + baseY);
        }
        if (l == 1125) {
            ItemDef itemDef = ItemDef.forID(i1);
            RSInterface class9_4 = RSInterface.interfaceCache[k];
            String s5;
            if (class9_4 != null && class9_4.invStackSizes[j] >= 0x186a0) {
                s5 = class9_4.invStackSizes[j] + " x " + itemDef.name;
            } else if (itemDef.description != null) {
                s5 = new String(itemDef.description);
            } else {
                s5 = "It's a " + itemDef.name + ".";
            }
            pushMessage(s5, 0, "");
        }
        if (l == 169) {
            stream.writePacketHeaderEnc(185);
            stream.writeShort(k);
            RSInterface class9_3 = RSInterface.interfaceCache[k];
            if (class9_3.valueIndexArray != null && class9_3.valueIndexArray[0][0] == 5) {
                int l2 = class9_3.valueIndexArray[0][1];
                currentUserSetting[l2] = 1 - currentUserSetting[l2];
                handleSettingId(l2);
                needDrawTabArea = true;
            }
        }
        if (l == 447) {
            itemSelected = 1;
            anInt1283 = j;
            anInt1284 = k;
            anInt1285 = i1;
            selectedItemName = ItemDef.forID(i1).name;
            spellSelected = 0;
            needDrawTabArea = true;
            return;
        }
        if (l == 1226) {
            int j1 = i1 >> 14 & 0x7fff;
            ObjectDef class46 = ObjectDef.forID(j1);
            String s10;
            if (class46.description != null) {
                s10 = new String(class46.description);
            } else {
                s10 = "It's a " + class46.name + ".";
            }
            pushMessage(s10, 0, "");
        }
        if (l == 244) {
            boolean flag7 = walkTo(2, 0, 0, 0, myPlayer.smallY[0], 0, 0, k, myPlayer.smallX[0], false, j);
            if (!flag7) {
                flag7 = walkTo(2, 0, 1, 0, myPlayer.smallY[0], 1, 0, k, myPlayer.smallX[0], false, j);
            }
            crossX = super.saveClickX;
            crossY = super.saveClickY;
            crossType = 2;
            crossIndex = 0;
            stream.writePacketHeaderEnc(253);
            stream.writeShortLE(j + baseX);
            stream.writeShortLEA(k + baseY);
            stream.writeShortA(i1);
        }
        if (l == 1448) {
            ItemDef itemDef_1 = ItemDef.forID(i1);
            String s6;
            if (itemDef_1.description != null) {
                s6 = new String(itemDef_1.description);
            } else {
                s6 = "It's a " + itemDef_1.name + ".";
            }
            pushMessage(s6, 0, "");
        }
        itemSelected = 0;
        spellSelected = 0;
        needDrawTabArea = true;
    }

    private void method70() {
        anInt1251 = 0;
        int j = (myPlayer.x >> 7) + baseX;
        int k = (myPlayer.y >> 7) + baseY;
        if (j >= 3053 && j <= 3156 && k >= 3056 && k <= 3136) {
            anInt1251 = 1;
        }
        if (j >= 3072 && j <= 3118 && k >= 9492 && k <= 9535) {
            anInt1251 = 1;
        }
        if (anInt1251 == 1 && j >= 3139 && j <= 3199 && k >= 3008 && k <= 3062) {
            anInt1251 = 0;
        }
    }

    public void run() {
        if (drawFlames) {
            drawFlames();
        } else {
            super.run();
        }
    }

    private void build3dScreenMenu() {
        if (itemSelected == 0 && spellSelected == 0) {
            menuActionName[menuActionRow] = "Walk here";
            menuActionID[menuActionRow] = 516;
            menuActionCmd2[menuActionRow] = super.mouseX;
            menuActionCmd3[menuActionRow] = super.mouseY;
            menuActionRow++;
        }
        int j = -1;
        for (int k = 0; k < Model.anInt1687; k++) {
            int l = Model.anIntArray1688[k];
            int i1 = l & 0x7f;
            int j1 = l >> 7 & 0x7f;
            int k1 = l >> 29 & 3;
            int l1 = l >> 14 & 0x7fff;
            if (l == j) {
                continue;
            }
            j = l;
            if (k1 == 2 && worldController.method304(plane, i1, j1, l) >= 0) {
                ObjectDef class46 = ObjectDef.forID(l1);
                if (class46.childrenIDs != null) {
                    class46 = class46.method580();
                }
                if (class46 == null) {
                    continue;
                }
                if (itemSelected == 1) {
                    menuActionName[menuActionRow] = "Use " + selectedItemName + " with @cya@" + class46.name;
                    menuActionID[menuActionRow] = 62;
                    menuActionCmd1[menuActionRow] = l;
                    menuActionCmd2[menuActionRow] = i1;
                    menuActionCmd3[menuActionRow] = j1;
                    menuActionRow++;
                } else if (spellSelected == 1) {
                    if ((spellUsableOn & 4) == 4) {
                        menuActionName[menuActionRow] = spellTooltip + " @cya@" + class46.name;
                        menuActionID[menuActionRow] = 956;
                        menuActionCmd1[menuActionRow] = l;
                        menuActionCmd2[menuActionRow] = i1;
                        menuActionCmd3[menuActionRow] = j1;
                        menuActionRow++;
                    }
                } else {
                    if (class46.actions != null) {
                        for (int i2 = 4; i2 >= 0; i2--) {
                            if (class46.actions[i2] != null) {
                                menuActionName[menuActionRow] = class46.actions[i2] + " @cya@" + class46.name;
                                if (i2 == 0) {
                                    menuActionID[menuActionRow] = 502;
                                }
                                if (i2 == 1) {
                                    menuActionID[menuActionRow] = 900;
                                }
                                if (i2 == 2) {
                                    menuActionID[menuActionRow] = 113;
                                }
                                if (i2 == 3) {
                                    menuActionID[menuActionRow] = 872;
                                }
                                if (i2 == 4) {
                                    menuActionID[menuActionRow] = 1062;
                                }
                                menuActionCmd1[menuActionRow] = l;
                                menuActionCmd2[menuActionRow] = i1;
                                menuActionCmd3[menuActionRow] = j1;
                                menuActionRow++;
                            }
                        }

                    }
                    menuActionName[menuActionRow] = "Examine @cya@" + class46.name + " @gre@(@whi@" + l1 + "@gre@) (@whi@" + (i1 + baseX) + "," + (j1 + baseY) + "@gre@)";
                    menuActionID[menuActionRow] = 1226;
                    menuActionCmd1[menuActionRow] = class46.type << 14;
                    menuActionCmd2[menuActionRow] = i1;
                    menuActionCmd3[menuActionRow] = j1;
                    menuActionRow++;
                }
            }
            if (k1 == 1) {
                NPC npc = npcArray[l1];
                if (npc.desc.aByte68 == 1 && (npc.x & 0x7f) == 64 && (npc.y & 0x7f) == 64) {
                    for (int j2 = 0; j2 < npcCount; j2++) {
                        NPC npc2 = npcArray[npcIndices[j2]];
                        if (npc2 != null && npc2 != npc && npc2.desc.aByte68 == 1 && npc2.x == npc.x && npc2.y == npc.y) {
                            buildAtNPCMenu(npc2.desc, npcIndices[j2], j1, i1);
                        }
                    }

                    for (int l2 = 0; l2 < playerCount; l2++) {
                        Player player = playerArray[playerIndices[l2]];
                        if (player != null && player.x == npc.x && player.y == npc.y) {
                            buildAtPlayerMenu(i1, playerIndices[l2], player, j1);
                        }
                    }

                }
                buildAtNPCMenu(npc.desc, l1, j1, i1);
            }
            if (k1 == 0) {
                Player player = playerArray[l1];
                if ((player.x & 0x7f) == 64 && (player.y & 0x7f) == 64) {
                    for (int k2 = 0; k2 < npcCount; k2++) {
                        NPC class30_sub2_sub4_sub1_sub1_2 = npcArray[npcIndices[k2]];
                        if (class30_sub2_sub4_sub1_sub1_2 != null && class30_sub2_sub4_sub1_sub1_2.desc.aByte68 == 1 && class30_sub2_sub4_sub1_sub1_2.x == player.x && class30_sub2_sub4_sub1_sub1_2.y == player.y) {
                            buildAtNPCMenu(class30_sub2_sub4_sub1_sub1_2.desc, npcIndices[k2], j1, i1);
                        }
                    }

                    for (int i3 = 0; i3 < playerCount; i3++) {
                        Player class30_sub2_sub4_sub1_sub2_2 = playerArray[playerIndices[i3]];
                        if (class30_sub2_sub4_sub1_sub2_2 != null && class30_sub2_sub4_sub1_sub2_2 != player && class30_sub2_sub4_sub1_sub2_2.x == player.x && class30_sub2_sub4_sub1_sub2_2.y == player.y) {
                            buildAtPlayerMenu(i1, playerIndices[i3], class30_sub2_sub4_sub1_sub2_2, j1);
                        }
                    }

                }
                buildAtPlayerMenu(i1, l1, player, j1);
            }
            if (k1 == 3) {
                NodeList class19 = groundArray[plane][i1][j1];
                if (class19 != null) {
                    for (Item item = (Item) class19.getFirst(); item != null; item = (Item) class19.getNext()) {
                        ItemDef itemDef = ItemDef.forID(item.ID);
                        if (itemSelected == 1) {
                            menuActionName[menuActionRow] = "Use " + selectedItemName + " with @lre@" + itemDef.name;
                            menuActionID[menuActionRow] = 511;
                            menuActionCmd1[menuActionRow] = item.ID;
                            menuActionCmd2[menuActionRow] = i1;
                            menuActionCmd3[menuActionRow] = j1;
                            menuActionRow++;
                        } else if (spellSelected == 1) {
                            if ((spellUsableOn & 1) == 1) {
                                menuActionName[menuActionRow] = spellTooltip + " @lre@" + itemDef.name;
                                menuActionID[menuActionRow] = 94;
                                menuActionCmd1[menuActionRow] = item.ID;
                                menuActionCmd2[menuActionRow] = i1;
                                menuActionCmd3[menuActionRow] = j1;
                                menuActionRow++;
                            }
                        } else {
                            for (int j3 = 4; j3 >= 0; j3--) {
                                if (itemDef.groundActions != null && itemDef.groundActions[j3] != null) {
                                    menuActionName[menuActionRow] = itemDef.groundActions[j3] + " @lre@" + itemDef.name;
                                    if (j3 == 0) {
                                        menuActionID[menuActionRow] = 652;
                                    }
                                    if (j3 == 1) {
                                        menuActionID[menuActionRow] = 567;
                                    }
                                    if (j3 == 2) {
                                        menuActionID[menuActionRow] = 234;
                                    }
                                    if (j3 == 3) {
                                        menuActionID[menuActionRow] = 244;
                                    }
                                    if (j3 == 4) {
                                        menuActionID[menuActionRow] = 213;
                                    }
                                    menuActionCmd1[menuActionRow] = item.ID;
                                    menuActionCmd2[menuActionRow] = i1;
                                    menuActionCmd3[menuActionRow] = j1;
                                    menuActionRow++;
                                } else if (j3 == 2) {
                                    menuActionName[menuActionRow] = "Take @lre@" + itemDef.name;
                                    menuActionID[menuActionRow] = 234;
                                    menuActionCmd1[menuActionRow] = item.ID;
                                    menuActionCmd2[menuActionRow] = i1;
                                    menuActionCmd3[menuActionRow] = j1;
                                    menuActionRow++;
                                }
                            }

                            menuActionName[menuActionRow] = "Examine @lre@" + itemDef.name + " @gre@(@whi@" + item.ID + "@gre@)";
                            menuActionID[menuActionRow] = 1448;
                            menuActionCmd1[menuActionRow] = item.ID;
                            menuActionCmd2[menuActionRow] = i1;
                            menuActionCmd3[menuActionRow] = j1;
                            menuActionRow++;
                        }
                    }

                }
            }
        }
    }

    public void cleanUpForExit() {
        Signlink.reporterror = false;
        try {
            if (socketStream != null) {
                socketStream.close();
            }
        } catch (Exception _ex) {
        }
        socketStream = null;
        stopMidi();
        if (mouseDetection != null) {
            mouseDetection.running = false;
        }
        mouseDetection = null;
        onDemandFetcher.disable();
        onDemandFetcher = null;
        aStream_834 = null;
        stream = null;
        aStream_847 = null;
        inStream = null;
        anIntArray1234 = null;
        aByteArrayArray1183 = null;
        aByteArrayArray1247 = null;
        anIntArray1235 = null;
        anIntArray1236 = null;
        intGroundArray = null;
        byteGroundArray = null;
        worldController = null;
        clipMap = null;
        anIntArrayArray901 = null;
        anIntArrayArray825 = null;
        bigX = null;
        bigY = null;
        aByteArray912 = null;
        aRSImageProducer_1163 = null;
        aRSImageProducer_1164 = null;
        aRSImageProducer_1165 = null;
        aRSImageProducer_1166 = null;
        aRSImageProducer_1123 = null;
        aRSImageProducer_1124 = null;
        aRSImageProducer_1125 = null;
        backLeftIP1 = null;
        backLeftIP2 = null;
        backRightIP1 = null;
        backRightIP2 = null;
        backTopIP1 = null;
        backVmidIP1 = null;
        backVmidIP2 = null;
        backVmidIP3 = null;
        backVmidIP2_2 = null;
        invBack = null;
        mapBack = null;
        chatBack = null;
        backBase1 = null;
        backBase2 = null;
        backHmid1 = null;
        sideIcons = null;
        redStone1 = null;
        redStone2 = null;
        redStone3 = null;
        redStone1_2 = null;
        redStone2_2 = null;
        redStone1_3 = null;
        redStone2_3 = null;
        redStone3_2 = null;
        redStone1_4 = null;
        redStone2_4 = null;
        compass = null;
        hitMarks = null;
        headIcons = null;
        crosses = null;
        mapDotItem = null;
        mapDotNPC = null;
        mapDotPlayer = null;
        mapDotFriend = null;
        mapDotTeam = null;
        mapScenes = null;
        mapFunctions = null;
        anIntArrayArray929 = null;
        playerArray = null;
        playerIndices = null;
        updatingPlayersList = null;
        cachedPlayerUpdateBuffers = null;
        removeFromUpdatingPlayersList = null;
        npcArray = null;
        npcIndices = null;
        groundArray = null;
        aClass19_1179 = null;
        aClass19_1013 = null;
        aClass19_1056 = null;
        menuActionCmd2 = null;
        menuActionCmd3 = null;
        menuActionID = null;
        menuActionCmd1 = null;
        menuActionName = null;
        currentUserSetting = null;
        anIntArray1072 = null;
        anIntArray1073 = null;
        aClass30_Sub2_Sub1_Sub1Array1140 = null;
        sprite = null;
        friendsList = null;
        friendsListAsLongs = null;
        friendsNodeIDs = null;
        leftFlameImageProducer = null;
        rightFlameImageProducer = null;
        aRSImageProducer_1107 = null;
        aRSImageProducer_1108 = null;
        aRSImageProducer_1109 = null;
        aRSImageProducer_1112 = null;
        aRSImageProducer_1113 = null;
        aRSImageProducer_1114 = null;
        aRSImageProducer_1115 = null;
        nullLoader();
        ObjectDef.nullLoader();
        EntityDef.nullLoader();
        ItemDef.nullLoader();
        Flo.cache = null;
        IDK.cache = null;
        RSInterface.interfaceCache = null;
        Animation.anims = null;
        StaticAnimation.cache = null;
        StaticAnimation.aMRUNodes_415 = null;
        Varp.cache = null;
        super.fullGameScreen = null;
        Player.mruNodes = null;
        Texture.nullLoader();
        WorldController.nullLoader();
        Model.nullLoader();
        Class36.nullLoader();
        System.gc();
    }

    private void printDebug() {
        System.out.println("============");
        System.out.println("flame-cycle:" + anInt1208);
        
        if (onDemandFetcher != null) {
            System.out.println("Od-cycle:" + onDemandFetcher.onDemandCycle);
        }
        System.out.println("loop-cycle:" + loopCycle);
        System.out.println("draw-cycle:" + drawCycle);
        System.out.println("ptype:" + packetOpcode);
        System.out.println("psize:" + packetLength);
        
        if (socketStream != null) {
            socketStream.printDebug();
        }
        super.shouldDebug = true;
    }

    Component getGameComponent() {
        if (Signlink.mainapp != null) {
            return Signlink.mainapp;
        }
        if (super.gameFrame != null) {
            return super.gameFrame;
        } else {
            return this;
        }
    }

    private void method73() {
        do {
            int j = readChar(-796);
            if (j == -1) {
                break;
            }
            if (openInterfaceID != -1 && openInterfaceID == reportAbuseInterfaceID) {
                if (j == 8 && reportAbuseInput.length() > 0) {
                    reportAbuseInput = reportAbuseInput.substring(0, reportAbuseInput.length() - 1);
                }
                if ((j >= 97 && j <= 122 || j >= 65 && j <= 90 || j >= 48 && j <= 57 || j == 32) && reportAbuseInput.length() < 12) {
                    reportAbuseInput += (char) j;
                }
            } else if (messagePromptRaised) {
                if (j >= 32 && j <= 122 && promptInput.length() < 80) {
                    promptInput += (char) j;
                    inputTaken = true;
                }
                if (j == 8 && promptInput.length() > 0) {
                    promptInput = promptInput.substring(0, promptInput.length() - 1);
                    inputTaken = true;
                }
                if (j == 13 || j == 10) {
                    messagePromptRaised = false;
                    inputTaken = true;
                    if (friendsListAction == 1) {
                        long l = StringHelper.longForName(promptInput);
                        addFriend(l);
                    }
                    if (friendsListAction == 2 && friendsCount > 0) {
                        long l1 = StringHelper.longForName(promptInput);
                        delFriend(l1);
                    }
                    if (friendsListAction == 3 && promptInput.length() > 0) {
                        stream.writePacketHeaderEnc(126);
                        stream.writeByte(0);
                        int k = stream.currentOffset;
                        stream.writeLong(aLong953);
                        TextInput.method526(promptInput, stream);
                        stream.writeByteXXX(stream.currentOffset - k);
                        promptInput = TextInput.processText(promptInput);
                        promptInput = Censor.apply(promptInput);
                        pushMessage(promptInput, 6, StringHelper.fixName(StringHelper.nameForLong(aLong953)));
                        if (privateChatMode == 2) {
                            privateChatMode = 1;
                            aBoolean1233 = true;
                            stream.writePacketHeaderEnc(95);
                            stream.writeByte(publicChatMode);
                            stream.writeByte(privateChatMode);
                            stream.writeByte(tradeMode);
                        }
                    }
                    if (friendsListAction == 4 && ignoreCount < 100) {
                        long l2 = StringHelper.longForName(promptInput);
                        addIgnore(l2);
                    }
                    if (friendsListAction == 5 && ignoreCount > 0) {
                        long l3 = StringHelper.longForName(promptInput);
                        removeIgnore(l3);
                    }
                }
            } else if (inputDialogState == 1) {
                if (j >= 48 && j <= 57 && amountOrNameInput.length() < 10) {
                    amountOrNameInput += (char) j;
                    inputTaken = true;
                }
                if (j == 8 && amountOrNameInput.length() > 0) {
                    amountOrNameInput = amountOrNameInput.substring(0, amountOrNameInput.length() - 1);
                    inputTaken = true;
                }
                if (j == 13 || j == 10) {
                    if (amountOrNameInput.length() > 0) {
                        int i1 = 0;
                        try {
                            i1 = Integer.parseInt(amountOrNameInput);
                        } catch (Exception _ex) {
                        }
                        stream.writePacketHeaderEnc(208);
                        stream.writeInt(i1);
                    }
                    inputDialogState = 0;
                    inputTaken = true;
                }
            } else if (inputDialogState == 2) {
                if (j >= 32 && j <= 122 && amountOrNameInput.length() < 12) {
                    amountOrNameInput += (char) j;
                    inputTaken = true;
                }
                if (j == 8 && amountOrNameInput.length() > 0) {
                    amountOrNameInput = amountOrNameInput.substring(0, amountOrNameInput.length() - 1);
                    inputTaken = true;
                }
                if (j == 13 || j == 10) {
                    if (amountOrNameInput.length() > 0) {
                        stream.writePacketHeaderEnc(60);
                        stream.writeLong(StringHelper.longForName(amountOrNameInput));
                    }
                    inputDialogState = 0;
                    inputTaken = true;
                }
            } else if (backDialogID == -1) {
                if (j >= 32 && j <= 122 && inputString.length() < 80) {
                    inputString += (char) j;
                    inputTaken = true;
                }
                if (j == 8 && inputString.length() > 0) {
                    inputString = inputString.substring(0, inputString.length() - 1);
                    inputTaken = true;
                }
                
                // Client-side commands
                if ((j == 13 || j == 10) && inputString.length() > 0) {
                    if (myPrivilege == 2) {
                        if (inputString.equals("::clientdrop")) {
                            connectionLost();
                        }
                        if (inputString.equals("::lag")) {
                            printDebug();
                        }
                        if (inputString.equals("::prefetchmusic")) {
                            for (int j1 = 0; j1 < onDemandFetcher.getVersionCount(2); j1++) {
                                onDemandFetcher.method563((byte) 1, 2, j1);
                            }
                        }
                        if (inputString.equals("::fpson")) {
                            displayStats = true;
                        }
                        if (inputString.equals("::fpsoff")) {
                            displayStats = false;
                        }
                        if (inputString.equals("::noclip")) {
                            for (int z = 0; z < 4; z++) {
                                for (int x = 1; x < 103; x++) {
                                    for (int y = 1; y < 103; y++) {
                                        clipMap[z].mapFlags[x][y] = 0;
                                    }
                                }
                            }
                        }
                    }
                    if (inputString.startsWith("::")) {
                        stream.writePacketHeaderEnc(103);
                        stream.writeByte(inputString.length() - 1);
                        stream.writeString(inputString.substring(2));
                    } else {
                        String s = inputString.toLowerCase();
                        int j2 = 0;
                        
                        if (s.startsWith("yellow:")) {
                            j2 = 0;
                            inputString = inputString.substring(7);
                        } else if (s.startsWith("red:")) {
                            j2 = 1;
                            inputString = inputString.substring(4);
                        } else if (s.startsWith("green:")) {
                            j2 = 2;
                            inputString = inputString.substring(6);
                        } else if (s.startsWith("cyan:")) {
                            j2 = 3;
                            inputString = inputString.substring(5);
                        } else if (s.startsWith("purple:")) {
                            j2 = 4;
                            inputString = inputString.substring(7);
                        } else if (s.startsWith("white:")) {
                            j2 = 5;
                            inputString = inputString.substring(6);
                        } else if (s.startsWith("flash1:")) {
                            j2 = 6;
                            inputString = inputString.substring(7);
                        } else if (s.startsWith("flash2:")) {
                            j2 = 7;
                            inputString = inputString.substring(7);
                        } else if (s.startsWith("flash3:")) {
                            j2 = 8;
                            inputString = inputString.substring(7);
                        } else if (s.startsWith("glow1:")) {
                            j2 = 9;
                            inputString = inputString.substring(6);
                        } else if (s.startsWith("glow2:")) {
                            j2 = 10;
                            inputString = inputString.substring(6);
                        } else if (s.startsWith("glow3:")) {
                            j2 = 11;
                            inputString = inputString.substring(6);
                        }
                        s = inputString.toLowerCase();
                        int i3 = 0;
                        
                        if (s.startsWith("wave:")) {
                            i3 = 1;
                            inputString = inputString.substring(5);
                        } else if (s.startsWith("wave2:")) {
                            i3 = 2;
                            inputString = inputString.substring(6);
                        } else if (s.startsWith("shake:")) {
                            i3 = 3;
                            inputString = inputString.substring(6);
                        } else if (s.startsWith("scroll:")) {
                            i3 = 4;
                            inputString = inputString.substring(7);
                        } else if (s.startsWith("slide:")) {
                            i3 = 5;
                            inputString = inputString.substring(6);
                        }
                        stream.writePacketHeaderEnc(4);
                        stream.writeByte(0);
                        int j3 = stream.currentOffset;
                        stream.writeByteS(i3);
                        stream.writeByteS(j2);
                        aStream_834.currentOffset = 0;
                        TextInput.method526(inputString, aStream_834);
                        stream.writeBytesA(0, aStream_834.buffer, aStream_834.currentOffset);
                        stream.writeByteXXX(stream.currentOffset - j3);
                        inputString = TextInput.processText(inputString);
                        inputString = Censor.apply(inputString);
                        myPlayer.textSpoken = inputString;
                        myPlayer.chatAttributes = j2;
                        myPlayer.anInt1531 = i3;
                        myPlayer.textCycle = 150;
                        
                        // Crown handling
                        if (myPrivilege == 2) {
                            pushMessage(myPlayer.textSpoken, 2, "@cr2@" + myPlayer.name);
                        } else if (myPrivilege == 1) {
                            pushMessage(myPlayer.textSpoken, 2, "@cr1@" + myPlayer.name);
                        } else {
                            pushMessage(myPlayer.textSpoken, 2, myPlayer.name);
                        }
                        
                        if (publicChatMode == 2) {
                            publicChatMode = 3;
                            aBoolean1233 = true;
                            stream.writePacketHeaderEnc(95);
                            stream.writeByte(publicChatMode);
                            stream.writeByte(privateChatMode);
                            stream.writeByte(tradeMode);
                        }
                    }
                    inputString = "";
                    inputTaken = true;
                }
            }
        } while (true);
    }

    private void buildChatAreaMenu(int j) {
        int l = 0;
        for (int i1 = 0; i1 < 100; i1++) {
            if (chatMessages[i1] == null) {
                continue;
            }
            int j1 = chatTypes[i1];
            int k1 = (70 - l * 14) + anInt1089 + 4;
            if (k1 < -20) {
                break;
            }
            String s = chatNames[i1];
            boolean flag = false;
            if (s != null && s.startsWith("@cr1@")) {
                s = s.substring(5);
                boolean flag1 = true;
            }
            if (s != null && s.startsWith("@cr2@")) {
                s = s.substring(5);
                byte byte0 = 2;
            }
            if (j1 == 0) {
                l++;
            }
            if ((j1 == 1 || j1 == 2) && (j1 == 1 || publicChatMode == 0 || publicChatMode == 1 && isFriendOrSelf(s))) {
                if (j > k1 - 14 && j <= k1 && !s.equals(myPlayer.name)) {
                    if (myPrivilege >= 1) {
                        menuActionName[menuActionRow] = "Report abuse @whi@" + s;
                        menuActionID[menuActionRow] = 606;
                        menuActionRow++;
                    }
                    menuActionName[menuActionRow] = "Add ignore @whi@" + s;
                    menuActionID[menuActionRow] = 42;
                    menuActionRow++;
                    menuActionName[menuActionRow] = "Add friend @whi@" + s;
                    menuActionID[menuActionRow] = 337;
                    menuActionRow++;
                }
                l++;
            }
            if ((j1 == 3 || j1 == 7) && splitPrivateChat == 0 && (j1 == 7 || privateChatMode == 0 || privateChatMode == 1 && isFriendOrSelf(s))) {
                if (j > k1 - 14 && j <= k1) {
                    if (myPrivilege >= 1) {
                        menuActionName[menuActionRow] = "Report abuse @whi@" + s;
                        menuActionID[menuActionRow] = 606;
                        menuActionRow++;
                    }
                    menuActionName[menuActionRow] = "Add ignore @whi@" + s;
                    menuActionID[menuActionRow] = 42;
                    menuActionRow++;
                    menuActionName[menuActionRow] = "Add friend @whi@" + s;
                    menuActionID[menuActionRow] = 337;
                    menuActionRow++;
                }
                l++;
            }
            if (j1 == 4 && (tradeMode == 0 || tradeMode == 1 && isFriendOrSelf(s))) {
                if (j > k1 - 14 && j <= k1) {
                    menuActionName[menuActionRow] = "Accept trade @whi@" + s;
                    menuActionID[menuActionRow] = 484;
                    menuActionRow++;
                }
                l++;
            }
            if ((j1 == 5 || j1 == 6) && splitPrivateChat == 0 && privateChatMode < 2) {
                l++;
            }
            if (j1 == 8 && (tradeMode == 0 || tradeMode == 1 && isFriendOrSelf(s))) {
                if (j > k1 - 14 && j <= k1) {
                    menuActionName[menuActionRow] = "Accept challenge @whi@" + s;
                    menuActionID[menuActionRow] = 6;
                    menuActionRow++;
                }
                l++;
            }
        }
    }

    private void drawFriendsListOrWelcomeScreen(RSInterface class9) {
        int j = class9.anInt214;
        if (j >= 1 && j <= 100 || j >= 701 && j <= 800) {
            if (j == 1 && friendsListLoadStatus == 0) {
                class9.message = "Loading friend list";
                class9.atActionType = 0;
                return;
            }
            if (j == 1 && friendsListLoadStatus == 1) {
                class9.message = "Connecting to friendserver";
                class9.atActionType = 0;
                return;
            }
            if (j == 2 && friendsListLoadStatus != 2) {
                class9.message = "Please wait...";
                class9.atActionType = 0;
                return;
            }
            int k = friendsCount;
            if (friendsListLoadStatus != 2) {
                k = 0;
            }
            if (j > 700) {
                j -= 601;
            } else {
                j--;
            }
            if (j >= k) {
                class9.message = "";
                class9.atActionType = 0;
                return;
            } else {
                class9.message = friendsList[j];
                class9.atActionType = 1;
                return;
            }
        }
        if (j >= 101 && j <= 200 || j >= 801 && j <= 900) {
            int l = friendsCount;
            if (friendsListLoadStatus != 2) {
                l = 0;
            }
            if (j > 800) {
                j -= 701;
            } else {
                j -= 101;
            }
            if (j >= l) {
                class9.message = "";
                class9.atActionType = 0;
                return;
            }
            if (friendsNodeIDs[j] == 0) {
                class9.message = "@red@Offline";
            } else if (friendsNodeIDs[j] == nodeId) {
                class9.message = "@gre@World-" + (friendsNodeIDs[j] - 9);
            } else {
                class9.message = "@yel@World-" + (friendsNodeIDs[j] - 9);
            }
            class9.atActionType = 1;
            return;
        }
        if (j == 203) {
            int i1 = friendsCount;
            if (friendsListLoadStatus != 2) {
                i1 = 0;
            }
            class9.maximumScrollPosition = i1 * 15 + 20;
            if (class9.maximumScrollPosition <= class9.height) {
                class9.maximumScrollPosition = class9.height + 1;
            }
            return;
        }
        if (j >= 401 && j <= 500) {
            if ((j -= 401) == 0 && friendsListLoadStatus == 0) {
                class9.message = "Loading ignore list";
                class9.atActionType = 0;
                return;
            }
            if (j == 1 && friendsListLoadStatus == 0) {
                class9.message = "Please wait...";
                class9.atActionType = 0;
                return;
            }
            int j1 = ignoreCount;
            if (friendsListLoadStatus == 0) {
                j1 = 0;
            }
            if (j >= j1) {
                class9.message = "";
                class9.atActionType = 0;
                return;
            } else {
                class9.message = StringHelper.fixName(StringHelper.nameForLong(ignoreListAsLongs[j]));
                class9.atActionType = 1;
                return;
            }
        }
        if (j == 503) {
            class9.maximumScrollPosition = ignoreCount * 15 + 20;
            if (class9.maximumScrollPosition <= class9.height) {
                class9.maximumScrollPosition = class9.height + 1;
            }
            return;
        }
        if (j == 327) {
            class9.modelRotation1 = 150;
            class9.anInt271 = (int) (Math.sin((double) loopCycle / 40D) * 256D) & 0x7ff;
            if (aBoolean1031) {
                for (int k1 = 0; k1 < 7; k1++) {
                    int l1 = anIntArray1065[k1];
                    if (l1 >= 0 && !IDK.cache[l1].method537()) {
                        return;
                    }
                }

                aBoolean1031 = false;
                Model aclass30_sub2_sub4_sub6s[] = new Model[7];
                int i2 = 0;
                for (int j2 = 0; j2 < 7; j2++) {
                    int k2 = anIntArray1065[j2];
                    if (k2 >= 0) {
                        aclass30_sub2_sub4_sub6s[i2++] = IDK.cache[k2].method538();
                    }
                }

                Model model = new Model(i2, aclass30_sub2_sub4_sub6s);
                for (int l2 = 0; l2 < 5; l2++) {
                    if (anIntArray990[l2] != 0) {
                        model.method476(anIntArrayArray1003[l2][0], anIntArrayArray1003[l2][anIntArray990[l2]]);
                        if (l2 == 1) {
                            model.method476(anIntArray1204[0], anIntArray1204[anIntArray990[l2]]);
                        }
                    }
                }

                model.method469();
                model.method470(Animation.anims[myPlayer.anInt1511].anIntArray353[0]);
                model.method479(64, 850, -30, -50, -30, true);
                class9.anInt233 = 5;
                class9.mediaId = 0;
                RSInterface.method208(aBoolean994, model);
            }
            return;
        }
        if (j == 324) {
            if (aClass30_Sub2_Sub1_Sub1_931 == null) {
                aClass30_Sub2_Sub1_Sub1_931 = class9.sprite1;
                aClass30_Sub2_Sub1_Sub1_932 = class9.sprite2;
            }
            if (aBoolean1047) {
                class9.sprite1 = aClass30_Sub2_Sub1_Sub1_932;
                return;
            } else {
                class9.sprite1 = aClass30_Sub2_Sub1_Sub1_931;
                return;
            }
        }
        if (j == 325) {
            if (aClass30_Sub2_Sub1_Sub1_931 == null) {
                aClass30_Sub2_Sub1_Sub1_931 = class9.sprite1;
                aClass30_Sub2_Sub1_Sub1_932 = class9.sprite2;
            }
            if (aBoolean1047) {
                class9.sprite1 = aClass30_Sub2_Sub1_Sub1_931;
                return;
            } else {
                class9.sprite1 = aClass30_Sub2_Sub1_Sub1_932;
                return;
            }
        }
        if (j == 600) {
            class9.message = reportAbuseInput;
            if (loopCycle % 20 < 10) {
                class9.message += "|";
                return;
            } else {
                class9.message += " ";
                return;
            }
        }
        if (j == 613) {
            if (myPrivilege >= 1) {
                if (canMute) {
                    class9.textColor = 0xff0000;
                    class9.message = "Moderator option: Mute player for 48 hours: <ON>";
                } else {
                    class9.textColor = 0xffffff;
                    class9.message = "Moderator option: Mute player for 48 hours: <OFF>";
                }
            } else {
                class9.message = "";
            }
        }
        if (j == 650 || j == 655) {
            if (lastLoggedIp != 0) {
                String s;
                if (daysSinceLastLogin == 0) {
                    s = "earlier today";
                } else if (daysSinceLastLogin == 1) {
                    s = "yesterday";
                } else {
                    s = daysSinceLastLogin + " days ago";
                }
                class9.message = "You last logged in " + s + " from: " + Signlink.dns;
            } else {
                class9.message = "";
            }
        }
        if (j == 651) {
            if (unreadMessages == 0) {
                class9.message = "0 unread messages";
                class9.textColor = 0xffff00;
            }
            if (unreadMessages == 1) {
                class9.message = "1 unread message";
                class9.textColor = 65280;
            }
            if (unreadMessages > 1) {
                class9.message = unreadMessages + " unread messages";
                class9.textColor = 65280;
            }
        }
        if (j == 652) {
            if (daysSinceRecoveryChange == 201) {
                if (memberWarning == 1) {
                    class9.message = "@yel@This is a non-members world: @whi@Since you are a member we";
                } else {
                    class9.message = "";
                }
            } else if (daysSinceRecoveryChange == 200) {
                class9.message = "You have not yet set any password recovery questions.";
            } else {
                String s1;
                if (daysSinceRecoveryChange == 0) {
                    s1 = "Earlier today";
                } else if (daysSinceRecoveryChange == 1) {
                    s1 = "Yesterday";
                } else {
                    s1 = daysSinceRecoveryChange + " days ago";
                }
                class9.message = s1 + " you changed your recovery questions";
            }
        }
        if (j == 653) {
            if (daysSinceRecoveryChange == 201) {
                if (memberWarning == 1) {
                    class9.message = "@whi@recommend you use a members world instead. You may use";
                } else {
                    class9.message = "";
                }
            } else if (daysSinceRecoveryChange == 200) {
                class9.message = "We strongly recommend you do so now to secure your account.";
            } else {
                class9.message = "If you do not remember making this change then cancel it immediately";
            }
        }
        if (j == 654) {
            if (daysSinceRecoveryChange == 201) {
                if (memberWarning == 1) {
                    class9.message = "@whi@this world but member benefits are unavailable whilst here.";
                    return;
                } else {
                    class9.message = "";
                    return;
                }
            }
            if (daysSinceRecoveryChange == 200) {
                class9.message = "Do this from the 'account management' area on our front webpage";
                return;
            }
            class9.message = "Do this from the 'account management' area on our front webpage";
        }
    }

    private void drawSplitPrivateChat() {
        if (splitPrivateChat == 0) {
            return;
        }
        TextDrawingArea textDrawingArea = loadingTextArea;
        int i = 0;
        
        if (timeUntilSystemUpdate != 0) {
            i = 1;
        }
        
        for (int j = 0; j < 100; j++) {
            if (chatMessages[j] != null) {
                int k = chatTypes[j];
                String s = chatNames[j];
                byte byte1 = 0;
                if (s != null && s.startsWith("@cr1@")) {
                    s = s.substring(5);
                    byte1 = 1;
                }
                if (s != null && s.startsWith("@cr2@")) {
                    s = s.substring(5);
                    byte1 = 2;
                }
                if ((k == 3 || k == 7) && (k == 7 || privateChatMode == 0 || privateChatMode == 1 && isFriendOrSelf(s))) {
                    int l = 329 - i * 13;
                    int k1 = 4;
                    textDrawingArea.method385(0, "From", l, k1);
                    textDrawingArea.method385(65535, "From", l - 1, k1);
                    k1 += textDrawingArea.getTextWidthWithoutColourCodes("From ");

                    if (byte1 == 1) {
                        modIcons[0].draw(k1, l - 12);
                        k1 += 14;
                    }

                    if (byte1 == 2) {
                        modIcons[1].draw(k1, l - 12);
                        k1 += 14;
                    }
                    textDrawingArea.method385(0, s + ": " + chatMessages[j], l, k1);
                    textDrawingArea.method385(65535, s + ": " + chatMessages[j], l - 1, k1);

                    if (++i >= 5) {
                        return;
                    }
                }
                if (k == 5 && privateChatMode < 2) {
                    int i1 = 329 - i * 13;
                    textDrawingArea.method385(0, chatMessages[j], i1, 4);
                    textDrawingArea.method385(65535, chatMessages[j], i1 - 1, 4);

                    if (++i >= 5) {
                        return;
                    }
                }
                if (k == 6 && privateChatMode < 2) {
                    int j1 = 329 - i * 13;
                    textDrawingArea.method385(0, "To " + s + ": " + chatMessages[j], j1, 4);
                    textDrawingArea.method385(65535, "To " + s + ": " + chatMessages[j], j1 - 1, 4);
                    
                    if (++i >= 5) {
                        return;
                    }
                }
            }
        }
    }

    private void pushMessage(String chatMessage, int chatType, String chatName) {
        if (chatType == 0 && dialogID != -1) {
            aString844 = chatMessage;
            super.clickMode3 = 0;
        }
        
        if (backDialogID == -1) {
            inputTaken = true;
        }
        
        for (int j = 99; j > 0; j--) {
            chatTypes[j] = chatTypes[j - 1];
            chatNames[j] = chatNames[j - 1];
            chatMessages[j] = chatMessages[j - 1];
        }
        chatTypes[0] = chatType;
        chatNames[0] = chatName;
        chatMessages[0] = chatMessage;
    }

    private void processTabClick() {
        if (super.clickMode3 == 1) {
            if (super.saveClickX >= 539 && super.saveClickX <= 573 && super.saveClickY >= 169 && super.saveClickY < 205 && tabInterfaceIDs[0] != -1) {
                tabClicked(0);
            }
            if (super.saveClickX >= 569 && super.saveClickX <= 599 && super.saveClickY >= 168 && super.saveClickY < 205 && tabInterfaceIDs[1] != -1) {
                tabClicked(1);
            }
            if (super.saveClickX >= 597 && super.saveClickX <= 627 && super.saveClickY >= 168 && super.saveClickY < 205 && tabInterfaceIDs[2] != -1) {
                tabClicked(2);
            }
            if (super.saveClickX >= 625 && super.saveClickX <= 669 && super.saveClickY >= 168 && super.saveClickY < 203 && tabInterfaceIDs[3] != -1) {
                tabClicked(3);
            }
            if (super.saveClickX >= 666 && super.saveClickX <= 696 && super.saveClickY >= 168 && super.saveClickY < 205 && tabInterfaceIDs[4] != -1) {
                tabClicked(4);
            }
            if (super.saveClickX >= 694 && super.saveClickX <= 724 && super.saveClickY >= 168 && super.saveClickY < 205 && tabInterfaceIDs[5] != -1) {
                tabClicked(5);
            }
            if (super.saveClickX >= 722 && super.saveClickX <= 756 && super.saveClickY >= 169 && super.saveClickY < 205 && tabInterfaceIDs[6] != -1) {
                tabClicked(6);
            }
            if (super.saveClickX >= 540 && super.saveClickX <= 574 && super.saveClickY >= 466 && super.saveClickY < 502 && tabInterfaceIDs[7] != -1) {
                tabClicked(7);
            }
            if (super.saveClickX >= 572 && super.saveClickX <= 602 && super.saveClickY >= 466 && super.saveClickY < 503 && tabInterfaceIDs[8] != -1) {
                tabClicked(8);
            }
            if (super.saveClickX >= 599 && super.saveClickX <= 629 && super.saveClickY >= 466 && super.saveClickY < 503 && tabInterfaceIDs[9] != -1) {
                tabClicked(9);
            }
            if (super.saveClickX >= 627 && super.saveClickX <= 671 && super.saveClickY >= 467 && super.saveClickY < 502 && tabInterfaceIDs[10] != -1) {
                tabClicked(10);
            }
            if (super.saveClickX >= 669 && super.saveClickX <= 699 && super.saveClickY >= 466 && super.saveClickY < 503 && tabInterfaceIDs[11] != -1) {
                tabClicked(11);
            }
            if (super.saveClickX >= 696 && super.saveClickX <= 726 && super.saveClickY >= 466 && super.saveClickY < 503 && tabInterfaceIDs[12] != -1) {
                tabClicked(12);
            }
            if (super.saveClickX >= 724 && super.saveClickX <= 758 && super.saveClickY >= 466 && super.saveClickY < 502 && tabInterfaceIDs[13] != -1) {
                tabClicked(13);
            }
        }
    }

    private void tabClicked(int tabId) {
        needDrawTabArea = true;
        tabID = tabId;
        tabAreaAltered = true;
    }

    private void resetImageProducers2() {
        if (aRSImageProducer_1166 != null) {
            return;
        }
        nullLoader();
        super.fullGameScreen = null;
        aRSImageProducer_1107 = null;
        aRSImageProducer_1108 = null;
        aRSImageProducer_1109 = null;
        leftFlameImageProducer = null;
        rightFlameImageProducer = null;
        aRSImageProducer_1112 = null;
        aRSImageProducer_1113 = null;
        aRSImageProducer_1114 = null;
        aRSImageProducer_1115 = null;
        aRSImageProducer_1166 = new RSImageProducer(479, 96, getGameComponent());
        aRSImageProducer_1164 = new RSImageProducer(172, 156, getGameComponent());
        DrawingArea.clear();
        mapBack.draw(0, 0);
        aRSImageProducer_1163 = new RSImageProducer(190, 261, getGameComponent());
        aRSImageProducer_1165 = new RSImageProducer(512, 334, getGameComponent());
        DrawingArea.clear();
        aRSImageProducer_1123 = new RSImageProducer(496, 50, getGameComponent());
        aRSImageProducer_1124 = new RSImageProducer(269, 37, getGameComponent());
        aRSImageProducer_1125 = new RSImageProducer(249, 45, getGameComponent());
        welcomeScreenRaised = true;
    }

    private String getDocumentBaseHost() {
        if (Signlink.mainapp != null) {
            return Signlink.mainapp.getDocumentBase().getHost().toLowerCase();
        }
        if (super.gameFrame != null) {
            return "runescape.com";
        } else {
            return super.getDocumentBase().getHost().toLowerCase();
        }
    }

    private void method81(Sprite sprite, int j, int k) {
        int l = k * k + j * j;
        if (l > 4225 && l < 0x15f90) {
            int i1 = minimapInt1 + minimapInt2 & 0x7ff;
            int j1 = Model.modelIntArray1[i1];
            int k1 = Model.modelIntArray2[i1];
            j1 = (j1 * 256) / (minimapInt3 + 256);
            k1 = (k1 * 256) / (minimapInt3 + 256);
            int l1 = j * j1 + k * k1 >> 16;
            int i2 = j * k1 - k * j1 >> 16;
            double d = Math.atan2(l1, i2);
            int j2 = (int) (Math.sin(d) * 63D);
            int k2 = (int) (Math.cos(d) * 57D);
            mapEdge.method353(83 - k2 - 20, d, (94 + j2 + 4) - 10);
        } else {
            markMinimap(sprite, k, j);
        }
    }

    private void processRightClick() {
        if (activeInterfaceType != 0) {
            return;
        }
        menuActionName[0] = "Cancel";
        menuActionID[0] = 1107;
        menuActionRow = 1;
        buildSplitPrivateChatMenu();
        anInt886 = 0;
        if (super.mouseX > 4 && super.mouseY > 4 && super.mouseX < 516 && super.mouseY < 338) {
            if (openInterfaceID != -1) {
                buildInterfaceMenu(4, RSInterface.interfaceCache[openInterfaceID], super.mouseX, 4, super.mouseY, 0);
            } else {
                build3dScreenMenu();
            }
        }
        if (anInt886 != anInt1026) {
            anInt1026 = anInt886;
        }
        anInt886 = 0;
        if (super.mouseX > 553 && super.mouseY > 205 && super.mouseX < 743 && super.mouseY < 466) {
            if (invOverlayInterfaceID != -1) {
                buildInterfaceMenu(553, RSInterface.interfaceCache[invOverlayInterfaceID], super.mouseX, 205, super.mouseY, 0);
            } else if (tabInterfaceIDs[tabID] != -1) {
                buildInterfaceMenu(553, RSInterface.interfaceCache[tabInterfaceIDs[tabID]], super.mouseX, 205, super.mouseY, 0);
            }
        }
        if (anInt886 != anInt1048) {
            needDrawTabArea = true;
            anInt1048 = anInt886;
        }
        anInt886 = 0;
        if (super.mouseX > 17 && super.mouseY > 357 && super.mouseX < 496 && super.mouseY < 453) {
            if (backDialogID != -1) {
                buildInterfaceMenu(17, RSInterface.interfaceCache[backDialogID], super.mouseX, 357, super.mouseY, 0);
            } else if (super.mouseY < 434 && super.mouseX < 426) {
                buildChatAreaMenu(super.mouseY - 357);
            }
        }
        if (backDialogID != -1 && anInt886 != anInt1039) {
            inputTaken = true;
            anInt1039 = anInt886;
        }
        boolean flag = false;
        while (!flag) {
            flag = true;
            for (int j = 0; j < menuActionRow - 1; j++) {
                if (menuActionID[j] < 1000 && menuActionID[j + 1] > 1000) {
                    String s = menuActionName[j];
                    menuActionName[j] = menuActionName[j + 1];
                    menuActionName[j + 1] = s;
                    int k = menuActionID[j];
                    menuActionID[j] = menuActionID[j + 1];
                    menuActionID[j + 1] = k;
                    k = menuActionCmd2[j];
                    menuActionCmd2[j] = menuActionCmd2[j + 1];
                    menuActionCmd2[j + 1] = k;
                    k = menuActionCmd3[j];
                    menuActionCmd3[j] = menuActionCmd3[j + 1];
                    menuActionCmd3[j + 1] = k;
                    k = menuActionCmd1[j];
                    menuActionCmd1[j] = menuActionCmd1[j + 1];
                    menuActionCmd1[j + 1] = k;
                    flag = false;
                }
            }

        }
    }

    private int method83(int i, int j, int k) {
        int l = 256 - k;
        return ((i & 0xff00ff) * l + (j & 0xff00ff) * k & 0xff00ff00)
                + ((i & 0xff00) * l + (j & 0xff00) * k & 0xff0000) >> 8;
    }

    private void login(String playerName, String playerPassword, boolean flag) {
        Signlink.errorname = playerName;
        
        try {
            if (!flag) {
                loginMessage1 = "";
                loginMessage2 = "Connecting to server...";
                drawLoginScreen(true);
            }
            socketStream = new RSSocket(this, openSocket(port));
            long name = StringHelper.longForName(playerName);
            int nameHash = (int) (name >> 16 & 31L);
            stream.currentOffset = 0;
            stream.writeByte(14);
            stream.writeByte(nameHash);
            socketStream.queueBytes(2, stream.buffer);
            
            for (int j = 0; j < 8; j++) {
                socketStream.read();
            }
            int responseCode = socketStream.read();
            int i1 = responseCode;
            
            if (responseCode == 0) {
                socketStream.flushInputStream(inStream.buffer, 8);
                inStream.currentOffset = 0;
                serverSessionKey = inStream.readULong();
                int ai[] = new int[4];
                ai[0] = (int) (Math.random() * 99999999D);
                ai[1] = (int) (Math.random() * 99999999D);
                ai[2] = (int) (serverSessionKey >> 32);
                ai[3] = (int) serverSessionKey;
                stream.currentOffset = 0;
                stream.writeByte(10);
                stream.writeInt(ai[0]);
                stream.writeInt(ai[1]);
                stream.writeInt(ai[2]);
                stream.writeInt(ai[3]);
                stream.writeInt(Signlink.uid);
                stream.writeString(playerName);
                stream.writeString(playerPassword);
                stream.generateKeys();
                aStream_847.currentOffset = 0;
                
                if (flag) {
                    aStream_847.writeByte(18);
                } else {
                    aStream_847.writeByte(16);
                }
                aStream_847.writeByte(stream.currentOffset + 36 + 1 + 1 + 2);
                aStream_847.writeByte(255);
                aStream_847.writeShort(317);
                aStream_847.writeByte(lowMem ? 1 : 0);
                
                for (int l1 = 0; l1 < 9; l1++) {
                    aStream_847.writeInt(expectedCRCs[l1]);
                }
                aStream_847.writeBytes(stream.buffer, stream.currentOffset, 0);
                stream.encryption = new ISAACCipher(ai);
                
                for (int j2 = 0; j2 < 4; j2++) {
                    ai[j2] += 50;
                }
                encryption = new ISAACCipher(ai);
                socketStream.queueBytes(aStream_847.currentOffset, aStream_847.buffer);
                responseCode = socketStream.read();
            }

            if (responseCode == 1) {
                try {
                    Thread.sleep(2000L);
                } catch (InterruptedException _ex) {
                }
                login(playerName, playerPassword, flag);
                return;
            }

            if (responseCode == 2) {
                myPrivilege = socketStream.read();
                flagged = socketStream.read() == 1;
                aLong1220 = 0L;
                anInt1022 = 0;
                mouseDetection.coordsIndex = 0;
                super.awtFocus = true;
                aBoolean954 = true;
                loggedIn = true;
                stream.currentOffset = 0;
                inStream.currentOffset = 0;
                packetOpcode = -1;
                anInt841 = -1;
                anInt842 = -1;
                anInt843 = -1;
                packetLength = 0;
                ticksSincelastPacketReceived = 0;
                timeUntilSystemUpdate = 0;
                anInt1011 = 0;
                iconType = 0;
                menuActionRow = 0;
                menuOpen = false;
                super.idleTime = 0;
                
                for (int i2 = 0; i2 < 100; i2++) {
                    chatMessages[i2] = null;
                }
                itemSelected = 0;
                spellSelected = 0;
                loadingStage = 0;
                soundSlot = 0;
                anInt1278 = (int) (Math.random() * 100D) - 50;
                anInt1131 = (int) (Math.random() * 110D) - 55;
                anInt896 = (int) (Math.random() * 80D) - 40;
                minimapInt2 = (int) (Math.random() * 120D) - 60;
                minimapInt3 = (int) (Math.random() * 30D) - 20;
                minimapInt1 = (int) (Math.random() * 20D) - 10 & 0x7ff;
                minimapState = 0;
                anInt985 = -1;
                destX = 0;
                destY = 0;
                playerCount = 0;
                npcCount = 0;
                
                for (int i2 = 0; i2 < maxPlayers; i2++) {
                    playerArray[i2] = null;
                    cachedPlayerUpdateBuffers[i2] = null;
                }

                for (int i2 = 0; i2 < 16384; i2++) {
                    npcArray[i2] = null;
                }
                myPlayer = playerArray[myPlayerIndex] = new Player();
                aClass19_1013.removeAll();
                aClass19_1056.removeAll();
                
                for (int z = 0; z < 4; z++) {
                    for (int x = 0; x < 104; x++) {
                        for (int y = 0; y < 104; y++) {
                            groundArray[z][x][y] = null;
                        }
                    }
                }
                aClass19_1179 = new NodeList();
                friendsListLoadStatus = 0;
                friendsCount = 0;
                dialogID = -1;
                backDialogID = -1;
                openInterfaceID = -1;
                invOverlayInterfaceID = -1;
                currentInterfaceId = -1;
                aBoolean1149 = false;
                tabID = 3;
                inputDialogState = 0;
                menuOpen = false;
                messagePromptRaised = false;
                aString844 = null;
                multiCombatFlag = 0;
                flashSidebarId = -1;
                aBoolean1047 = true;
                method45();
                
                for (int i2 = 0; i2 < 5; i2++) {
                    anIntArray990[i2] = 0;
                }
                for (int i2 = 0; i2 < 5; i2++) {
                    atPlayerActions[i2] = null;
                    atPlayerArray[i2] = false;
                }
                anInt1175 = 0;
                anInt1134 = 0;
                anInt986 = 0;
                anInt1288 = 0;
                anInt924 = 0;
                anInt1188 = 0;
                anInt1155 = 0;
                anInt1226 = 0;
                resetImageProducers2();
                return;
            }
            if (responseCode == 3) {
                loginMessage1 = "";
                loginMessage2 = "Invalid username or password.";
                return;
            }
            if (responseCode == 4) {
                loginMessage1 = "Your account has been disabled.";
                loginMessage2 = "Please check your message-center for details.";
                return;
            }
            if (responseCode == 5) {
                loginMessage1 = "Your account is already logged in.";
                loginMessage2 = "Try again in 60 secs...";
                return;
            }
            if (responseCode == 6) {
                loginMessage1 = "RuneScape has been updated!";
                loginMessage2 = "Please reload this page.";
                return;
            }
            if (responseCode == 7) {
                loginMessage1 = "This world is full.";
                loginMessage2 = "Please use a different world.";
                return;
            }
            if (responseCode == 8) {
                loginMessage1 = "Unable to connect.";
                loginMessage2 = "Login server offline.";
                return;
            }
            if (responseCode == 9) {
                loginMessage1 = "Login limit exceeded.";
                loginMessage2 = "Too many connections from your address.";
                return;
            }
            if (responseCode == 10) {
                loginMessage1 = "Unable to connect.";
                loginMessage2 = "Bad session id.";
                return;
            }
            if (responseCode == 11) {
                loginMessage2 = "Login server rejected session.";
                loginMessage2 = "Please try again.";
                return;
            }
            if (responseCode == 12) {
                loginMessage1 = "You need a members account to login to this world.";
                loginMessage2 = "Please subscribe, or use a different world.";
                return;
            }
            if (responseCode == 13) {
                loginMessage1 = "Could not complete login.";
                loginMessage2 = "Please try using a different world.";
                return;
            }
            if (responseCode == 14) {
                loginMessage1 = "The server is being updated.";
                loginMessage2 = "Please wait 1 minute and try again.";
                return;
            }
            if (responseCode == 15) {
                loggedIn = true;
                stream.currentOffset = 0;
                inStream.currentOffset = 0;
                packetOpcode = -1;
                anInt841 = -1;
                anInt842 = -1;
                anInt843 = -1;
                packetLength = 0;
                ticksSincelastPacketReceived = 0;
                timeUntilSystemUpdate = 0;
                menuActionRow = 0;
                menuOpen = false;
                aLong824 = System.currentTimeMillis();
                return;
            }
            if (responseCode == 16) {
                loginMessage1 = "Login attempts exceeded.";
                loginMessage2 = "Please wait 1 minute and try again.";
                return;
            }
            if (responseCode == 17) {
                loginMessage1 = "You are standing in a members-only area.";
                loginMessage2 = "To play on this world move to a free area first";
                return;
            }
            if (responseCode == 20) {
                loginMessage1 = "Invalid loginserver requested";
                loginMessage2 = "Please try using a different world.";
                return;
            }
            if (responseCode == 21) {
                for (int k1 = socketStream.read(); k1 >= 0; k1--) {
                    loginMessage1 = "You have only just left another world";
                    loginMessage2 = "Your profile will be transferred in: " + k1 + " seconds";
                    drawLoginScreen(true);
                    
                    try {
                        Thread.sleep(1000L);
                    } catch (InterruptedException _ex) {
                    }
                }
                login(playerName, playerPassword, flag);
                return;
            }
            if (responseCode == -1) {
                if (i1 == 0) {
                    if (loginFailures < 2) {
                        try {
                            Thread.sleep(2000L);
                        } catch (InterruptedException _ex) {
                        }
                        loginFailures++;
                        login(playerName, playerPassword, flag);
                        return;
                    } else {
                        loginMessage1 = "No response from loginserver";
                        loginMessage2 = "Please wait 1 minute and try again.";
                        return;
                    }
                } else {
                    loginMessage1 = "No response from server";
                    loginMessage2 = "Please try using a different world.";
                    return;
                }
            } else {
                System.out.println("response:" + responseCode);
                loginMessage1 = "Unexpected server response";
                loginMessage2 = "Please try using a different world.";
                return;
            }
        } catch (IOException _ex) {
            loginMessage1 = "";
        }
        loginMessage2 = "Error connecting to server.";
    }

    private boolean walkTo(int i, int j, int k, int i1, int y, int k1, int l1, int targetY, int x, boolean flag,
            int targetX) {
        byte byte0 = 104;
        byte byte1 = 104;

        for (int l2 = 0; l2 < byte0; l2++) {
            for (int i3 = 0; i3 < byte1; i3++) {
                anIntArrayArray901[l2][i3] = 0;
                anIntArrayArray825[l2][i3] = 0x5f5e0ff;
            }
        }
        int tmpX = x;
        int tmpY = y;
        anIntArrayArray901[x][y] = 99;
        anIntArrayArray825[x][y] = 0;
        int l3 = 0;
        int i4 = 0;
        bigX[l3] = x;
        bigY[l3++] = y;
        boolean successful = false;
        int j4 = bigX.length;
        int ai[][] = clipMap[plane].mapFlags;

        while (i4 != l3) {
            tmpX = bigX[i4];
            tmpY = bigY[i4];
            i4 = (i4 + 1) % j4;
            if (tmpX == targetX && tmpY == targetY) {
                successful = true;
                break;
            }
            if (i1 != 0) {
                if ((i1 < 5 || i1 == 10) && clipMap[plane].method219(targetX, tmpX, tmpY, j, i1 - 1, targetY)) {
                    successful = true;
                    break;
                }
                if (i1 < 10 && clipMap[plane].method220(targetX, targetY, tmpY, i1 - 1, j, tmpX)) {
                    successful = true;
                    break;
                }
            }
            if (k1 != 0 && k != 0 && clipMap[plane].method221(targetY, targetX, tmpX, k, l1, k1, tmpY)) {
                successful = true;
                break;
            }
            int l4 = anIntArrayArray825[tmpX][tmpY] + 1;
            if (tmpX > 0 && anIntArrayArray901[tmpX - 1][tmpY] == 0 && (ai[tmpX - 1][tmpY] & 0x1280108) == 0) {
                bigX[l3] = tmpX - 1;
                bigY[l3] = tmpY;
                l3 = (l3 + 1) % j4;
                anIntArrayArray901[tmpX - 1][tmpY] = 2;
                anIntArrayArray825[tmpX - 1][tmpY] = l4;
            }
            if (tmpX < byte0 - 1 && anIntArrayArray901[tmpX + 1][tmpY] == 0 && (ai[tmpX + 1][tmpY] & 0x1280180) == 0) {
                bigX[l3] = tmpX + 1;
                bigY[l3] = tmpY;
                l3 = (l3 + 1) % j4;
                anIntArrayArray901[tmpX + 1][tmpY] = 8;
                anIntArrayArray825[tmpX + 1][tmpY] = l4;
            }
            if (tmpY > 0 && anIntArrayArray901[tmpX][tmpY - 1] == 0 && (ai[tmpX][tmpY - 1] & 0x1280102) == 0) {
                bigX[l3] = tmpX;
                bigY[l3] = tmpY - 1;
                l3 = (l3 + 1) % j4;
                anIntArrayArray901[tmpX][tmpY - 1] = 1;
                anIntArrayArray825[tmpX][tmpY - 1] = l4;
            }
            if (tmpY < byte1 - 1 && anIntArrayArray901[tmpX][tmpY + 1] == 0 && (ai[tmpX][tmpY + 1] & 0x1280120) == 0) {
                bigX[l3] = tmpX;
                bigY[l3] = tmpY + 1;
                l3 = (l3 + 1) % j4;
                anIntArrayArray901[tmpX][tmpY + 1] = 4;
                anIntArrayArray825[tmpX][tmpY + 1] = l4;
            }
            if (tmpX > 0 && tmpY > 0 && anIntArrayArray901[tmpX - 1][tmpY - 1] == 0 && (ai[tmpX - 1][tmpY - 1] & 0x128010e) == 0
                    && (ai[tmpX - 1][tmpY] & 0x1280108) == 0 && (ai[tmpX][tmpY - 1] & 0x1280102) == 0) {
                bigX[l3] = tmpX - 1;
                bigY[l3] = tmpY - 1;
                l3 = (l3 + 1) % j4;
                anIntArrayArray901[tmpX - 1][tmpY - 1] = 3;
                anIntArrayArray825[tmpX - 1][tmpY - 1] = l4;
            }
            if (tmpX < byte0 - 1 && tmpY > 0 && anIntArrayArray901[tmpX + 1][tmpY - 1] == 0 && (ai[tmpX + 1][tmpY - 1] & 0x1280183) == 0
                    && (ai[tmpX + 1][tmpY] & 0x1280180) == 0 && (ai[tmpX][tmpY - 1] & 0x1280102) == 0) {
                bigX[l3] = tmpX + 1;
                bigY[l3] = tmpY - 1;
                l3 = (l3 + 1) % j4;
                anIntArrayArray901[tmpX + 1][tmpY - 1] = 9;
                anIntArrayArray825[tmpX + 1][tmpY - 1] = l4;
            }
            if (tmpX > 0 && tmpY < byte1 - 1 && anIntArrayArray901[tmpX - 1][tmpY + 1] == 0 && (ai[tmpX - 1][tmpY + 1] & 0x1280138) == 0
                    && (ai[tmpX - 1][tmpY] & 0x1280108) == 0 && (ai[tmpX][tmpY + 1] & 0x1280120) == 0) {
                bigX[l3] = tmpX - 1;
                bigY[l3] = tmpY + 1;
                l3 = (l3 + 1) % j4;
                anIntArrayArray901[tmpX - 1][tmpY + 1] = 6;
                anIntArrayArray825[tmpX - 1][tmpY + 1] = l4;
            }
            if (tmpX < byte0 - 1 && tmpY < byte1 - 1 && anIntArrayArray901[tmpX + 1][tmpY + 1] == 0 && (ai[tmpX + 1][tmpY + 1] & 0x12801e0) == 0
                    && (ai[tmpX + 1][tmpY] & 0x1280180) == 0 && (ai[tmpX][tmpY + 1] & 0x1280120) == 0) {
                bigX[l3] = tmpX + 1;
                bigY[l3] = tmpY + 1;
                l3 = (l3 + 1) % j4;
                anIntArrayArray901[tmpX + 1][tmpY + 1] = 12;
                anIntArrayArray825[tmpX + 1][tmpY + 1] = l4;
            }
        }
        anInt1264 = 0;

        if (!successful) {
            if (flag) {
                int i5 = 100;

                for (int k5 = 1; k5 < 2; k5++) {
                    for (int i6 = targetX - k5; i6 <= targetX + k5; i6++) {
                        for (int l6 = targetY - k5; l6 <= targetY + k5; l6++) {
                            if (i6 >= 0 && l6 >= 0 && i6 < 104 && l6 < 104 && anIntArrayArray825[i6][l6] < i5) {
                                i5 = anIntArrayArray825[i6][l6];
                                tmpX = i6;
                                tmpY = l6;
                                anInt1264 = 1;
                                successful = true;
                            }
                        }
                    }

                    if (successful) {
                        break;
                    }
                }

            }

            if (!successful) {
                return false;
            }
        }
        i4 = 0;
        bigX[i4] = tmpX;
        bigY[i4++] = tmpY;
        int l5;
        for (int j5 = l5 = anIntArrayArray901[tmpX][tmpY]; tmpX != x || tmpY != y; j5 = anIntArrayArray901[tmpX][tmpY]) {
            if (j5 != l5) {
                l5 = j5;
                bigX[i4] = tmpX;
                bigY[i4++] = tmpY;
            }
            if ((j5 & 2) != 0) {
                tmpX++;
            } else if ((j5 & 8) != 0) {
                tmpX--;
            }
            if ((j5 & 1) != 0) {
                tmpY++;
            } else if ((j5 & 4) != 0) {
                tmpY--;
            }
        }
        // if(cancelWalk) { return i4 > 0; }

        if (i4 > 0) {
            int k4 = i4;
            if (k4 > 25) {
                k4 = 25;
            }
            i4--;
            int k6 = bigX[i4];
            int i7 = bigY[i4];
            anInt1288 += k4;
            if (anInt1288 >= 92) {
                stream.writePacketHeaderEnc(36);
                stream.writeInt(0);
                anInt1288 = 0;
            }
            if (i == 0) {
                stream.writePacketHeaderEnc(164);
                stream.writeByte(k4 + k4 + 3);
            }
            if (i == 1) {
                stream.writePacketHeaderEnc(248);
                stream.writeByte(k4 + k4 + 3 + 14);
            }
            if (i == 2) {
                stream.writePacketHeaderEnc(98);
                stream.writeByte(k4 + k4 + 3);
            }
            stream.writeShortLEA(k6 + baseX);
            destX = bigX[0];
            destY = bigY[0];
            
            for (int j7 = 1; j7 < k4; j7++) {
                i4--;
                stream.writeByte(bigX[i4] - k6);
                stream.writeByte(bigY[i4] - i7);
            }
            stream.writeShortLE(i7 + baseY);
            stream.writeByteC(super.keyArray[5] != 1 ? 0 : 1);
            return true;
        }
        return i != 1;
    }

    private void updateNpcByFlag(Stream stream) {
        for (int j = 0; j < anInt893; j++) {
            int k = updatingPlayersList[j];
            NPC npc = npcArray[k];
            int l = stream.readUByte();

            if ((l & 0x10) != 0) {
                int animationId = stream.readUShortLE();

                if (animationId == 65535) {
                    animationId = -1;
                }
                int i2 = stream.readUByte();

                if (animationId == npc.currentAnimationId && animationId != -1) {
                    int l2 = Animation.anims[animationId].anInt365;

                    if (l2 == 1) {
                        npc.anInt1527 = 0;
                        npc.anInt1528 = 0;
                        npc.anInt1529 = i2;
                        npc.anInt1530 = 0;
                    }

                    if (l2 == 2) {
                        npc.anInt1530 = 0;
                    }
                } else if (animationId == -1 || npc.currentAnimationId == -1
                        || Animation.anims[animationId].anInt359 >= Animation.anims[npc.currentAnimationId].anInt359) {
                    npc.currentAnimationId = animationId;
                    npc.anInt1527 = 0;
                    npc.anInt1528 = 0;
                    npc.anInt1529 = i2;
                    npc.anInt1530 = 0;
                    npc.anInt1542 = npc.smallXYIndex;
                }
            }
            if ((l & 8) != 0) {
                int hitValue = stream.readUByteA();
                int hitMarkType = stream.readUByteC();
                npc.updateHitData(hitMarkType, hitValue, loopCycle);
                npc.loopCycleStatus = loopCycle + 300;
                npc.currentHealth = stream.readUByteA();
                npc.maxHealth = stream.readUByte();
            }
            if ((l & 0x80) != 0) {
                npc.currentGraphicsId = stream.readUShort();
                int delay = stream.readUInt();
                npc.currentGraphicsDelaySegment = delay >> 16;
                npc.currentGraphicsTargetLoop = loopCycle + (delay & 0xffff);
                npc.anInt1521 = 0;
                npc.anInt1522 = 0;

                if (npc.currentGraphicsTargetLoop > loopCycle) {
                    npc.anInt1521 = -1;
                }

                if (npc.currentGraphicsId == 65535) {
                    npc.currentGraphicsId = -1;
                }
            }
            if ((l & 0x20) != 0) {
                npc.interactingEntityId = stream.readUShort();

                if (npc.interactingEntityId == 65535) {
                    npc.interactingEntityId = -1;
                }
            }
            if ((l & 1) != 0) {
                npc.textSpoken = stream.readString();
                npc.textCycle = 100;
                // entityMessage(npc);
            }
            if ((l & 0x40) != 0) {
                int l1 = stream.readUByteC();
                int k2 = stream.readUByteS();
                npc.updateHitData(k2, l1, loopCycle);
                npc.loopCycleStatus = loopCycle + 300;
                npc.currentHealth = stream.readUByteS();
                npc.maxHealth = stream.readUByteC();
            }
            if ((l & 2) != 0) {
                npc.desc = EntityDef.forID(stream.readUShortLEA());
                npc.anInt1540 = npc.desc.aByte68;
                npc.anInt1504 = npc.desc.anInt79;
                npc.anInt1554 = npc.desc.anInt67;
                npc.anInt1555 = npc.desc.anInt58;
                npc.anInt1556 = npc.desc.anInt83;
                npc.anInt1557 = npc.desc.anInt55;
                npc.anInt1511 = npc.desc.anInt77;
            }
            if ((l & 4) != 0) {
                npc.facingCoordinateX = stream.readUShortLE();
                npc.facingCoordinateY = stream.readUShortLE();
            }
        }
    }

    private void buildAtNPCMenu(EntityDef entityDef, int cmd1, int cmd3, int cmd2) {
        if (menuActionRow >= 400) {
            return;
        }
        
        if (entityDef.childrenIDs != null) {
            entityDef = entityDef.method161();
        }
        
        if (entityDef == null) {
            return;
        }
        
        if (!entityDef.aBoolean84) {
            return;
        }
        String s = entityDef.name;
        
        if (entityDef.combatLevel != 0) {
            s = s + combatDiffColor(myPlayer.combatLevel, entityDef.combatLevel) + " (level-" + entityDef.combatLevel + ")";
        }
        
        if (itemSelected == 1) {
            menuActionName[menuActionRow] = "Use " + selectedItemName + " with @yel@" + s;
            menuActionID[menuActionRow] = 582;
            menuActionCmd1[menuActionRow] = cmd1;
            menuActionCmd2[menuActionRow] = cmd2;
            menuActionCmd3[menuActionRow] = cmd3;
            menuActionRow++;
            return;
        }
        if (spellSelected == 1) {
            if ((spellUsableOn & 2) == 2) {
                menuActionName[menuActionRow] = spellTooltip + " @yel@" + s;
                menuActionID[menuActionRow] = 413;
                menuActionCmd1[menuActionRow] = cmd1;
                menuActionCmd2[menuActionRow] = cmd2;
                menuActionCmd3[menuActionRow] = cmd3;
                menuActionRow++;
            }
        } else {
            if (entityDef.actions != null) {
                for (int l = 4; l >= 0; l--) {
                    if (entityDef.actions[l] != null && !entityDef.actions[l].equalsIgnoreCase("attack")) {
                        menuActionName[menuActionRow] = entityDef.actions[l] + " @yel@" + s;
                        
                        if (l == 0) {
                            menuActionID[menuActionRow] = 20;
                        }
                        if (l == 1) {
                            menuActionID[menuActionRow] = 412;
                        }
                        if (l == 2) {
                            menuActionID[menuActionRow] = 225;
                        }
                        if (l == 3) {
                            menuActionID[menuActionRow] = 965;
                        }
                        if (l == 4) {
                            menuActionID[menuActionRow] = 478;
                        }
                        menuActionCmd1[menuActionRow] = cmd1;
                        menuActionCmd2[menuActionRow] = cmd2;
                        menuActionCmd3[menuActionRow] = cmd3;
                        menuActionRow++;
                    }
                }
            }
            
            if (entityDef.actions != null) {
                for (int i = 4; i >= 0; i--) {
                    if (entityDef.actions[i] != null && entityDef.actions[i].equalsIgnoreCase("attack")) {
                        char c = '\0';
                        
                        if (entityDef.combatLevel > myPlayer.combatLevel) {
                            c = '\u07D0';
                        }
                        menuActionName[menuActionRow] = entityDef.actions[i] + " @yel@" + s;
                        
                        if (i == 0) {
                            menuActionID[menuActionRow] = 20 + c;
                        }
                        if (i == 1) {
                            menuActionID[menuActionRow] = 412 + c;
                        }
                        if (i == 2) {
                            menuActionID[menuActionRow] = 225 + c;
                        }
                        if (i == 3) {
                            menuActionID[menuActionRow] = 965 + c;
                        }
                        if (i == 4) {
                            menuActionID[menuActionRow] = 478 + c;
                        }
                        menuActionCmd1[menuActionRow] = cmd1;
                        menuActionCmd2[menuActionRow] = cmd2;
                        menuActionCmd3[menuActionRow] = cmd3;
                        menuActionRow++;
                    }
                }
            }
            menuActionName[menuActionRow] = "Examine @yel@" + s + " @gre@(@whi@" + entityDef.type + "@gre@)";
            menuActionID[menuActionRow] = 1025;
            menuActionCmd1[menuActionRow] = cmd1;
            menuActionCmd2[menuActionRow] = cmd2;
            menuActionCmd3[menuActionRow] = cmd3;
            menuActionRow++;
        }
    }

    private void buildAtPlayerMenu(int cmd2, int cmd1, Player player, int cmd3) {
        if (player == myPlayer) {
            return;
        }
        
        if (menuActionRow >= 400) {
            return;
        }
        String s;
        
        if (player.skill == 0) {
            s = player.name + combatDiffColor(myPlayer.combatLevel, player.combatLevel) + " (level-" + player.combatLevel + ")";
        } else {
            s = player.name + " (skill-" + player.skill + ")";
        }
        
        if (itemSelected == 1) {
            menuActionName[menuActionRow] = "Use " + selectedItemName + " with @whi@" + s;
            menuActionID[menuActionRow] = 491;
            menuActionCmd1[menuActionRow] = cmd1;
            menuActionCmd2[menuActionRow] = cmd2;
            menuActionCmd3[menuActionRow] = cmd3;
            menuActionRow++;
        } else if (spellSelected == 1) {
            if ((spellUsableOn & 8) == 8) {
                menuActionName[menuActionRow] = spellTooltip + " @whi@" + s;
                menuActionID[menuActionRow] = 365;
                menuActionCmd1[menuActionRow] = cmd1;
                menuActionCmd2[menuActionRow] = cmd2;
                menuActionCmd3[menuActionRow] = cmd3;
                menuActionRow++;
            }
        } else {
            for (int l = 4; l >= 0; l--) {
                if (atPlayerActions[l] != null) {
                    menuActionName[menuActionRow] = atPlayerActions[l] + " @whi@" + s;
                    char c = '\0';
                    
                    if (atPlayerActions[l].equalsIgnoreCase("attack")) {
                        if (player.combatLevel > myPlayer.combatLevel) {
                            c = '\u07D0';
                        }
                        
                        if (myPlayer.team != 0 && player.team != 0) {
                            if (myPlayer.team == player.team) {
                                c = '\u07D0';
                            } else {
                                c = '\0';
                            }
                        }
                    } else if (atPlayerArray[l]) {
                        c = '\u07D0';
                    }
                    if (l == 0) {
                        menuActionID[menuActionRow] = 561 + c;
                    }
                    if (l == 1) {
                        menuActionID[menuActionRow] = 779 + c;
                    }
                    if (l == 2) {
                        menuActionID[menuActionRow] = 27 + c;
                    }
                    if (l == 3) {
                        menuActionID[menuActionRow] = 577 + c;
                    }
                    if (l == 4) {
                        menuActionID[menuActionRow] = 729 + c;
                    }
                    menuActionCmd1[menuActionRow] = cmd1;
                    menuActionCmd2[menuActionRow] = cmd2;
                    menuActionCmd3[menuActionRow] = cmd3;
                    menuActionRow++;
                }
            }
        }
        
        for (int i1 = 0; i1 < menuActionRow; i1++) {
            if (menuActionID[i1] == 516) {
                menuActionName[i1] = "Walk here @whi@" + s;
                return;
            }
        }
    }

    private void method89(Class30_Sub1 class30_sub1) {
        int uid = 0;
        int j = -1;
        int k = 0;
        int l = 0;
        
        if (class30_sub1.objectType == 0) {
            uid = worldController.getObject1Uid(class30_sub1.plane, class30_sub1.x, class30_sub1.y);
        }
        
        if (class30_sub1.objectType == 1) {
            uid = worldController.getObject2Uid(class30_sub1.plane, class30_sub1.x, class30_sub1.y);
        }
        
        if (class30_sub1.objectType == 2) {
            uid = worldController.getObject5Uid(class30_sub1.plane, class30_sub1.x, class30_sub1.y);
        }
        
        if (class30_sub1.objectType == 3) {
            uid = worldController.getObject3Uid(class30_sub1.plane, class30_sub1.x, class30_sub1.y);
        }
        
        if (uid != 0) {
            int i1 = worldController.method304(class30_sub1.plane, class30_sub1.x, class30_sub1.y, uid);
            j = uid >> 14 & 0x7fff;
            k = i1 & 0x1f;
            l = i1 >> 6;
        }
        class30_sub1.anInt1299 = j;
        class30_sub1.anInt1301 = k;
        class30_sub1.anInt1300 = l;
    }

    private void method90() {
        for (int i = 0; i < soundSlot; i++) {
            if (soundFinishTime[i] <= 0) {
                boolean flag1 = false;
                
                try {
                    if (soundIds[i] == anInt874 && soundVolume[i] == anInt1289) {
                        if (!replayWave()) {
                            flag1 = true;
                        }
                    } else {
                        Stream strm = Sound.method241(soundVolume[i], soundIds[i]);

                        if (System.currentTimeMillis() + (long) (strm.currentOffset / 22) > aLong1172 + (long) (anInt1257 / 22)) {
                            anInt1257 = strm.currentOffset;
                            aLong1172 = System.currentTimeMillis();
                            
                            if (saveWave(strm.buffer, strm.currentOffset)) {
                                anInt874 = soundIds[i];
                                anInt1289 = soundVolume[i];
                            } else {
                                flag1 = true;
                            }
                        }
                    }
                } catch (Exception exception) {
                }
                
                if (!flag1 || soundFinishTime[i] == -5) {
                    soundSlot--;
                    
                    for (int j = i; j < soundSlot; j++) {
                        soundIds[j] = soundIds[j + 1];
                        soundVolume[j] = soundVolume[j + 1];
                        soundFinishTime[j] = soundFinishTime[j + 1];
                    }
                    i--;
                } else {
                    soundFinishTime[i] = -5;
                }
            } else {
                soundFinishTime[i]--;
            }
        }

        if (prevSong > 0) {
            prevSong -= 20;
            
            if (prevSong < 0) {
                prevSong = 0;
            }
            
            if (prevSong == 0 && musicEnabled && !lowMem) {
                nextSong = currentSong;
                songChanging = true;
                onDemandFetcher.fetchItem(2, nextSong);
            }
        }
    }

    void startUp() {
        drawLoadingText(20, "Starting up");
        
        if (Signlink.sunjava) {
            super.minDelay = 5;
        }
        
        if (aBoolean993) {
            // rsAlreadyLoaded = true;
            // return;
        }
        aBoolean993 = true;
        boolean validHost = true;
        String s = getDocumentBaseHost();
        
        if (s.endsWith("jagex.com")) {
            validHost = true;
        }
        
        if (s.endsWith("runescape.com")) {
            validHost = true;
        }
        
        if (s.endsWith("192.168.1.2")) {
            validHost = true;
        }
        
        if (s.endsWith("192.168.1.229")) {
            validHost = true;
        }
        
        if (s.endsWith("192.168.1.228")) {
            validHost = true;
        }
        
        if (s.endsWith("192.168.1.227")) {
            validHost = true;
        }
        
        if (s.endsWith("192.168.1.226")) {
            validHost = true;
        }
        
        if (s.endsWith("127.0.0.1")) {
            validHost = true;
        }
        
        if (!validHost) {
            genericLoadingError = true;
            return;
        }
        
        if (Signlink.cache_dat != null) {
            for (int i = 0; i < 5; i++) {
                decompressors[i] = new Decompression(Signlink.cache_dat, Signlink.cache_idx[i], i + 1);
            }
        }
        
        try {
            connectServer();
            titleStreamLoader = streamLoaderForName(1, "title screen", "title", expectedCRCs[1], 25);
            aTextDrawingArea_1270 = new TextDrawingArea(false, "p11_full", titleStreamLoader);
            loadingTextArea = new TextDrawingArea(false, "p12_full", titleStreamLoader);
            chatTextDrawingArea = new TextDrawingArea(false, "b12_full", titleStreamLoader);
            TextDrawingArea aTextDrawingArea_1273 = new TextDrawingArea(true, "q8_full", titleStreamLoader);
            drawLogo();
            loadTitleScreen();
            StreamLoader configLoader = streamLoaderForName(2, "config", "config", expectedCRCs[2], 30);
            StreamLoader interfaceLoader = streamLoaderForName(3, "interface", "interface", expectedCRCs[3], 35);
            StreamLoader mediaLoader = streamLoaderForName(4, "2d graphics", "media", expectedCRCs[4], 40);
            StreamLoader textureLoader = streamLoaderForName(6, "textures", "textures", expectedCRCs[6], 45);
            StreamLoader chatLoader = streamLoaderForName(7, "chat system", "wordenc", expectedCRCs[7], 50);
            StreamLoader soundLoader = streamLoaderForName(8, "sound effects", "sounds", expectedCRCs[8], 55);
            byteGroundArray = new byte[4][104][104];
            intGroundArray = new int[4][105][105];
            worldController = new WorldController(intGroundArray);

            for (int j = 0; j < 4; j++) {
                clipMap[j] = new ClipMap();
            }

            sprite = new Sprite(512, 512);
            StreamLoader updateListLoader = streamLoaderForName(5, "update list", "versionlist", expectedCRCs[5], 60);
            drawLoadingText(60, "Connecting to update server");
            onDemandFetcher = new OnDemandFetcher();
            onDemandFetcher.start(updateListLoader, this);
            Class36.method528(onDemandFetcher.getAnimationCount());
            Model.method459(onDemandFetcher.getVersionCount(0), onDemandFetcher);

            if (!lowMem) {
                nextSong = 0;

                try {
                    nextSong = Integer.parseInt(getParameter("music"));
                } catch (Exception _ex) {
                }
                songChanging = true;
                onDemandFetcher.fetchItem(2, nextSong);

                while (onDemandFetcher.getNodeCount() > 0) {
                    processOnDemandQueue();

                    try {
                        Thread.sleep(100L);
                    } catch (Exception _ex) {
                    }

                    if (onDemandFetcher.anInt1349 > 3) {
                        loadError();
                        return;
                    }
                }
            }
            drawLoadingText(65, "Requesting animations");
            int k = onDemandFetcher.getVersionCount(1);

            for (int i1 = 0; i1 < k; i1++) {
                onDemandFetcher.fetchItem(1, i1);
            }

            while (onDemandFetcher.getNodeCount() > 0) {
                int j1 = k - onDemandFetcher.getNodeCount();

                if (j1 > 0) {
                    drawLoadingText(65, "Loading animations - " + (j1 * 100) / k + "%");
                }
                processOnDemandQueue();

                try {
                    Thread.sleep(100L);
                } catch (Exception _ex) {
                }

                if (onDemandFetcher.anInt1349 > 3) {
                    loadError();
                    return;
                }
            }
            drawLoadingText(70, "Requesting models");
            k = onDemandFetcher.getVersionCount(0);

            for (int k1 = 0; k1 < k; k1++) {
                int l1 = onDemandFetcher.getModelIndex(k1);

                if ((l1 & 1) != 0) {
                    onDemandFetcher.fetchItem(0, k1);
                }
            }

            k = onDemandFetcher.getNodeCount();
            while (onDemandFetcher.getNodeCount() > 0) {
                int i2 = k - onDemandFetcher.getNodeCount();

                if (i2 > 0) {
                    drawLoadingText(70, "Loading models - " + (i2 * 100) / k + "%");
                }
                processOnDemandQueue();

                try {
                    Thread.sleep(100L);
                } catch (Exception _ex) {
                }
            }
            if (decompressors[0] != null) {
                drawLoadingText(75, "Requesting maps");
                onDemandFetcher.fetchItem(3, onDemandFetcher.method562(0, 48, 47));
                onDemandFetcher.fetchItem(3, onDemandFetcher.method562(1, 48, 47));
                onDemandFetcher.fetchItem(3, onDemandFetcher.method562(0, 48, 48));
                onDemandFetcher.fetchItem(3, onDemandFetcher.method562(1, 48, 48));
                onDemandFetcher.fetchItem(3, onDemandFetcher.method562(0, 48, 49));
                onDemandFetcher.fetchItem(3, onDemandFetcher.method562(1, 48, 49));
                onDemandFetcher.fetchItem(3, onDemandFetcher.method562(0, 47, 47));
                onDemandFetcher.fetchItem(3, onDemandFetcher.method562(1, 47, 47));
                onDemandFetcher.fetchItem(3, onDemandFetcher.method562(0, 47, 48));
                onDemandFetcher.fetchItem(3, onDemandFetcher.method562(1, 47, 48));
                onDemandFetcher.fetchItem(3, onDemandFetcher.method562(0, 148, 48));
                onDemandFetcher.fetchItem(3, onDemandFetcher.method562(1, 148, 48));
                k = onDemandFetcher.getNodeCount();

                while (onDemandFetcher.getNodeCount() > 0) {
                    int j2 = k - onDemandFetcher.getNodeCount();

                    if (j2 > 0) {
                        drawLoadingText(75, "Loading maps - " + (j2 * 100) / k + "%");
                    }
                    processOnDemandQueue();

                    try {
                        Thread.sleep(100L);
                    } catch (Exception _ex) {
                    }
                }
            }
            k = onDemandFetcher.getVersionCount(0);

            for (int k2 = 0; k2 < k; k2++) {
                int l2 = onDemandFetcher.getModelIndex(k2);
                byte byte0 = 0;

                if ((l2 & 8) != 0) {
                    byte0 = 10;
                } else if ((l2 & 0x20) != 0) {
                    byte0 = 9;
                } else if ((l2 & 0x10) != 0) {
                    byte0 = 8;
                } else if ((l2 & 0x40) != 0) {
                    byte0 = 7;
                } else if ((l2 & 0x80) != 0) {
                    byte0 = 6;
                } else if ((l2 & 2) != 0) {
                    byte0 = 5;
                } else if ((l2 & 4) != 0) {
                    byte0 = 4;
                }

                if ((l2 & 1) != 0) {
                    byte0 = 3;
                }

                if (byte0 != 0) {
                    onDemandFetcher.method563(byte0, 0, k2);
                }
            }
            onDemandFetcher.method554(isMembers);

            if (!lowMem) {
                int l = onDemandFetcher.getVersionCount(2);

                for (int i3 = 1; i3 < l; i3++) {
                    if (onDemandFetcher.method569(i3)) {
                        onDemandFetcher.method563((byte) 1, 2, i3);
                    }
                }
            }
            drawLoadingText(80, "Unpacking media");
            invBack = new Background(mediaLoader, "invback", 0);
            chatBack = new Background(mediaLoader, "chatback", 0);
            mapBack = new Background(mediaLoader, "mapback", 0);
            backBase1 = new Background(mediaLoader, "backbase1", 0);
            backBase2 = new Background(mediaLoader, "backbase2", 0);
            backHmid1 = new Background(mediaLoader, "backhmid1", 0);

            for (int j3 = 0; j3 < 13; j3++) {
                sideIcons[j3] = new Background(mediaLoader, "sideicons", j3);
            }
            compass = new Sprite(mediaLoader, "compass", 0);
            mapEdge = new Sprite(mediaLoader, "mapedge", 0);
            mapEdge.method345();

            try {
                for (int k3 = 0; k3 < 100; k3++) {
                    mapScenes[k3] = new Background(mediaLoader, "mapscene", k3);
                }

            } catch (Exception _ex) {
            }

            try {
                for (int l3 = 0; l3 < 100; l3++) {
                    mapFunctions[l3] = new Sprite(mediaLoader, "mapfunction", l3);
                }

            } catch (Exception _ex) {
            }

            try {
                for (int i4 = 0; i4 < 20; i4++) {
                    hitMarks[i4] = new Sprite(mediaLoader, "hitmarks", i4);
                }

            } catch (Exception _ex) {
            }

            try {
                for (int j4 = 0; j4 < 20; j4++) {
                    headIcons[j4] = new Sprite(mediaLoader, "headicons", j4);
                }

            } catch (Exception _ex) {
            }
            mapFlag = new Sprite(mediaLoader, "mapmarker", 0);
            mapMarker = new Sprite(mediaLoader, "mapmarker", 1);

            for (int k4 = 0; k4 < 8; k4++) {
                crosses[k4] = new Sprite(mediaLoader, "cross", k4);
            }
            mapDotItem = new Sprite(mediaLoader, "mapdots", 0);
            mapDotNPC = new Sprite(mediaLoader, "mapdots", 1);
            mapDotPlayer = new Sprite(mediaLoader, "mapdots", 2);
            mapDotFriend = new Sprite(mediaLoader, "mapdots", 3);
            mapDotTeam = new Sprite(mediaLoader, "mapdots", 4);
            scrollBar1 = new Background(mediaLoader, "scrollbar", 0);
            scrollBar2 = new Background(mediaLoader, "scrollbar", 1);
            redStone1 = new Background(mediaLoader, "redstone1", 0);
            redStone2 = new Background(mediaLoader, "redstone2", 0);
            redStone3 = new Background(mediaLoader, "redstone3", 0);
            redStone1_2 = new Background(mediaLoader, "redstone1", 0);
            redStone1_2.method358();
            redStone2_2 = new Background(mediaLoader, "redstone2", 0);
            redStone2_2.method358();
            redStone1_3 = new Background(mediaLoader, "redstone1", 0);
            redStone1_3.method359();
            redStone2_3 = new Background(mediaLoader, "redstone2", 0);
            redStone2_3.method359();
            redStone3_2 = new Background(mediaLoader, "redstone3", 0);
            redStone3_2.method359();
            redStone1_4 = new Background(mediaLoader, "redstone1", 0);
            redStone1_4.method358();
            redStone1_4.method359();
            redStone2_4 = new Background(mediaLoader, "redstone2", 0);
            redStone2_4.method358();
            redStone2_4.method359();

            for (int l4 = 0; l4 < 2; l4++) {
                modIcons[l4] = new Background(mediaLoader, "mod_icons", l4);
            }
            Sprite sprite = new Sprite(mediaLoader, "backleft1", 0);
            backLeftIP1 = new RSImageProducer(sprite.spriteWidth, sprite.spriteHeight, getGameComponent());
            sprite.method346(0, 0);
            sprite = new Sprite(mediaLoader, "backleft2", 0);
            backLeftIP2 = new RSImageProducer(sprite.spriteWidth, sprite.spriteHeight, getGameComponent());
            sprite.method346(0, 0);
            sprite = new Sprite(mediaLoader, "backright1", 0);
            backRightIP1 = new RSImageProducer(sprite.spriteWidth, sprite.spriteHeight, getGameComponent());
            sprite.method346(0, 0);
            sprite = new Sprite(mediaLoader, "backright2", 0);
            backRightIP2 = new RSImageProducer(sprite.spriteWidth, sprite.spriteHeight, getGameComponent());
            sprite.method346(0, 0);
            sprite = new Sprite(mediaLoader, "backtop1", 0);
            backTopIP1 = new RSImageProducer(sprite.spriteWidth, sprite.spriteHeight, getGameComponent());
            sprite.method346(0, 0);
            sprite = new Sprite(mediaLoader, "backvmid1", 0);
            backVmidIP1 = new RSImageProducer(sprite.spriteWidth, sprite.spriteHeight, getGameComponent());
            sprite.method346(0, 0);
            sprite = new Sprite(mediaLoader, "backvmid2", 0);
            backVmidIP2 = new RSImageProducer(sprite.spriteWidth, sprite.spriteHeight, getGameComponent());
            sprite.method346(0, 0);
            sprite = new Sprite(mediaLoader, "backvmid3", 0);
            backVmidIP3 = new RSImageProducer(sprite.spriteWidth, sprite.spriteHeight, getGameComponent());
            sprite.method346(0, 0);
            sprite = new Sprite(mediaLoader, "backhmid2", 0);
            backVmidIP2_2 = new RSImageProducer(sprite.spriteWidth, sprite.spriteHeight, getGameComponent());
            sprite.method346(0, 0);
            int i5 = (int) (Math.random() * 21D) - 10;
            int j5 = (int) (Math.random() * 21D) - 10;
            int k5 = (int) (Math.random() * 21D) - 10;
            int l5 = (int) (Math.random() * 41D) - 20;

            for (int i6 = 0; i6 < 100; i6++) {
                if (mapFunctions[i6] != null) {
                    mapFunctions[i6].method344(i5 + l5, j5 + l5, k5 + l5);
                }

                if (mapScenes[i6] != null) {
                    mapScenes[i6].method360(i5 + l5, j5 + l5, k5 + l5);
                }
            }
            drawLoadingText(83, "Unpacking textures");
            Texture.method368(textureLoader);
            Texture.method372(0.80000000000000004D);
            Texture.method367();
            drawLoadingText(86, "Unpacking config");
            Animation.unpackConfig(configLoader);
            ObjectDef.unpackConfig(configLoader);
            Flo.unpackConfig(configLoader);
            ItemDef.unpackConfig(configLoader);
            EntityDef.unpackConfig(configLoader);
            IDK.unpackConfig(configLoader);
            StaticAnimation.unpackConfig(configLoader);
            Varp.unpackConfig(configLoader);
            VarBit.unpackConfig(configLoader);
            ItemDef.isMembers = isMembers;

            if (!lowMem) {
                drawLoadingText(90, "Unpacking sounds");
                byte abyte0[] = soundLoader.getDataForName("sounds.dat");
                Stream stream = new Stream(abyte0);
                Sound.unpack(stream);
            }
            drawLoadingText(95, "Unpacking interfaces");
            TextDrawingArea aclass30_sub2_sub1_sub4s[] = {
                aTextDrawingArea_1270, loadingTextArea, chatTextDrawingArea, aTextDrawingArea_1273
            };
            RSInterface.unpack(interfaceLoader, aclass30_sub2_sub1_sub4s, mediaLoader);
            drawLoadingText(100, "Preparing game engine");

            for (int j6 = 0; j6 < 33; j6++) {
                int k6 = 999;
                int i7 = 0;

                for (int k7 = 0; k7 < 34; k7++) {
                    if (mapBack.bgPixels[k7 + j6 * mapBack.bgWidth] == 0) {
                        if (k6 == 999) {
                            k6 = k7;
                        }
                        continue;
                    }

                    if (k6 == 999) {
                        continue;
                    }
                    i7 = k7;
                    break;
                }
                anIntArray968[j6] = k6;
                anIntArray1057[j6] = i7 - k6;
            }

            for (int l6 = 5; l6 < 156; l6++) {
                int j7 = 999;
                int l7 = 0;

                for (int j8 = 25; j8 < 172; j8++) {
                    if (mapBack.bgPixels[j8 + l6 * mapBack.bgWidth] == 0 && (j8 > 34 || l6 > 34)) {
                        if (j7 == 999) {
                            j7 = j8;
                        }
                        continue;
                    }

                    if (j7 == 999) {
                        continue;
                    }
                    l7 = j8;
                    break;
                }

                anIntArray1052[l6 - 5] = j7 - 25;
                anIntArray1229[l6 - 5] = l7 - j7;
            }
            Texture.method365(479, 96);
            anIntArray1180 = Texture.anIntArray1472;
            Texture.method365(190, 261);
            anIntArray1181 = Texture.anIntArray1472;
            Texture.method365(512, 334);
            anIntArray1182 = Texture.anIntArray1472;
            int ai[] = new int[9];

            for (int i8 = 0; i8 < 9; i8++) {
                int k8 = 128 + i8 * 32 + 15;
                int l8 = 600 + k8 * 3;
                int i9 = Texture.anIntArray1470[k8];
                ai[i8] = l8 * i9 >> 16;
            }
            WorldController.method310(500, 800, 512, 334, ai);
            Censor.loadConfig(chatLoader);
            mouseDetection = new MouseDetection(this);
            startRunnable(mouseDetection, 10);
            Animable_Sub5.clientInstance = this;
            ObjectDef.instance = this;
            EntityDef.clientInstance = this;
            return;
        } catch (Exception ex) {
            Signlink.printError("loaderror " + loadingText + " " + loadingPercentage);
        }
        loadingError = true;
    }

    private void updatePlayerList(Stream stream, int i) {
        while (stream.bitPosition + 10 < i * 8) {
            int nextPlayerId = stream.readBits(11);

            // Max player cap check
            if (nextPlayerId == 2047) {
                break;
            }

            // Checking if the current player is cached
            if (playerArray[nextPlayerId] == null) {
                playerArray[nextPlayerId] = new Player();

                if (cachedPlayerUpdateBuffers[nextPlayerId] != null) {
                    playerArray[nextPlayerId].updatePlayer(cachedPlayerUpdateBuffers[nextPlayerId]);
                }
            }
            playerIndices[playerCount++] = nextPlayerId;
            Player player = playerArray[nextPlayerId];
            player.loopCycle = loopCycle;
            int flag1 = stream.readBits(1);

            if (flag1 == 1) {
                updatingPlayersList[anInt893++] = nextPlayerId;
            }
            int flag2 = stream.readBits(1);
            int deltaY = stream.readBits(5);

            if (deltaY > 15) {
                deltaY -= 32;
            }
            int deltaX = stream.readBits(5);

            if (deltaX > 15) {
                deltaX -= 32;
            }
            player.setPosition(myPlayer.smallX[0] + deltaX, myPlayer.smallY[0] + deltaY, flag2 == 1);
        }
        stream.finishBitAccess();
    }

    private void processMainScreenClick() {
        if (minimapState != 0) {
            return;
        }
        if (super.clickMode3 == 1) {
            int i = super.saveClickX - 25 - 550;
            int j = super.saveClickY - 5 - 4;
            if (i >= 0 && j >= 0 && i < 146 && j < 151) {
                i -= 73;
                j -= 75;
                int k = minimapInt1 + minimapInt2 & 0x7ff;
                int i1 = Texture.anIntArray1470[k];
                int j1 = Texture.anIntArray1471[k];
                i1 = i1 * (minimapInt3 + 256) >> 8;
                j1 = j1 * (minimapInt3 + 256) >> 8;
                int k1 = j * i1 + i * j1 >> 11;
                int l1 = j * j1 - i * i1 >> 11;
                int i2 = myPlayer.x + k1 >> 7;
                int j2 = myPlayer.y - l1 >> 7;
                boolean flag1 = walkTo(1, 0, 0, 0, myPlayer.smallY[0], 0, 0, j2, myPlayer.smallX[0], true, i2);
                if (flag1) {
                    stream.writeByte(i);
                    stream.writeByte(j);
                    stream.writeShort(minimapInt1);
                    stream.writeByte(57);
                    stream.writeByte(minimapInt2);
                    stream.writeByte(minimapInt3);
                    stream.writeByte(89);
                    stream.writeShort(myPlayer.x);
                    stream.writeShort(myPlayer.y);
                    stream.writeByte(anInt1264);
                    stream.writeByte(63);
                }
            }
            anInt1117++;
            if (anInt1117 > 1151) {
                anInt1117 = 0;
                stream.writePacketHeaderEnc(246);
                stream.writeByte(0);
                int l = stream.currentOffset;
                if ((int) (Math.random() * 2D) == 0) {
                    stream.writeByte(101);
                }
                stream.writeByte(197);
                stream.writeShort((int) (Math.random() * 65536D));
                stream.writeByte((int) (Math.random() * 256D));
                stream.writeByte(67);
                stream.writeShort(14214);
                if ((int) (Math.random() * 2D) == 0) {
                    stream.writeShort(29487);
                }
                stream.writeShort((int) (Math.random() * 65536D));
                if ((int) (Math.random() * 2D) == 0) {
                    stream.writeByte(220);
                }
                stream.writeByte(180);
                stream.writeByteXXX(stream.currentOffset - l);
            }
        }
    }

    private String interfaceIntToString(int j) {
        if (j < 0x3b9ac9ff) {
            return String.valueOf(j);
        } else {
            return "*";
        }
    }

    private void drawErrorScreen() {
        Graphics g = getGameComponent().getGraphics();
        g.setColor(Color.black);
        g.fillRect(0, 0, 765, 503);
        setDelayTime(1);
        
        if (loadingError) {
            aBoolean831 = false;
            g.setFont(new Font("Helvetica", 1, 16));
            g.setColor(Color.yellow);
            int k = 35;
            g.drawString("Sorry, an error has occured whilst loading RuneScape", 30, k);
            k += 50;
            g.setColor(Color.white);
            g.drawString("To fix this try the following (in order):", 30, k);
            k += 50;
            g.setColor(Color.white);
            g.setFont(new Font("Helvetica", 1, 12));
            g.drawString("1: Try closing ALL open web-browser windows, and reloading", 30, k);
            k += 30;
            g.drawString("2: Try clearing your web-browsers cache from tools->internet options", 30, k);
            k += 30;
            g.drawString("3: Try using a different game-world", 30, k);
            k += 30;
            g.drawString("4: Try rebooting your computer", 30, k);
            k += 30;
            g.drawString("5: Try selecting a different version of Java from the play-game menu", 30, k);
        }
        
        if (genericLoadingError) {
            aBoolean831 = false;
            g.setFont(new Font("Helvetica", 1, 20));
            g.setColor(Color.white);
            g.drawString("Error - unable to load game!", 50, 50);
            g.drawString("To play RuneScape make sure you play from", 50, 100);
            g.drawString("http://www.runescape.com", 50, 150);
        }
        
        if (rsAlreadyLoaded) {
            aBoolean831 = false;
            g.setColor(Color.yellow);
            int l = 35;
            g.drawString("Error a copy of RuneScape already appears to be loaded", 30, l);
            l += 50;
            g.setColor(Color.white);
            g.drawString("To fix this try the following (in order):", 30, l);
            l += 50;
            g.setColor(Color.white);
            g.setFont(new Font("Helvetica", 1, 12));
            g.drawString("1: Try closing ALL open web-browser windows, and reloading", 30, l);
            l += 30;
            g.drawString("2: Try rebooting your computer, and reloading", 30, l);
            l += 30;
        }
    }

    public URL getCodeBase() {
        if (Signlink.mainapp != null) {
            return Signlink.mainapp.getCodeBase();
        }
        
        try {
            if (super.gameFrame != null) {
                return new URL("http://127.0.0.1:" + webPort);
            }
        } catch (MalformedURLException _ex) {
        }
        return super.getCodeBase();
    }

    private void method95() {
        for (int j = 0; j < npcCount; j++) {
            int k = npcIndices[j];
            NPC npc = npcArray[k];
            if (npc != null) {
                method96(npc);
            }
        }
    }

    private void method96(Entity entity) {
        if (entity.x < 128 || entity.y < 128 || entity.x >= 13184 || entity.y >= 13184) {
            entity.currentAnimationId = -1;
            entity.currentGraphicsId = -1;
            entity.asyncMovementSpeedStartToEnd = 0;
            entity.asyncMovementSpeedEndToStart = 0;
            entity.x = entity.smallX[0] * 128 + entity.anInt1540 * 64;
            entity.y = entity.smallY[0] * 128 + entity.anInt1540 * 64;
            entity.method446();
        }
        if (entity == myPlayer && (entity.x < 1536 || entity.y < 1536 || entity.x >= 11776 || entity.y >= 11776)) {
            entity.currentAnimationId = -1;
            entity.currentGraphicsId = -1;
            entity.asyncMovementSpeedStartToEnd = 0;
            entity.asyncMovementSpeedEndToStart = 0;
            entity.x = entity.smallX[0] * 128 + entity.anInt1540 * 64;
            entity.y = entity.smallY[0] * 128 + entity.anInt1540 * 64;
            entity.method446();
        }
        if (entity.asyncMovementSpeedStartToEnd > loopCycle) {
            method97(entity);
        } else if (entity.asyncMovementSpeedEndToStart >= loopCycle) {
            method98(entity);
        } else {
            method99(entity);
        }
        method100(entity);
        method101(entity);
    }

    private void method97(Entity entity) {
        int i = entity.asyncMovementSpeedStartToEnd - loopCycle;
        int j = entity.asyncMovementStartX * 128 + entity.anInt1540 * 64;
        int k = entity.asyncMovementStartY * 128 + entity.anInt1540 * 64;
        entity.x += (j - entity.x) / i;
        entity.y += (k - entity.y) / i;
        entity.anInt1503 = 0;
        if (entity.asyncMovementDirection == 0) {
            entity.turnDirection = 1024;
        }
        if (entity.asyncMovementDirection == 1) {
            entity.turnDirection = 1536;
        }
        if (entity.asyncMovementDirection == 2) {
            entity.turnDirection = 0;
        }
        if (entity.asyncMovementDirection == 3) {
            entity.turnDirection = 512;
        }
    }

    private void method98(Entity entity) {
        if (entity.asyncMovementSpeedEndToStart == loopCycle || entity.currentAnimationId == -1 || entity.anInt1529 != 0 || entity.anInt1528 + 1 > Animation.anims[entity.currentAnimationId].method258(entity.anInt1527)) {
            int i = entity.asyncMovementSpeedEndToStart - entity.asyncMovementSpeedStartToEnd;
            int j = loopCycle - entity.asyncMovementSpeedStartToEnd;
            int k = entity.asyncMovementStartX * 128 + entity.anInt1540 * 64;
            int l = entity.asyncMovementStartY * 128 + entity.anInt1540 * 64;
            int i1 = entity.asyncMovementEndX * 128 + entity.anInt1540 * 64;
            int j1 = entity.asyncMovementEndY * 128 + entity.anInt1540 * 64;
            entity.x = (k * (i - j) + i1 * j) / i;
            entity.y = (l * (i - j) + j1 * j) / i;
        }
        entity.anInt1503 = 0;
        if (entity.asyncMovementDirection == 0) {
            entity.turnDirection = 1024;
        }
        if (entity.asyncMovementDirection == 1) {
            entity.turnDirection = 1536;
        }
        if (entity.asyncMovementDirection == 2) {
            entity.turnDirection = 0;
        }
        if (entity.asyncMovementDirection == 3) {
            entity.turnDirection = 512;
        }
        entity.anInt1552 = entity.turnDirection;
    }

    private void method99(Entity entity) {
        entity.anInt1517 = entity.anInt1511;
        if (entity.smallXYIndex == 0) {
            entity.anInt1503 = 0;
            return;
        }
        if (entity.currentAnimationId != -1 && entity.anInt1529 == 0) {
            Animation animation = Animation.anims[entity.currentAnimationId];
            if (entity.anInt1542 > 0 && animation.anInt363 == 0) {
                entity.anInt1503++;
                return;
            }
            if (entity.anInt1542 <= 0 && animation.anInt364 == 0) {
                entity.anInt1503++;
                return;
            }
        }
        int i = entity.x;
        int j = entity.y;
        int k = entity.smallX[entity.smallXYIndex - 1] * 128 + entity.anInt1540 * 64;
        int l = entity.smallY[entity.smallXYIndex - 1] * 128 + entity.anInt1540 * 64;
        if (k - i > 256 || k - i < -256 || l - j > 256 || l - j < -256) {
            entity.x = k;
            entity.y = l;
            return;
        }
        if (i < k) {
            if (j < l) {
                entity.turnDirection = 1280;
            } else if (j > l) {
                entity.turnDirection = 1792;
            } else {
                entity.turnDirection = 1536;
            }
        } else if (i > k) {
            if (j < l) {
                entity.turnDirection = 768;
            } else if (j > l) {
                entity.turnDirection = 256;
            } else {
                entity.turnDirection = 512;
            }
        } else if (j < l) {
            entity.turnDirection = 1024;
        } else {
            entity.turnDirection = 0;
        }
        int i1 = entity.turnDirection - entity.anInt1552 & 0x7ff;
        if (i1 > 1024) {
            i1 -= 2048;
        }
        int j1 = entity.anInt1555;
        if (i1 >= -256 && i1 <= 256) {
            j1 = entity.anInt1554;
        } else if (i1 >= 256 && i1 < 768) {
            j1 = entity.anInt1557;
        } else if (i1 >= -768 && i1 <= -256) {
            j1 = entity.anInt1556;
        }
        if (j1 == -1) {
            j1 = entity.anInt1554;
        }
        entity.anInt1517 = j1;
        int k1 = 4;
        if (entity.anInt1552 != entity.turnDirection && entity.interactingEntityId == -1 && entity.anInt1504 != 0) {
            k1 = 2;
        }
        if (entity.smallXYIndex > 2) {
            k1 = 6;
        }
        if (entity.smallXYIndex > 3) {
            k1 = 8;
        }
        if (entity.anInt1503 > 0 && entity.smallXYIndex > 1) {
            k1 = 8;
            entity.anInt1503--;
        }
        if (entity.aBooleanArray1553[entity.smallXYIndex - 1]) {
            k1 <<= 1;
        }
        if (k1 >= 8 && entity.anInt1517 == entity.anInt1554 && entity.anInt1505 != -1) {
            entity.anInt1517 = entity.anInt1505;
        }
        if (i < k) {
            entity.x += k1;
            if (entity.x > k) {
                entity.x = k;
            }
        } else if (i > k) {
            entity.x -= k1;
            if (entity.x < k) {
                entity.x = k;
            }
        }
        if (j < l) {
            entity.y += k1;
            if (entity.y > l) {
                entity.y = l;
            }
        } else if (j > l) {
            entity.y -= k1;
            if (entity.y < l) {
                entity.y = l;
            }
        }
        if (entity.x == k && entity.y == l) {
            entity.smallXYIndex--;
            if (entity.anInt1542 > 0) {
                entity.anInt1542--;
            }
        }
    }

    private void method100(Entity entity) {
        if (entity.anInt1504 == 0) {
            return;
        }
        if (entity.interactingEntityId != -1 && entity.interactingEntityId < 32768) {
            NPC npc = npcArray[entity.interactingEntityId];
            if (npc != null) {
                int i1 = entity.x - npc.x;
                int k1 = entity.y - npc.y;
                if (i1 != 0 || k1 != 0) {
                    entity.turnDirection = (int) (Math.atan2(i1, k1) * 325.94900000000001D) & 0x7ff;
                }
            }
        }
        if (entity.interactingEntityId >= 32768) {
            int j = entity.interactingEntityId - 32768;
            if (j == playerListIndex) {
                j = myPlayerIndex;
            }
            Player player = playerArray[j];
            if (player != null) {
                int l1 = entity.x - player.x;
                int i2 = entity.y - player.y;
                if (l1 != 0 || i2 != 0) {
                    entity.turnDirection = (int) (Math.atan2(l1, i2) * 325.94900000000001D) & 0x7ff;
                }
            }
        }
        if ((entity.facingCoordinateX != 0 || entity.facingCoordinateY != 0) && (entity.smallXYIndex == 0 || entity.anInt1503 > 0)) {
            int k = entity.x - (entity.facingCoordinateX - baseX - baseX) * 64;
            int j1 = entity.y - (entity.facingCoordinateY - baseY - baseY) * 64;
            if (k != 0 || j1 != 0) {
                entity.turnDirection = (int) (Math.atan2(k, j1) * 325.94900000000001D) & 0x7ff;
            }
            entity.facingCoordinateX = 0;
            entity.facingCoordinateY = 0;
        }
        int l = entity.turnDirection - entity.anInt1552 & 0x7ff;
        if (l != 0) {
            if (l < entity.anInt1504 || l > 2048 - entity.anInt1504) {
                entity.anInt1552 = entity.turnDirection;
            } else if (l > 1024) {
                entity.anInt1552 -= entity.anInt1504;
            } else {
                entity.anInt1552 += entity.anInt1504;
            }
            entity.anInt1552 &= 0x7ff;
            if (entity.anInt1517 == entity.anInt1511 && entity.anInt1552 != entity.turnDirection) {
                if (entity.anInt1512 != -1) {
                    entity.anInt1517 = entity.anInt1512;
                    return;
                }
                entity.anInt1517 = entity.anInt1554;
            }
        }
    }

    private void method101(Entity entity) {
        entity.aBoolean1541 = false;
        if (entity.anInt1517 != -1) {
            Animation animation = Animation.anims[entity.anInt1517];
            entity.anInt1519++;
            if (entity.anInt1518 < animation.anInt352 && entity.anInt1519 > animation.method258(entity.anInt1518)) {
                entity.anInt1519 = 0;
                entity.anInt1518++;
            }
            if (entity.anInt1518 >= animation.anInt352) {
                entity.anInt1519 = 0;
                entity.anInt1518 = 0;
            }
        }
        if (entity.currentGraphicsId != -1 && loopCycle >= entity.currentGraphicsTargetLoop) {
            if (entity.anInt1521 < 0) {
                entity.anInt1521 = 0;
            }
            Animation animation_1 = StaticAnimation.cache[entity.currentGraphicsId].anim;
            for (entity.anInt1522++; entity.anInt1521 < animation_1.anInt352 && entity.anInt1522 > animation_1.method258(entity.anInt1521); entity.anInt1521++) {
                entity.anInt1522 -= animation_1.method258(entity.anInt1521);
            }

            if (entity.anInt1521 >= animation_1.anInt352 && (entity.anInt1521 < 0 || entity.anInt1521 >= animation_1.anInt352)) {
                entity.currentGraphicsId = -1;
            }
        }
        if (entity.currentAnimationId != -1 && entity.anInt1529 <= 1) {
            Animation animation_2 = Animation.anims[entity.currentAnimationId];
            if (animation_2.anInt363 == 1 && entity.anInt1542 > 0 && entity.asyncMovementSpeedStartToEnd <= loopCycle && entity.asyncMovementSpeedEndToStart < loopCycle) {
                entity.anInt1529 = 1;
                return;
            }
        }
        if (entity.currentAnimationId != -1 && entity.anInt1529 == 0) {
            Animation animation_3 = Animation.anims[entity.currentAnimationId];
            for (entity.anInt1528++; entity.anInt1527 < animation_3.anInt352 && entity.anInt1528 > animation_3.method258(entity.anInt1527); entity.anInt1527++) {
                entity.anInt1528 -= animation_3.method258(entity.anInt1527);
            }

            if (entity.anInt1527 >= animation_3.anInt352) {
                entity.anInt1527 -= animation_3.anInt356;
                entity.anInt1530++;
                if (entity.anInt1530 >= animation_3.anInt362) {
                    entity.currentAnimationId = -1;
                }
                if (entity.anInt1527 < 0 || entity.anInt1527 >= animation_3.anInt352) {
                    entity.currentAnimationId = -1;
                }
            }
            entity.aBoolean1541 = animation_3.aBoolean358;
        }
        if (entity.anInt1529 > 0) {
            entity.anInt1529--;
        }
    }

    private void drawGameScreen() {
        if (welcomeScreenRaised) {
            welcomeScreenRaised = false;
            backLeftIP1.drawGraphics(super.graphics, 0, 4);
            backLeftIP2.drawGraphics(super.graphics, 0, 357);
            backRightIP1.drawGraphics(super.graphics, 722, 4);
            backRightIP2.drawGraphics(super.graphics, 743, 205);
            backTopIP1.drawGraphics(super.graphics, 0, 0);
            backVmidIP1.drawGraphics(super.graphics, 516, 4);
            backVmidIP2.drawGraphics(super.graphics, 516, 205);
            backVmidIP3.drawGraphics(super.graphics, 496, 357);
            backVmidIP2_2.drawGraphics(super.graphics, 0, 338);
            needDrawTabArea = true;
            inputTaken = true;
            tabAreaAltered = true;
            aBoolean1233 = true;

            if (loadingStage != 2) {
                aRSImageProducer_1165.drawGraphics(super.graphics, 4, 4);
                aRSImageProducer_1164.drawGraphics(super.graphics, 550, 4);
            }
        }

        if (loadingStage == 2) {
            method146();
        }

        if (menuOpen && menuScreenArea == 1) {
            needDrawTabArea = true;
        }

        if (invOverlayInterfaceID != -1) {
            boolean flag1 = method119(anInt945, invOverlayInterfaceID);

            if (flag1) {
                needDrawTabArea = true;
            }
        }
        if (atInventoryInterfaceType == 2) {
            needDrawTabArea = true;
        }

        if (activeInterfaceType == 2) {
            needDrawTabArea = true;
        }

        if (needDrawTabArea) {
            drawTabArea();
            needDrawTabArea = false;
        }

        if (backDialogID == -1) {
            aClass9_1059.scrollPosition = anInt1211 - anInt1089 - 77;
            if (super.mouseX > 448 && super.mouseX < 560 && super.mouseY > 332) {
                method65(463, 77, super.mouseX - 17, super.mouseY - 357, aClass9_1059, 0, false, anInt1211);
            }
            int i = anInt1211 - 77 - aClass9_1059.scrollPosition;
            if (i < 0) {
                i = 0;
            }
            if (i > anInt1211 - 77) {
                i = anInt1211 - 77;
            }
            if (anInt1089 != i) {
                anInt1089 = i;
                inputTaken = true;
            }
        }

        if (backDialogID != -1) {
            boolean flag2 = method119(anInt945, backDialogID);
            if (flag2) {
                inputTaken = true;
            }
        }

        if (atInventoryInterfaceType == 3) {
            inputTaken = true;
        }

        if (activeInterfaceType == 3) {
            inputTaken = true;
        }

        if (aString844 != null) {
            inputTaken = true;
        }

        if (menuOpen && menuScreenArea == 2) {
            inputTaken = true;
        }

        if (inputTaken) {
            drawChatArea();
            inputTaken = false;
        }

        if (loadingStage == 2) {
            drawMinimap();
            aRSImageProducer_1164.drawGraphics(super.graphics, 550, 4);
        }

        if (flashSidebarId != -1) {
            tabAreaAltered = true;
        }

        if (tabAreaAltered) {
            if (flashSidebarId != -1 && flashSidebarId == tabID) {
                flashSidebarId = -1;
                stream.writePacketHeaderEnc(120);
                stream.writeByte(tabID);
            }
            tabAreaAltered = false;
            aRSImageProducer_1125.initDrawingArea();
            backHmid1.draw(0, 0);

            if (invOverlayInterfaceID == -1) {
                if (tabInterfaceIDs[tabID] != -1) {
                    if (tabID == 0) {
                        redStone1.draw(22, 10);
                    }
                    if (tabID == 1) {
                        redStone2.draw(54, 8);
                    }
                    if (tabID == 2) {
                        redStone2.draw(82, 8);
                    }
                    if (tabID == 3) {
                        redStone3.draw(110, 8);
                    }
                    if (tabID == 4) {
                        redStone2_2.draw(153, 8);
                    }
                    if (tabID == 5) {
                        redStone2_2.draw(181, 8);
                    }
                    if (tabID == 6) {
                        redStone1_2.draw(209, 9);
                    }
                }
                if (tabInterfaceIDs[0] != -1 && (flashSidebarId != 0 || loopCycle % 20 < 10)) {
                    sideIcons[0].draw(29, 13);
                }
                if (tabInterfaceIDs[1] != -1 && (flashSidebarId != 1 || loopCycle % 20 < 10)) {
                    sideIcons[1].draw(53, 11);
                }
                if (tabInterfaceIDs[2] != -1 && (flashSidebarId != 2 || loopCycle % 20 < 10)) {
                    sideIcons[2].draw(82, 11);
                }
                if (tabInterfaceIDs[3] != -1 && (flashSidebarId != 3 || loopCycle % 20 < 10)) {
                    sideIcons[3].draw(115, 12);
                }
                if (tabInterfaceIDs[4] != -1 && (flashSidebarId != 4 || loopCycle % 20 < 10)) {
                    sideIcons[4].draw(153, 13);
                }
                if (tabInterfaceIDs[5] != -1 && (flashSidebarId != 5 || loopCycle % 20 < 10)) {
                    sideIcons[5].draw(180, 11);
                }
                if (tabInterfaceIDs[6] != -1 && (flashSidebarId != 6 || loopCycle % 20 < 10)) {
                    sideIcons[6].draw(208, 13);
                }
            }
            aRSImageProducer_1125.drawGraphics(super.graphics, 516, 160);
            aRSImageProducer_1124.initDrawingArea();
            backBase2.draw(0, 0);

            if (invOverlayInterfaceID == -1) {
                if (tabInterfaceIDs[tabID] != -1) {
                    if (tabID == 7) {
                        redStone1_3.draw(42, 0);
                    }
                    if (tabID == 8) {
                        redStone2_3.draw(74, 0);
                    }
                    if (tabID == 9) {
                        redStone2_3.draw(102, 0);
                    }
                    if (tabID == 10) {
                        redStone3_2.draw(130, 1);
                    }
                    if (tabID == 11) {
                        redStone2_4.draw(173, 0);
                    }
                    if (tabID == 12) {
                        redStone2_4.draw(201, 0);
                    }
                    if (tabID == 13) {
                        redStone1_4.draw(229, 0);
                    }
                }
                if (tabInterfaceIDs[8] != -1 && (flashSidebarId != 8 || loopCycle % 20 < 10)) {
                    sideIcons[7].draw(74, 2);
                }
                if (tabInterfaceIDs[9] != -1 && (flashSidebarId != 9 || loopCycle % 20 < 10)) {
                    sideIcons[8].draw(102, 3);
                }
                if (tabInterfaceIDs[10] != -1 && (flashSidebarId != 10 || loopCycle % 20 < 10)) {
                    sideIcons[9].draw(137, 4);
                }
                if (tabInterfaceIDs[11] != -1 && (flashSidebarId != 11 || loopCycle % 20 < 10)) {
                    sideIcons[10].draw(174, 2);
                }
                if (tabInterfaceIDs[12] != -1 && (flashSidebarId != 12 || loopCycle % 20 < 10)) {
                    sideIcons[11].draw(201, 2);
                }
                if (tabInterfaceIDs[13] != -1 && (flashSidebarId != 13 || loopCycle % 20 < 10)) {
                    sideIcons[12].draw(226, 2);
                }
            }
            aRSImageProducer_1124.drawGraphics(super.graphics, 496, 466);
            aRSImageProducer_1165.initDrawingArea();
        }

        if (aBoolean1233) {
            aBoolean1233 = false;
            aRSImageProducer_1123.initDrawingArea();
            backBase1.draw(0, 0);
            loadingTextArea.drawText(0xffffff, 55, "Public chat", 28, true);
            if (publicChatMode == 0) {
                loadingTextArea.drawText(65280, 55, "On", 41, true);
            }
            if (publicChatMode == 1) {
                loadingTextArea.drawText(0xffff00, 55, "Friends", 41, true);
            }
            if (publicChatMode == 2) {
                loadingTextArea.drawText(0xff0000, 55, "Off", 41, true);
            }
            if (publicChatMode == 3) {
                loadingTextArea.drawText(65535, 55, "Hide", 41, true);
            }
            loadingTextArea.drawText(0xffffff, 184, "Private chat", 28, true);
            if (privateChatMode == 0) {
                loadingTextArea.drawText(65280, 184, "On", 41, true);
            }
            if (privateChatMode == 1) {
                loadingTextArea.drawText(0xffff00, 184, "Friends", 41, true);
            }
            if (privateChatMode == 2) {
                loadingTextArea.drawText(0xff0000, 184, "Off", 41, true);
            }
            loadingTextArea.drawText(0xffffff, 324, "Trade/compete", 28, true);
            if (tradeMode == 0) {
                loadingTextArea.drawText(65280, 324, "On", 41, true);
            }
            if (tradeMode == 1) {
                loadingTextArea.drawText(0xffff00, 324, "Friends", 41, true);
            }
            if (tradeMode == 2) {
                loadingTextArea.drawText(0xff0000, 324, "Off", 41, true);
            }
            loadingTextArea.drawText(0xffffff, 458, "Report abuse", 33, true);
            aRSImageProducer_1123.drawGraphics(super.graphics, 0, 453);
            aRSImageProducer_1165.initDrawingArea();
        }
        anInt945 = 0;
    }

    private boolean buildFriendsListMenu(RSInterface class9) {
        int i = class9.anInt214;
        if (i >= 1 && i <= 200 || i >= 701 && i <= 900) {
            if (i >= 801) {
                i -= 701;
            } else if (i >= 701) {
                i -= 601;
            } else if (i >= 101) {
                i -= 101;
            } else {
                i--;
            }
            menuActionName[menuActionRow] = "Remove @whi@" + friendsList[i];
            menuActionID[menuActionRow] = 792;
            menuActionRow++;
            menuActionName[menuActionRow] = "Message @whi@" + friendsList[i];
            menuActionID[menuActionRow] = 639;
            menuActionRow++;
            return true;
        }
        if (i >= 401 && i <= 500) {
            menuActionName[menuActionRow] = "Remove @whi@" + class9.message;
            menuActionID[menuActionRow] = 322;
            menuActionRow++;
            return true;
        } else {
            return false;
        }
    }

    private void method104() {
        Animable_Sub3 class30_sub2_sub4_sub3 = (Animable_Sub3) aClass19_1056.reverseGetFirst();
        for (; class30_sub2_sub4_sub3 != null; class30_sub2_sub4_sub3 = (Animable_Sub3) aClass19_1056.reverseGetNext()) {
            if (class30_sub2_sub4_sub3.anInt1560 != plane || class30_sub2_sub4_sub3.aBoolean1567) {
                class30_sub2_sub4_sub3.unlink();
            } else if (loopCycle >= class30_sub2_sub4_sub3.anInt1564) {
                class30_sub2_sub4_sub3.method454(anInt945);
                if (class30_sub2_sub4_sub3.aBoolean1567) {
                    class30_sub2_sub4_sub3.unlink();
                } else {
                    worldController.method285(class30_sub2_sub4_sub3.anInt1560, 0, class30_sub2_sub4_sub3.anInt1563, -1, class30_sub2_sub4_sub3.anInt1562, 60, class30_sub2_sub4_sub3.anInt1561, class30_sub2_sub4_sub3, false);
                }
            }
        }
    }

    private void drawInterface(int j, int k, RSInterface class9, int l) {
        if (class9.type != 0 || class9.children == null) {
            return;
        }
        if (class9.aBoolean266 && anInt1026 != class9.id && anInt1048 != class9.id && anInt1039 != class9.id) {
            return;
        }
        int i1 = DrawingArea.topX;
        int j1 = DrawingArea.topY;
        int k1 = DrawingArea.bottomX;
        int l1 = DrawingArea.bottomY;
        DrawingArea.setDrawingArea(l + class9.height, k, k + class9.width, l);
        int i2 = class9.children.length;
        for (int j2 = 0; j2 < i2; j2++) {
            int k2 = class9.childX[j2] + k;
            int l2 = (class9.childY[j2] + l) - j;
            RSInterface class9_1 = RSInterface.interfaceCache[class9.children[j2]];
            k2 += class9_1.offsetX;
            l2 += class9_1.offsetY;
            if (class9_1.anInt214 > 0) {
                drawFriendsListOrWelcomeScreen(class9_1);
            }
            if (class9_1.type == 0) {
                if (class9_1.scrollPosition > class9_1.maximumScrollPosition - class9_1.height) {
                    class9_1.scrollPosition = class9_1.maximumScrollPosition - class9_1.height;
                }
                if (class9_1.scrollPosition < 0) {
                    class9_1.scrollPosition = 0;
                }
                drawInterface(class9_1.scrollPosition, k2, class9_1, l2);
                if (class9_1.maximumScrollPosition > class9_1.height) {
                    method30(class9_1.height, class9_1.scrollPosition, l2, k2 + class9_1.width, class9_1.maximumScrollPosition);
                }
            } else if (class9_1.type != 1) {
                if (class9_1.type == 2) {
                    int i3 = 0;
                    for (int l3 = 0; l3 < class9_1.height; l3++) {
                        for (int l4 = 0; l4 < class9_1.width; l4++) {
                            int k5 = k2 + l4 * (32 + class9_1.invSpritePadX);
                            int j6 = l2 + l3 * (32 + class9_1.invSpritePadY);
                            if (i3 < 20) {
                                k5 += class9_1.spritesX[i3];
                                j6 += class9_1.spritesY[i3];
                            }
                            if (class9_1.inv[i3] > 0) {
                                int k6 = 0;
                                int j7 = 0;
                                int j9 = class9_1.inv[i3] - 1;
                                if (k5 > DrawingArea.topX - 32 && k5 < DrawingArea.bottomX && j6 > DrawingArea.topY - 32 && j6 < DrawingArea.bottomY || activeInterfaceType != 0 && anInt1085 == i3) {
                                    int l9 = 0;
                                    if (itemSelected == 1 && anInt1283 == i3 && anInt1284 == class9_1.id) {
                                        l9 = 0xffffff;
                                    }
                                    Sprite class30_sub2_sub1_sub1_2 = ItemDef.getSprite(j9, class9_1.invStackSizes[i3], l9);
                                    if (class30_sub2_sub1_sub1_2 != null) {
                                        if (activeInterfaceType != 0 && anInt1085 == i3 && anInt1084 == class9_1.id) {
                                            k6 = super.mouseX - anInt1087;
                                            j7 = super.mouseY - anInt1088;
                                            if (k6 < 5 && k6 > -5) {
                                                k6 = 0;
                                            }
                                            if (j7 < 5 && j7 > -5) {
                                                j7 = 0;
                                            }
                                            if (anInt989 < 5) {
                                                k6 = 0;
                                                j7 = 0;
                                            }
                                            class30_sub2_sub1_sub1_2.drawSprite1(k5 + k6, j6 + j7);
                                            if (j6 + j7 < DrawingArea.topY && class9.scrollPosition > 0) {
                                                int i10 = (anInt945 * (DrawingArea.topY - j6 - j7)) / 3;
                                                if (i10 > anInt945 * 10) {
                                                    i10 = anInt945 * 10;
                                                }
                                                if (i10 > class9.scrollPosition) {
                                                    i10 = class9.scrollPosition;
                                                }
                                                class9.scrollPosition -= i10;
                                                anInt1088 += i10;
                                            }
                                            if (j6 + j7 + 32 > DrawingArea.bottomY && class9.scrollPosition < class9.maximumScrollPosition - class9.height) {
                                                int j10 = (anInt945 * ((j6 + j7 + 32) - DrawingArea.bottomY)) / 3;
                                                if (j10 > anInt945 * 10) {
                                                    j10 = anInt945 * 10;
                                                }
                                                if (j10 > class9.maximumScrollPosition - class9.height - class9.scrollPosition) {
                                                    j10 = class9.maximumScrollPosition - class9.height - class9.scrollPosition;
                                                }
                                                class9.scrollPosition += j10;
                                                anInt1088 -= j10;
                                            }
                                        } else if (atInventoryInterfaceType != 0 && atInventoryIndex == i3 && atInventoryInterface == class9_1.id) {
                                            class30_sub2_sub1_sub1_2.drawSprite1(k5, j6);
                                        } else {
                                            class30_sub2_sub1_sub1_2.drawSprite(k5, j6);
                                        }
                                        if (class30_sub2_sub1_sub1_2.anInt1444 == 33 || class9_1.invStackSizes[i3] != 1) {
                                            int k10 = class9_1.invStackSizes[i3];
                                            aTextDrawingArea_1270.method385(0, getValuePostfix(k10), j6 + 10 + j7, k5 + 1 + k6);
                                            aTextDrawingArea_1270.method385(0xffff00, getValuePostfix(k10), j6 + 9 + j7, k5 + k6);
                                        }
                                    }
                                }
                            } else if (class9_1.sprites != null && i3 < 20) {
                                Sprite class30_sub2_sub1_sub1_1 = class9_1.sprites[i3];
                                if (class30_sub2_sub1_sub1_1 != null) {
                                    class30_sub2_sub1_sub1_1.drawSprite(k5, j6);
                                }
                            }
                            i3++;
                        }

                    }

                } else if (class9_1.type == 3) {
                    boolean flag = false;
                    if (anInt1039 == class9_1.id || anInt1048 == class9_1.id || anInt1026 == class9_1.id) {
                        flag = true;
                    }
                    int j3;
                    if (interfaceIsSelected(class9_1)) {
                        j3 = class9_1.anInt219;
                        if (flag && class9_1.anInt239 != 0) {
                            j3 = class9_1.anInt239;
                        }
                    } else {
                        j3 = class9_1.textColor;
                        if (flag && class9_1.anInt216 != 0) {
                            j3 = class9_1.anInt216;
                        }
                    }
                    if (class9_1.aByte254 == 0) {
                        if (class9_1.aBoolean227) {
                            DrawingArea.fillPixelsReverseOrder(class9_1.height, l2, k2, j3, class9_1.width);
                        } else {
                            DrawingArea.fillPixels(k2, class9_1.width, class9_1.height, j3, l2);
                        }
                    } else if (class9_1.aBoolean227) {
                        DrawingArea.method335(j3, l2, class9_1.width, class9_1.height, 256 - (class9_1.aByte254 & 0xff), k2);
                    } else {
                        DrawingArea.method338(l2, class9_1.height, 256 - (class9_1.aByte254 & 0xff), j3, class9_1.width, k2);
                    }
                } else if (class9_1.type == 4) {
                    TextDrawingArea textDrawingArea = class9_1.textDrawingAreas;
                    String s = class9_1.message;
                    boolean flag1 = false;
                    if (anInt1039 == class9_1.id || anInt1048 == class9_1.id || anInt1026 == class9_1.id) {
                        flag1 = true;
                    }
                    int i4;
                    if (interfaceIsSelected(class9_1)) {
                        i4 = class9_1.anInt219;
                        if (flag1 && class9_1.anInt239 != 0) {
                            i4 = class9_1.anInt239;
                        }
                        if (class9_1.aString228.length() > 0) {
                            s = class9_1.aString228;
                        }
                    } else {
                        i4 = class9_1.textColor;
                        if (flag1 && class9_1.anInt216 != 0) {
                            i4 = class9_1.anInt216;
                        }
                    }
                    if (class9_1.atActionType == 6 && aBoolean1149) {
                        s = "Please wait...";
                        i4 = class9_1.textColor;
                    }
                    if (DrawingArea.width == 479) {
                        if (i4 == 0xffff00) {
                            i4 = 255;
                        }
                        if (i4 == 49152) {
                            i4 = 0xffffff;
                        }
                    }
                    for (int l6 = l2 + textDrawingArea.anInt1497; s.length() > 0; l6 += textDrawingArea.anInt1497) {
                        if (s.indexOf("%") != -1) {
                            do {
                                int k7 = s.indexOf("%1");
                                if (k7 == -1) {
                                    break;
                                }
                                s = s.substring(0, k7) + interfaceIntToString(extractInterfaceValues(class9_1, 0)) + s.substring(k7 + 2);
                            } while (true);
                            do {
                                int l7 = s.indexOf("%2");
                                if (l7 == -1) {
                                    break;
                                }
                                s = s.substring(0, l7) + interfaceIntToString(extractInterfaceValues(class9_1, 1)) + s.substring(l7 + 2);
                            } while (true);
                            do {
                                int i8 = s.indexOf("%3");
                                if (i8 == -1) {
                                    break;
                                }
                                s = s.substring(0, i8) + interfaceIntToString(extractInterfaceValues(class9_1, 2)) + s.substring(i8 + 2);
                            } while (true);
                            do {
                                int j8 = s.indexOf("%4");
                                if (j8 == -1) {
                                    break;
                                }
                                s = s.substring(0, j8) + interfaceIntToString(extractInterfaceValues(class9_1, 3)) + s.substring(j8 + 2);
                            } while (true);
                            do {
                                int k8 = s.indexOf("%5");
                                if (k8 == -1) {
                                    break;
                                }
                                s = s.substring(0, k8) + interfaceIntToString(extractInterfaceValues(class9_1, 4)) + s.substring(k8 + 2);
                            } while (true);
                        }
                        int l8 = s.indexOf("\\n");
                        String s1;
                        if (l8 != -1) {
                            s1 = s.substring(0, l8);
                            s = s.substring(l8 + 2);
                        } else {
                            s1 = s;
                            s = "";
                        }
                        if (class9_1.aBoolean223) {
                            textDrawingArea.drawText(i4, k2 + class9_1.width / 2, s1, l6, class9_1.aBoolean268);
                        } else {
                            textDrawingArea.drawText(class9_1.aBoolean268, k2, i4, s1, l6);
                        }
                    }

                } else if (class9_1.type == 5) {
                    Sprite sprite;
                    if (interfaceIsSelected(class9_1)) {
                        sprite = class9_1.sprite2;
                    } else {
                        sprite = class9_1.sprite1;
                    }
                    if (sprite != null) {
                        sprite.drawSprite(k2, l2);
                    }
                } else if (class9_1.type == 6) {
                    int k3 = Texture.textureInt1;
                    int j4 = Texture.textureInt2;
                    Texture.textureInt1 = k2 + class9_1.width / 2;
                    Texture.textureInt2 = l2 + class9_1.height / 2;
                    int i5 = Texture.anIntArray1470[class9_1.modelRotation1] * class9_1.zoomFactor >> 16;
                    int l5 = Texture.anIntArray1471[class9_1.modelRotation1] * class9_1.zoomFactor >> 16;
                    boolean flag2 = interfaceIsSelected(class9_1);
                    int i7;
                    if (flag2) {
                        i7 = class9_1.anInt258;
                    } else {
                        i7 = class9_1.animationId;
                    }
                    Model model;
                    if (i7 == -1) {
                        model = class9_1.method209(-1, -1, flag2);
                    } else {
                        Animation animation = Animation.anims[i7];
                        model = class9_1.method209(animation.anIntArray354[class9_1.anInt246], animation.anIntArray353[class9_1.anInt246], flag2);
                    }
                    if (model != null) {
                        model.method482(class9_1.anInt271, 0, class9_1.modelRotation1, 0, i5, l5);
                    }
                    Texture.textureInt1 = k3;
                    Texture.textureInt2 = j4;
                } else if (class9_1.type == 7) {
                    TextDrawingArea textDrawingArea_1 = class9_1.textDrawingAreas;
                    int k4 = 0;
                    for (int j5 = 0; j5 < class9_1.height; j5++) {
                        for (int i6 = 0; i6 < class9_1.width; i6++) {
                            if (class9_1.inv[k4] > 0) {
                                ItemDef itemDef = ItemDef.forID(class9_1.inv[k4] - 1);
                                String s2 = itemDef.name;
                                if (itemDef.stackable || class9_1.invStackSizes[k4] != 1) {
                                    s2 = s2 + " x" + getValuePostfixColoured(class9_1.invStackSizes[k4]);
                                }
                                int i9 = k2 + i6 * (115 + class9_1.invSpritePadX);
                                int k9 = l2 + j5 * (12 + class9_1.invSpritePadY);
                                if (class9_1.aBoolean223) {
                                    textDrawingArea_1.drawText(class9_1.textColor, i9 + class9_1.width / 2, s2, k9, class9_1.aBoolean268);
                                } else {
                                    textDrawingArea_1.drawText(class9_1.aBoolean268, i9, class9_1.textColor, s2, k9);
                                }
                            }
                            k4++;
                        }

                    }

                }
            }
        }

        DrawingArea.setDrawingArea(l1, i1, k1, j1);
    }

    private void randomizeBackground(Background background) {
        int j = 256;
        for (int k = 0; k < anIntArray1190.length; k++) {
            anIntArray1190[k] = 0;
        }

        for (int l = 0; l < 5000; l++) {
            int i1 = (int) (Math.random() * 128D * (double) j);
            anIntArray1190[i1] = (int) (Math.random() * 256D);
        }

        for (int j1 = 0; j1 < 20; j1++) {
            for (int k1 = 1; k1 < j - 1; k1++) {
                for (int i2 = 1; i2 < 127; i2++) {
                    int k2 = i2 + (k1 << 7);
                    anIntArray1191[k2] = (anIntArray1190[k2 - 1] + anIntArray1190[k2 + 1] + anIntArray1190[k2 - 128] + anIntArray1190[k2 + 128]) / 4;
                }

            }

            int ai[] = anIntArray1190;
            anIntArray1190 = anIntArray1191;
            anIntArray1191 = ai;
        }

        if (background != null) {
            int l1 = 0;
            for (int j2 = 0; j2 < background.bgHeight; j2++) {
                for (int l2 = 0; l2 < background.bgWidth; l2++) {
                    if (background.bgPixels[l1++] != 0) {
                        int i3 = l2 + 16 + background.offsetX;
                        int j3 = j2 + 16 + background.offsetY;
                        int k3 = i3 + (j3 << 7);
                        anIntArray1190[k3] = 0;
                    }
                }

            }

        }
    }

    /**
     * Handles decoding and executing the player flag-based updating.
     * Player chat ignoring logic is done here too.
     */
    private void updatePlayerByFlag(int mask, int playerSlot, Stream stream, Player player) {
        // Async walking update
        if ((mask & 0x400) != 0) {
            player.asyncMovementStartX = stream.readUByteS();
            player.asyncMovementStartY = stream.readUByteS();
            player.asyncMovementEndX = stream.readUByteS();
            player.asyncMovementEndY = stream.readUByteS();
            player.asyncMovementSpeedStartToEnd = stream.readUShortLEA() + loopCycle;
            player.asyncMovementSpeedEndToStart = stream.readUShortA() + loopCycle;
            player.asyncMovementDirection = stream.readUByteS();
            player.method446();
        }

        // Player graphics updating
        if ((mask & 0x100) != 0) {
            player.currentGraphicsId = stream.readUShortLE();
            int graphicsDelay = stream.readUInt();
            player.currentGraphicsDelaySegment = graphicsDelay >> 16;
            player.currentGraphicsTargetLoop = loopCycle + (graphicsDelay & 0xffff);
            player.anInt1521 = 0;
            player.anInt1522 = 0;

            if (player.currentGraphicsTargetLoop > loopCycle) {
                player.anInt1521 = -1;
            }

            // Checking upper bound of the graphic id
            if (player.currentGraphicsId == 65535) {
                player.currentGraphicsId = -1;
            }
        }

        // Player animation updating
        if ((mask & 8) != 0) {
            int animationId = stream.readUShortLE();

            // Checking upper bound of the animation
            if (animationId == 65535) {
                animationId = -1;
            }
            int animationDelay = stream.readUByteC();

            if (animationId == player.currentAnimationId && animationId != -1) {
                int flag = Animation.anims[animationId].anInt365;

                if (flag == 1) {
                    player.anInt1527 = 0;
                    player.anInt1528 = 0;
                    player.anInt1529 = animationDelay;
                    player.anInt1530 = 0;
                }

                if (flag == 2) {
                    player.anInt1530 = 0;
                }
            } else if (animationId == -1 || player.currentAnimationId == -1
                    || Animation.anims[animationId].anInt359 >= Animation.anims[player.currentAnimationId].anInt359) {
                player.currentAnimationId = animationId;
                player.anInt1527 = 0;
                player.anInt1528 = 0;
                player.anInt1529 = animationDelay;
                player.anInt1530 = 0;
                player.anInt1542 = player.smallXYIndex;
            }
        }

        // Player force text
        if ((mask & 4) != 0) {
            player.textSpoken = stream.readString();

            if (player.textSpoken.charAt(0) == '~') {
                player.textSpoken = player.textSpoken.substring(1);
                pushMessage(player.textSpoken, 2, player.name);
            } else if (player == myPlayer) {
                pushMessage(player.textSpoken, 2, player.name);
            }
            player.chatAttributes = 0;
            player.anInt1531 = 0;
            player.textCycle = 150;
        }

        // Player chat
        if ((mask & 0x80) != 0) {
            int chatEffects = stream.readUShortLE(); // text colour/effects
            int playerPrivileges = stream.readUByte();
            int chatTextLength = stream.readUByteC();
            int k3 = stream.currentOffset;
            
            if (player.name != null && player.visible) {
                long playerName = StringHelper.longForName(player.name);
                boolean ignored = false;
                
                if (playerPrivileges <= 1) {
                    for (int idx = 0; idx < ignoreCount; idx++) {
                        if (ignoreListAsLongs[idx] != playerName) {
                            continue;
                        }
                        ignored = true;
                        break;
                    }
                }

                if (!ignored && anInt1251 == 0) {
                    try {
                        aStream_834.currentOffset = 0;
                        stream.writeBytes(chatTextLength, 0, aStream_834.buffer);
                        aStream_834.currentOffset = 0;
                        String message = TextInput.constructInput(chatTextLength, aStream_834);
                        message = Censor.apply(message);
                        player.textSpoken = message;
                        player.chatAttributes = chatEffects >> 8;
                        player.privilege = playerPrivileges;

                        //entityMessage(player);
                        player.anInt1531 = chatEffects & 0xff;
                        player.textCycle = 150;

                        // Player rights handling
                        if (playerPrivileges == 2 || playerPrivileges == 3) {
                            pushMessage(message, 1, "@cr2@" + player.name);
                        } else if (playerPrivileges == 1) {
                            pushMessage(message, 1, "@cr1@" + player.name);
                        } else {
                            pushMessage(message, 2, player.name);
                        }
                    } catch (Exception exception) {
                        Signlink.printError("cde2");
                    }
                }
            }
            stream.currentOffset = k3 + chatTextLength;
        }

        // Player interacting with entity
        if ((mask & 1) != 0) {
            player.interactingEntityId = stream.readUShortLE();

            // Upper bounds check
            if (player.interactingEntityId == 65535) {
                player.interactingEntityId = -1;
            }
        }

        // Cached player appearance update
        if ((mask & 0x10) != 0) {
            int size = stream.readUByteC();
            byte cache[] = new byte[size];
            Stream playerBuffer = new Stream(cache);
            stream.readBytes(size, 0, cache);
            cachedPlayerUpdateBuffers[playerSlot] = playerBuffer;
            player.updatePlayer(playerBuffer);
        }

        // Facing coordinates
        if ((mask & 2) != 0) {
            player.facingCoordinateX = stream.readUShortLEA();
            player.facingCoordinateY = stream.readUShortLE();
        }

        // Primary hit
        if ((mask & 0x20) != 0) {
            int damage = stream.readUByte();
            int type = stream.readUByteA();
            player.updateHitData(type, damage, loopCycle);
            player.loopCycleStatus = loopCycle + 300;
            player.currentHealth = stream.readUByteC();
            player.maxHealth = stream.readUByte();
        }

        // Secondary hit
        if ((mask & 0x200) != 0) {
            int damage = stream.readUByte();
            int type = stream.readUByteS();
            player.updateHitData(type, damage, loopCycle);
            player.loopCycleStatus = loopCycle + 300;
            player.currentHealth = stream.readUByte();
            player.maxHealth = stream.readUByteC();
        }
    }

    private void method108() {
        try {
            int j = myPlayer.x + anInt1278;
            int k = myPlayer.y + anInt1131;
            if (anInt1014 - j < -500 || anInt1014 - j > 500 || anInt1015 - k < -500 || anInt1015 - k > 500) {
                anInt1014 = j;
                anInt1015 = k;
            }
            if (anInt1014 != j) {
                anInt1014 += (j - anInt1014) / 16;
            }
            if (anInt1015 != k) {
                anInt1015 += (k - anInt1015) / 16;
            }
            if (super.keyArray[1] == 1) {
                anInt1186 += (-24 - anInt1186) / 2;
            } else if (super.keyArray[2] == 1) {
                anInt1186 += (24 - anInt1186) / 2;
            } else {
                anInt1186 /= 2;
            }
            if (super.keyArray[3] == 1) {
                anInt1187 += (12 - anInt1187) / 2;
            } else if (super.keyArray[4] == 1) {
                anInt1187 += (-12 - anInt1187) / 2;
            } else {
                anInt1187 /= 2;
            }
            minimapInt1 = minimapInt1 + anInt1186 / 2 & 0x7ff;
            anInt1184 += anInt1187 / 2;
            if (anInt1184 < 128) {
                anInt1184 = 128;
            }
            if (anInt1184 > 383) {
                anInt1184 = 383;
            }
            int l = anInt1014 >> 7;
            int i1 = anInt1015 >> 7;
            int j1 = method42(plane, anInt1015, anInt1014);
            int k1 = 0;
            if (l > 3 && i1 > 3 && l < 100 && i1 < 100) {
                for (int l1 = l - 4; l1 <= l + 4; l1++) {
                    for (int k2 = i1 - 4; k2 <= i1 + 4; k2++) {
                        int l2 = plane;
                        if (l2 < 3 && (byteGroundArray[1][l1][k2] & 2) == 2) {
                            l2++;
                        }
                        int i3 = j1 - intGroundArray[l2][l1][k2];
                        if (i3 > k1) {
                            k1 = i3;
                        }
                    }

                }

            }
            anInt1005++;
            if (anInt1005 > 1512) {
                anInt1005 = 0;
                stream.writePacketHeaderEnc(77);
                stream.writeByte(0);
                int i2 = stream.currentOffset;
                stream.writeByte((int) (Math.random() * 256D));
                stream.writeByte(101);
                stream.writeByte(233);
                stream.writeShort(45092);
                if ((int) (Math.random() * 2D) == 0) {
                    stream.writeShort(35784);
                }
                stream.writeByte((int) (Math.random() * 256D));
                stream.writeByte(64);
                stream.writeByte(38);
                stream.writeShort((int) (Math.random() * 65536D));
                stream.writeShort((int) (Math.random() * 65536D));
                stream.writeByteXXX(stream.currentOffset - i2);
            }
            int j2 = k1 * 192;
            if (j2 > 0x17f00) {
                j2 = 0x17f00;
            }
            if (j2 < 32768) {
                j2 = 32768;
            }
            if (j2 > anInt984) {
                anInt984 += (j2 - anInt984) / 24;
                return;
            }
            if (j2 < anInt984) {
                anInt984 += (j2 - anInt984) / 80;
            }
        } catch (Exception _ex) {
            Signlink.printError("glfc_ex " + myPlayer.x + "," + myPlayer.y + "," + anInt1014 + "," + anInt1015 + "," + anInt1069 + "," + anInt1070 + "," + baseX + "," + baseY);
            throw new RuntimeException("eek");
        }
    }

    public void draw() {
        if (rsAlreadyLoaded || loadingError || genericLoadingError) {
            drawErrorScreen();
            return;
        }
        drawCycle++;

        if (!loggedIn) {
            drawLoginScreen(false);
        } else {
            drawGameScreen();
        }
        anInt1213 = 0;
    }

    private boolean isFriendOrSelf(String s) {
        if (s == null) {
            return false;
        }

        for (int i = 0; i < friendsCount; i++) {
            if (s.equalsIgnoreCase(friendsList[i])) {
                return true;
            }
        }
        return s.equalsIgnoreCase(myPlayer.name);
    }

    private void setWaveVolume(int i) {
        Signlink.wavevol = i;
    }

    private void draw3dScreen() {
        drawSplitPrivateChat();

        if (crossType == 1) {
            crosses[crossIndex / 100].drawSprite(crossX - 8 - 4, crossY - 8 - 4);
            anInt1142++;

            if (anInt1142 > 67) {
                anInt1142 = 0;
                stream.writePacketHeaderEnc(78);
            }
        }

        if (crossType == 2) {
            crosses[4 + crossIndex / 100].drawSprite(crossX - 8 - 4, crossY - 8 - 4);
        }

        if (currentInterfaceId != -1) {
            method119(anInt945, currentInterfaceId);
            drawInterface(0, 0, RSInterface.interfaceCache[currentInterfaceId], 0);
        }

        if (openInterfaceID != -1) {
            method119(anInt945, openInterfaceID);
            drawInterface(0, 0, RSInterface.interfaceCache[openInterfaceID], 0);
        }
        method70();

        if (!menuOpen) {
            processRightClick();
            drawTooltip();
        } else if (menuScreenArea == 0) {
            drawMenu();
        }

        if (multiCombatFlag == 1) {
            headIcons[1].drawSprite(472, 296);
        }

        if (displayStats) {
            char c = '\u01FB';
            int k = 20;
            int i1 = 0xffff00;
            if (super.fps < 15) {
                i1 = 0xff0000;
            }
            loadingTextArea.method380("Fps:" + super.fps, c, i1, k);
            k += 15;
            Runtime runtime = Runtime.getRuntime();
            int memoryUsage = (int) ((runtime.totalMemory() - runtime.freeMemory()) / 1024L);
            loadingTextArea.method380("Mem:" + memoryUsage + "k", c, 0xffff00, k);
        }

        if (timeUntilSystemUpdate != 0) {
            int seconds = timeUntilSystemUpdate / 50;
            int updateTime = seconds / 60;
            seconds %= 60;

            if (seconds < 10) {
                loadingTextArea.method385(0xffff00, "System update in: " + updateTime + ":0" + seconds, 329, 4);
            } else {
                loadingTextArea.method385(0xffff00, "System update in: " + updateTime + ":" + seconds, 329, 4);
            }
            anInt849++;

            if (anInt849 > 75) {
                anInt849 = 0;
                stream.writePacketHeaderEnc(148);
            }
        }
    }

    private void addIgnore(long playerName) {
        try {
            if (playerName == 0L) {
                return;
            }
            if (ignoreCount >= 100) {
                pushMessage("Your ignore list is full. Max of 100 hit", 0, "");
                return;
            }
            String s = StringHelper.fixName(StringHelper.nameForLong(playerName));

            for (int j = 0; j < ignoreCount; j++) {
                if (ignoreListAsLongs[j] == playerName) {
                    pushMessage(s + " is already on your ignore list", 0, "");
                    return;
                }
            }

            for (int k = 0; k < friendsCount; k++) {
                if (friendsListAsLongs[k] == playerName) {
                    pushMessage("Please remove " + s + " from your friend list first", 0, "");
                    return;
                }
            }
            ignoreListAsLongs[ignoreCount++] = playerName;
            needDrawTabArea = true;
            stream.writePacketHeaderEnc(133);
            stream.writeLong(playerName);
            return;
        } catch (RuntimeException ex) {
            Signlink.printError("addIgnore: playerName=" + playerName + ", " + ex.toString());
        }
        throw new RuntimeException();
    }

    private void method114() {
        for (int i = -1; i < playerCount; i++) {
            int j;
            if (i == -1) {
                j = myPlayerIndex;
            } else {
                j = playerIndices[i];
            }
            Player player = playerArray[j];
            if (player != null) {
                method96(player);
            }
        }
    }

    private void method115() {
        if (loadingStage == 2) {
            for (Class30_Sub1 class30_sub1 = (Class30_Sub1) aClass19_1179.reverseGetFirst(); class30_sub1 != null; class30_sub1 = (Class30_Sub1) aClass19_1179.reverseGetNext()) {
                if (class30_sub1.anInt1294 > 0) {
                    class30_sub1.anInt1294--;
                }
                if (class30_sub1.anInt1294 == 0) {
                    if (class30_sub1.anInt1299 < 0 || ObjectManager.method178(class30_sub1.anInt1299, class30_sub1.anInt1301)) {
                        method142(class30_sub1.y, class30_sub1.plane, class30_sub1.anInt1300, class30_sub1.anInt1301, class30_sub1.x, class30_sub1.objectType, class30_sub1.anInt1299);
                        class30_sub1.unlink();
                    }
                } else {
                    if (class30_sub1.anInt1302 > 0) {
                        class30_sub1.anInt1302--;
                    }
                    if (class30_sub1.anInt1302 == 0 && class30_sub1.x >= 1 && class30_sub1.y >= 1 && class30_sub1.x <= 102 && class30_sub1.y <= 102 && (class30_sub1.anInt1291 < 0 || ObjectManager.method178(class30_sub1.anInt1291, class30_sub1.anInt1293))) {
                        method142(class30_sub1.y, class30_sub1.plane, class30_sub1.anInt1292, class30_sub1.anInt1293, class30_sub1.x, class30_sub1.objectType, class30_sub1.anInt1291);
                        class30_sub1.anInt1302 = -1;
                        if (class30_sub1.anInt1291 == class30_sub1.anInt1299 && class30_sub1.anInt1299 == -1) {
                            class30_sub1.unlink();
                        } else if (class30_sub1.anInt1291 == class30_sub1.anInt1299 && class30_sub1.anInt1292 == class30_sub1.anInt1300 && class30_sub1.anInt1293 == class30_sub1.anInt1301) {
                            class30_sub1.unlink();
                        }
                    }
                }
            }

        }
    }

    private void determineMenuSize() {
        int i = chatTextDrawingArea.getTextWidthWithoutColourCodes("Choose Option");
        for (int j = 0; j < menuActionRow; j++) {
            int k = chatTextDrawingArea.getTextWidthWithoutColourCodes(menuActionName[j]);
            if (k > i) {
                i = k;
            }
        }

        i += 8;
        int l = 15 * menuActionRow + 21;
        if (super.saveClickX > 4 && super.saveClickY > 4 && super.saveClickX < 516 && super.saveClickY < 338) {
            int i1 = super.saveClickX - 4 - i / 2;
            if (i1 + i > 512) {
                i1 = 512 - i;
            }
            if (i1 < 0) {
                i1 = 0;
            }
            int l1 = super.saveClickY - 4;
            if (l1 + l > 334) {
                l1 = 334 - l;
            }
            if (l1 < 0) {
                l1 = 0;
            }
            menuOpen = true;
            menuScreenArea = 0;
            menuOffsetX = i1;
            menuOffsetY = l1;
            menuWidth = i;
            anInt952 = 15 * menuActionRow + 22;
        }
        if (super.saveClickX > 553 && super.saveClickY > 205 && super.saveClickX < 743 && super.saveClickY < 466) {
            int j1 = super.saveClickX - 553 - i / 2;
            if (j1 < 0) {
                j1 = 0;
            } else if (j1 + i > 190) {
                j1 = 190 - i;
            }
            int i2 = super.saveClickY - 205;
            if (i2 < 0) {
                i2 = 0;
            } else if (i2 + l > 261) {
                i2 = 261 - l;
            }
            menuOpen = true;
            menuScreenArea = 1;
            menuOffsetX = j1;
            menuOffsetY = i2;
            menuWidth = i;
            anInt952 = 15 * menuActionRow + 22;
        }
        if (super.saveClickX > 17 && super.saveClickY > 357 && super.saveClickX < 496 && super.saveClickY < 453) {
            int k1 = super.saveClickX - 17 - i / 2;
            if (k1 < 0) {
                k1 = 0;
            } else if (k1 + i > 479) {
                k1 = 479 - i;
            }
            int j2 = super.saveClickY - 357;
            if (j2 < 0) {
                j2 = 0;
            } else if (j2 + l > 96) {
                j2 = 96 - l;
            }
            menuOpen = true;
            menuScreenArea = 2;
            menuOffsetX = k1;
            menuOffsetY = j2;
            menuWidth = i;
            anInt952 = 15 * menuActionRow + 22;
        }
    }

    private void updateLocalPlayerMovement(Stream stream) {
        stream.initBitAccess();
        int kee = stream.readBits(1);

        if (kee == 0) { // Nothing to update
            return;
        }
        int k = stream.readBits(2);

        if (k == 0) {
            updatingPlayersList[anInt893++] = myPlayerIndex;
            return;
        }

        if (k == 1) {
            int l = stream.readBits(3);
            myPlayer.moveInDir(false, l);
            int k1 = stream.readBits(1);

            if (k1 == 1) {
                updatingPlayersList[anInt893++] = myPlayerIndex;
            }
            return;
        }

        if (k == 2) {
            int i1 = stream.readBits(3);
            myPlayer.moveInDir(true, i1);
            int l1 = stream.readBits(3);
            myPlayer.moveInDir(true, l1);
            int j2 = stream.readBits(1);

            if (j2 == 1) {
                updatingPlayersList[anInt893++] = myPlayerIndex;
            }
            return;
        }

        if (k == 3) {
            plane = stream.readBits(2);
            int j1 = stream.readBits(1);
            int i2 = stream.readBits(1);

            if (i2 == 1) {
                updatingPlayersList[anInt893++] = myPlayerIndex;
            }
            int y = stream.readBits(7);
            int x = stream.readBits(7);
            myPlayer.setPosition(x, y, j1 == 1);
        }
    }

    private void nullLoader() {
        aBoolean831 = false;
        
        while (drawingFlames) {
            aBoolean831 = false;
            
            try {
                Thread.sleep(50L);
            } catch (InterruptedException _ex) {
            }
        }
        titleBoxBackground = null;
        buttonBackground = null;
        aBackgroundArray1152s = null;
        anIntArray850 = null;
        anIntArray851 = null;
        anIntArray852 = null;
        anIntArray853 = null;
        anIntArray1190 = null;
        anIntArray1191 = null;
        anIntArray828 = null;
        anIntArray829 = null;
        leftFlameSprite = null;
        rightFlameSprite = null;
    }

    private boolean method119(int i, int j) {
        boolean flag1 = false;
        RSInterface class9 = RSInterface.interfaceCache[j];
        for (int k = 0; k < class9.children.length; k++) {
            if (class9.children[k] == -1) {
                break;
            }
            RSInterface class9_1 = RSInterface.interfaceCache[class9.children[k]];
            if (class9_1.type == 1) {
                flag1 |= method119(i, class9_1.id);
            }
            if (class9_1.type == 6 && (class9_1.animationId != -1 || class9_1.anInt258 != -1)) {
                boolean flag2 = interfaceIsSelected(class9_1);
                int l;
                if (flag2) {
                    l = class9_1.anInt258;
                } else {
                    l = class9_1.animationId;
                }
                if (l != -1) {
                    Animation animation = Animation.anims[l];
                    for (class9_1.anInt208 += i; class9_1.anInt208 > animation.method258(class9_1.anInt246);) {
                        class9_1.anInt208 -= animation.method258(class9_1.anInt246) + 1;
                        class9_1.anInt246++;
                        if (class9_1.anInt246 >= animation.anInt352) {
                            class9_1.anInt246 -= animation.anInt356;
                            if (class9_1.anInt246 < 0 || class9_1.anInt246 >= animation.anInt352) {
                                class9_1.anInt246 = 0;
                            }
                        }
                        flag1 = true;
                    }

                }
            }
        }

        return flag1;
    }

    private int method120() {
        int j = 3;
        if (yCameraCurve < 310) {
            int k = xCameraPos >> 7;
            int l = yCameraPos >> 7;
            int i1 = myPlayer.x >> 7;
            int j1 = myPlayer.y >> 7;
            if ((byteGroundArray[plane][k][l] & 4) != 0) {
                j = plane;
            }
            int k1;
            if (i1 > k) {
                k1 = i1 - k;
            } else {
                k1 = k - i1;
            }
            int l1;
            if (j1 > l) {
                l1 = j1 - l;
            } else {
                l1 = l - j1;
            }
            if (k1 > l1) {
                int i2 = (l1 * 0x10000) / k1;
                int k2 = 32768;
                while (k != i1) {
                    if (k < i1) {
                        k++;
                    } else if (k > i1) {
                        k--;
                    }
                    if ((byteGroundArray[plane][k][l] & 4) != 0) {
                        j = plane;
                    }
                    k2 += i2;
                    if (k2 >= 0x10000) {
                        k2 -= 0x10000;
                        if (l < j1) {
                            l++;
                        } else if (l > j1) {
                            l--;
                        }
                        if ((byteGroundArray[plane][k][l] & 4) != 0) {
                            j = plane;
                        }
                    }
                }
            } else {
                int j2 = (k1 * 0x10000) / l1;
                int l2 = 32768;
                while (l != j1) {
                    if (l < j1) {
                        l++;
                    } else if (l > j1) {
                        l--;
                    }
                    if ((byteGroundArray[plane][k][l] & 4) != 0) {
                        j = plane;
                    }
                    l2 += j2;
                    if (l2 >= 0x10000) {
                        l2 -= 0x10000;
                        if (k < i1) {
                            k++;
                        } else if (k > i1) {
                            k--;
                        }
                        if ((byteGroundArray[plane][k][l] & 4) != 0) {
                            j = plane;
                        }
                    }
                }
            }
        }
        if ((byteGroundArray[plane][myPlayer.x >> 7][myPlayer.y >> 7] & 4) != 0) {
            j = plane;
        }
        return j;
    }

    private int method121() {
        int j = method42(plane, yCameraPos, xCameraPos);
        if (j - zCameraPos < 800 && (byteGroundArray[plane][xCameraPos >> 7][yCameraPos >> 7] & 4) != 0) {
            return plane;
        } else {
            return 3;
        }
    }

    private void removeIgnore(long playerName) {
        try {
            if (playerName == 0L) {
                return;
            }

            for (int j = 0; j < ignoreCount; j++) {
                if (ignoreListAsLongs[j] == playerName) {
                    ignoreCount--;
                    needDrawTabArea = true;
                    System.arraycopy(ignoreListAsLongs, j + 1, ignoreListAsLongs, j, ignoreCount - j);
                    stream.writePacketHeaderEnc(74);
                    stream.writeLong(playerName);
                    return;
                }
            }
            return;
        } catch (RuntimeException ex) {
            Signlink.printError("removeIgnore: playerName=" + playerName + ", " + ex.toString());
        }
        throw new RuntimeException();
    }

    public String getParameter(String s) {
        if (Signlink.mainapp != null) {
            return Signlink.mainapp.getParameter(s);
        } else {
            return super.getParameter(s);
        }
    }

    private void adjustVolume(boolean playAdjustingVolume, int volume) {
        Signlink.midiVolume = volume;

        if (playAdjustingVolume) {
            Signlink.midiName = "voladjust";
        }
    }

    private int extractInterfaceValues(RSInterface rsi, int j) {
        if (rsi.valueIndexArray == null || j >= rsi.valueIndexArray.length) {
            return -2;
        }
        
        try {
            int ids[] = rsi.valueIndexArray[j];
            int k = 0;
            int count = 0;
            int i1 = 0;
            
            do {
                int j1 = ids[count++];
                int k1 = 0;
                byte byte0 = 0;
                if (j1 == 0) {
                    return k;
                }
                if (j1 == 1) {
                    k1 = currentLevels[ids[count++]];
                }
                if (j1 == 2) {
                    k1 = maxStats[ids[count++]];
                }
                if (j1 == 3) {
                    k1 = currentExperience[ids[count++]];
                }
                if (j1 == 4) {
                    RSInterface rsi2 = RSInterface.interfaceCache[ids[count++]];
                    int k2 = ids[count++];
                    
                    if (k2 >= 0 && k2 < ItemDef.totalItems && (!ItemDef.forID(k2).membersObject || isMembers)) {
                        for (int j3 = 0; j3 < rsi2.inv.length; j3++) {
                            if (rsi2.inv[j3] == k2 + 1) {
                                k1 += rsi2.invStackSizes[j3];
                            }
                        }
                    }
                }
                if (j1 == 5) {
                    k1 = currentUserSetting[ids[count++]];
                }
                if (j1 == 6) {
                    k1 = anIntArray1019[maxStats[ids[count++]] - 1];
                }
                if (j1 == 7) {
                    k1 = (currentUserSetting[ids[count++]] * 100) / 46875;
                }
                if (j1 == 8) {
                    k1 = myPlayer.combatLevel;
                }
                if (j1 == 9) {
                    for (int l1 = 0; l1 < SkillConstants.COUNT; l1++) {
                        if (SkillConstants.ENABLED[l1]) {
                            k1 += maxStats[l1];
                        }
                    }
                }
                if (j1 == 10) {
                    RSInterface rsi3 = RSInterface.interfaceCache[ids[count++]];
                    int l2 = ids[count++] + 1;
                    
                    if (l2 >= 0 && l2 < ItemDef.totalItems && (!ItemDef.forID(l2).membersObject || isMembers)) {
                        for (int k3 = 0; k3 < rsi3.inv.length; k3++) {
                            if (rsi3.inv[k3] != l2) {
                                continue;
                            }
                            k1 = 0x3b9ac9ff;
                            break;
                        }
                    }
                }
                if (j1 == 11) {
                    k1 = runEnergy;
                }
                if (j1 == 12) {
                    k1 = weight;
                }
                if (j1 == 13) {
                    int i2 = currentUserSetting[ids[count++]];
                    int i3 = ids[count++];
                    k1 = (i2 & 1 << i3) == 0 ? 0 : 1;
                }
                if (j1 == 14) {
                    int j2 = ids[count++];
                    VarBit varBit = VarBit.cache[j2];
                    int l3 = varBit.anInt648;
                    int i4 = varBit.anInt649;
                    int j4 = varBit.anInt650;
                    int k4 = anIntArray1232[j4 - i4];
                    k1 = currentUserSetting[l3] >> i4 & k4;
                }
                if (j1 == 15) {
                    byte0 = 1;
                }
                if (j1 == 16) {
                    byte0 = 2;
                }
                if (j1 == 17) {
                    byte0 = 3;
                }
                if (j1 == 18) {
                    k1 = (myPlayer.x >> 7) + baseX;
                }
                if (j1 == 19) {
                    k1 = (myPlayer.y >> 7) + baseY;
                }
                if (j1 == 20) {
                    k1 = ids[count++];
                }
                if (byte0 == 0) {
                    if (i1 == 0) {
                        k += k1;
                    }
                    if (i1 == 1) {
                        k -= k1;
                    }
                    if (i1 == 2 && k1 != 0) {
                        k /= k1;
                    }
                    if (i1 == 3) {
                        k *= k1;
                    }
                    i1 = 0;
                } else {
                    i1 = byte0;
                }
            } while (true);
        } catch (Exception _ex) {
            return -1;
        }
    }

    private void drawTooltip() {
        if (menuActionRow < 2 && itemSelected == 0 && spellSelected == 0) {
            return;
        }
        String s;
        
        if (itemSelected == 1 && menuActionRow < 2) {
            s = "Use " + selectedItemName + " with...";
        } else if (spellSelected == 1 && menuActionRow < 2) {
            s = spellTooltip + "...";
        } else {
            s = menuActionName[menuActionRow - 1];
        }
        
        if (menuActionRow > 2) {
            s = s + "@whi@ / " + (menuActionRow - 2) + " more options";
        }
        chatTextDrawingArea.method390(4, 0xffffff, s, loopCycle / 1000, 15);
    }

    private void drawMinimap() {
        aRSImageProducer_1164.initDrawingArea();

        // Draw blacked out map
        if (minimapState == 2) {
            byte mapBytes[] = mapBack.bgPixels;
            int ai[] = DrawingArea.pixels;
            int mapLength = mapBytes.length;

            for (int i = 0; i < mapLength; i++) {
                if (mapBytes[i] == 0) {
                    ai[i] = 0;
                }
            }
            compass.method352(33, minimapInt1, anIntArray1057, 256, anIntArray968, 25, 0, 0, 33, 25);
            aRSImageProducer_1165.initDrawingArea();
            return;
        }
        int i = minimapInt1 + minimapInt2 & 0x7ff;
        int j = 48 + myPlayer.x / 32;
        int l2 = 464 - myPlayer.y / 32;
        sprite.method352(151, i, anIntArray1229, 256 + minimapInt3, anIntArray1052, l2, 5, 25, 146, j);
        compass.method352(33, minimapInt1, anIntArray1057, 256, anIntArray968, 25, 0, 0, 33, 25);

        for (int j5 = 0; j5 < anInt1071; j5++) {
            int k = (anIntArray1072[j5] * 4 + 2) - myPlayer.x / 32;
            int i3 = (anIntArray1073[j5] * 4 + 2) - myPlayer.y / 32;
            markMinimap(aClass30_Sub2_Sub1_Sub1Array1140[j5], k, i3);
        }

        for (int k5 = 0; k5 < 104; k5++) {
            for (int l5 = 0; l5 < 104; l5++) {
                NodeList class19 = groundArray[plane][k5][l5];
                if (class19 != null) {
                    int l = (k5 * 4 + 2) - myPlayer.x / 32;
                    int j3 = (l5 * 4 + 2) - myPlayer.y / 32;
                    markMinimap(mapDotItem, l, j3);
                }
            }
        }

        for (int i6 = 0; i6 < npcCount; i6++) {
            NPC npc = npcArray[npcIndices[i6]];
            if (npc != null && npc.isVisible()) {
                EntityDef entityDef = npc.desc;
                if (entityDef.childrenIDs != null) {
                    entityDef = entityDef.method161();
                }
                if (entityDef != null && entityDef.aBoolean87 && entityDef.aBoolean84) {
                    int i1 = npc.x / 32 - myPlayer.x / 32;
                    int k3 = npc.y / 32 - myPlayer.y / 32;
                    markMinimap(mapDotNPC, i1, k3);
                }
            }
        }

        for (int j6 = 0; j6 < playerCount; j6++) {
            Player player = playerArray[playerIndices[j6]];
            if (player != null && player.isVisible()) {
                int j1 = player.x / 32 - myPlayer.x / 32;
                int l3 = player.y / 32 - myPlayer.y / 32;
                boolean flag1 = false;
                long l6 = StringHelper.longForName(player.name);
                for (int k6 = 0; k6 < friendsCount; k6++) {
                    if (l6 != friendsListAsLongs[k6] || friendsNodeIDs[k6] == 0) {
                        continue;
                    }
                    flag1 = true;
                    break;
                }

                boolean flag2 = false;
                if (myPlayer.team != 0 && player.team != 0 && myPlayer.team == player.team) {
                    flag2 = true;
                }
                if (flag1) {
                    markMinimap(mapDotFriend, j1, l3);
                } else if (flag2) {
                    markMinimap(mapDotTeam, j1, l3);
                } else {
                    markMinimap(mapDotPlayer, j1, l3);
                }
            }
        }

        if (iconType != 0 && loopCycle % 20 < 10) {
            if (iconType == 1 && iconTargetNpcId >= 0 && iconTargetNpcId < npcArray.length) {
                NPC npc = npcArray[iconTargetNpcId];

                if (npc != null) {
                    int k1 = npc.x / 32 - myPlayer.x / 32;
                    int i4 = npc.y / 32 - myPlayer.y / 32;
                    method81(mapMarker, i4, k1);
                }
            }
            if (iconType == 2) {
                int l1 = ((iconX - baseX) * 4 + 2) - myPlayer.x / 32;
                int j4 = ((iconY - baseY) * 4 + 2) - myPlayer.y / 32;
                method81(mapMarker, j4, l1);
            }
            if (iconType == 10 && iconPlayerTargetId >= 0 && iconPlayerTargetId < playerArray.length) {
                Player player = playerArray[iconPlayerTargetId];

                if (player != null) {
                    int i2 = player.x / 32 - myPlayer.x / 32;
                    int k4 = player.y / 32 - myPlayer.y / 32;
                    method81(mapMarker, k4, i2);
                }
            }
        }

        if (destX != 0) {
            int j2 = (destX * 4 + 2) - myPlayer.x / 32;
            int l4 = (destY * 4 + 2) - myPlayer.y / 32;
            markMinimap(mapFlag, j2, l4);
        }
        DrawingArea.fillPixelsReverseOrder(3, 78, 97, 0xffffff, 3);
        aRSImageProducer_1165.initDrawingArea();
    }

    private void npcScreenPos(Entity entity, int i) {
        calcEntityScreenPos(entity.x, i, entity.y);
        //aryan	entity.entScreenX = spriteDrawX; entity.entScreenY = spriteDrawY;
    }

    private void calcEntityScreenPos(int i, int j, int l) {
        if (i < 128 || l < 128 || i > 13056 || l > 13056) {
            spriteDrawX = -1;
            spriteDrawY = -1;
            return;
        }
        int i1 = method42(plane, l, i) - j;
        i -= xCameraPos;
        i1 -= zCameraPos;
        l -= yCameraPos;
        int j1 = Model.modelIntArray1[yCameraCurve];
        int k1 = Model.modelIntArray2[yCameraCurve];
        int l1 = Model.modelIntArray1[xCameraCurve];
        int i2 = Model.modelIntArray2[xCameraCurve];
        int j2 = l * l1 + i * i2 >> 16;
        l = l * i2 - i * l1 >> 16;
        i = j2;
        j2 = i1 * k1 - l * j1 >> 16;
        l = i1 * j1 + l * k1 >> 16;
        i1 = j2;
        if (l >= 50) {
            spriteDrawX = Texture.textureInt1 + (i << 9) / l;
            spriteDrawY = Texture.textureInt2 + (i1 << 9) / l;
        } else {
            spriteDrawX = -1;
            spriteDrawY = -1;
        }
    }

    private void buildSplitPrivateChatMenu() {
        if (splitPrivateChat == 0) {
            return;
        }
        int i = 0;
        
        if (timeUntilSystemUpdate != 0) {
            i = 1;
        }
        
        for (int j = 0; j < 100; j++) {
            if (chatMessages[j] != null) {
                int k = chatTypes[j];
                String s = chatNames[j];
                boolean flag1 = false;
                
                if (s != null && s.startsWith("@cr1@")) {
                    s = s.substring(5);
                    boolean flag2 = true;
                }
                
                if (s != null && s.startsWith("@cr2@")) {
                    s = s.substring(5);
                    byte byte0 = 2;
                }
                
                if ((k == 3 || k == 7) && (k == 7 || privateChatMode == 0 || privateChatMode == 1 && isFriendOrSelf(s))) {
                    int l = 329 - i * 13;
                    
                    if (super.mouseX > 4 && super.mouseY - 4 > l - 10 && super.mouseY - 4 <= l + 3) {
                        int i1 = loadingTextArea.getTextWidthWithoutColourCodes("From:  " + s + chatMessages[j]) + 25;
                        
                        if (i1 > 450) {
                            i1 = 450;
                        }
                        
                        if (super.mouseX < 4 + i1) {
                            if (myPrivilege >= 1) {
                                menuActionName[menuActionRow] = "Report abuse @whi@" + s;
                                menuActionID[menuActionRow] = 2606;
                                menuActionRow++;
                            }
                            menuActionName[menuActionRow] = "Add ignore @whi@" + s;
                            menuActionID[menuActionRow] = 2042;
                            menuActionRow++;
                            menuActionName[menuActionRow] = "Add friend @whi@" + s;
                            menuActionID[menuActionRow] = 2337;
                            menuActionRow++;
                        }
                    }
                    if (++i >= 5) {
                        return;
                    }
                }
                
                if ((k == 5 || k == 6) && privateChatMode < 2 && ++i >= 5) {
                    return;
                }
            }
        }
    }

    private void method130(int j, int k, int l, int i1, int j1, int k1, int plane,
            int i2, int j2) {
        Class30_Sub1 class30_sub1 = null;
        
        for (Class30_Sub1 class30_sub1_1 = (Class30_Sub1) aClass19_1179.reverseGetFirst(); class30_sub1_1 != null; class30_sub1_1 = (Class30_Sub1) aClass19_1179.reverseGetNext()) {
            if (class30_sub1_1.plane != plane || class30_sub1_1.x != i2 || class30_sub1_1.y != j1 || class30_sub1_1.objectType != i1) {
                continue;
            }
            class30_sub1 = class30_sub1_1;
            break;
        }

        if (class30_sub1 == null) {
            class30_sub1 = new Class30_Sub1();
            class30_sub1.plane = plane;
            class30_sub1.objectType = i1;
            class30_sub1.x = i2;
            class30_sub1.y = j1;
            method89(class30_sub1);
            aClass19_1179.insertHead(class30_sub1);
        }
        class30_sub1.anInt1291 = k;
        class30_sub1.anInt1293 = k1;
        class30_sub1.anInt1292 = l;
        class30_sub1.anInt1302 = j2;
        class30_sub1.anInt1294 = j;
    }

    private boolean interfaceIsSelected(RSInterface class9) {
        if (class9.anIntArray245 == null) {
            return false;
        }
        for (int i = 0; i < class9.anIntArray245.length; i++) {
            int j = extractInterfaceValues(class9, i);
            int k = class9.anIntArray212[i];
            if (class9.anIntArray245[i] == 2) {
                if (j >= k) {
                    return false;
                }
            } else if (class9.anIntArray245[i] == 3) {
                if (j <= k) {
                    return false;
                }
            } else if (class9.anIntArray245[i] == 4) {
                if (j == k) {
                    return false;
                }
            } else if (j != k) {
                return false;
            }
        }

        return true;
    }

    private DataInputStream openJagGrabInputStream(String resource) throws IOException {
        //       if(!aBoolean872)
        //           if(signlink.mainapp != null)
        //               return signlink.openurl(resource);
        //           else
        //               return new DataInputStream((new URL(getCodeBase(), resource)).openStream());
        if (jagGrabSocket != null) {
            try {
                jagGrabSocket.close();
            } catch (Exception _ex) {
            }
            jagGrabSocket = null;
        }
        jagGrabSocket = openSocket(43595);
        jagGrabSocket.setSoTimeout(10000);
        InputStream input = jagGrabSocket.getInputStream();
        OutputStream output = jagGrabSocket.getOutputStream();
        output.write(("JAGGRAB /" + resource + "\n\n").getBytes());
        return new DataInputStream(input);
    }

    private void doFlamesDrawing() {
        int c = 256;

        if (anInt1040 > 0) {
            for (int i = 0; i < 256; i++) {
                if (anInt1040 > 768) {
                    anIntArray850[i] = method83(anIntArray851[i], anIntArray852[i], 1024 - anInt1040);
                } else if (anInt1040 > 256) {
                    anIntArray850[i] = anIntArray852[i];
                } else {
                    anIntArray850[i] = method83(anIntArray852[i], anIntArray851[i], 256 - anInt1040);
                }
            }
        } else if (anInt1041 > 0) {
            for (int j = 0; j < 256; j++) {
                if (anInt1041 > 768) {
                    anIntArray850[j] = method83(anIntArray851[j], anIntArray853[j], 1024 - anInt1041);
                } else if (anInt1041 > 256) {
                    anIntArray850[j] = anIntArray853[j];
                } else {
                    anIntArray850[j] = method83(anIntArray853[j], anIntArray851[j], 256 - anInt1041);
                }
            }
        } else { // Colours the flame from a smoke-looking colour to reddish-yellow flames colour
            System.arraycopy(anIntArray851, 0, anIntArray850, 0, 256);
        }
        System.arraycopy(leftFlameSprite.spritePixels, 0, leftFlameImageProducer.pixels, 0, 33920);

        int i1 = 0;
        int j1 = 1152;

        for (int k1 = 1; k1 < c - 1; k1++) {
            int l1 = (anIntArray969[k1] * (c - k1)) / c;
            int j2 = 22 + l1;

            if (j2 < 0) {
                j2 = 0;
            }
            i1 += j2;

            for (int l2 = j2; l2 < 128; l2++) {
                int j3 = anIntArray828[i1++];

                if (j3 != 0) {
                    int l3 = j3;
                    int j4 = 256 - j3;
                    j3 = anIntArray850[j3];
                    int l4 = leftFlameImageProducer.pixels[j1];
                    leftFlameImageProducer.pixels[j1++] = ((j3 & 0xff00ff) * l3 + (l4 & 0xff00ff) * j4 & 0xff00ff00) + ((j3 & 0xff00) * l3 + (l4 & 0xff00) * j4 & 0xff0000) >> 8;
                } else {
                    j1++;
                }
            }
            j1 += j2;
        }

        leftFlameImageProducer.drawGraphics(super.graphics, 0, 0);
        System.arraycopy(rightFlameSprite.spritePixels, 0, rightFlameImageProducer.pixels, 0, 33920);

        i1 = 0;
        j1 = 1176;

        for (int k2 = 1; k2 < c - 1; k2++) {
            int i3 = (anIntArray969[k2] * (c - k2)) / c;
            int k3 = 103 - i3;
            j1 += i3;

            for (int i4 = 0; i4 < k3; i4++) {
                int k4 = anIntArray828[i1++];

                if (k4 != 0) {
                    int i5 = k4;
                    int j5 = 256 - k4;
                    k4 = anIntArray850[k4];
                    int k5 = rightFlameImageProducer.pixels[j1];
                    rightFlameImageProducer.pixels[j1++] = ((k4 & 0xff00ff) * i5 + (k5 & 0xff00ff) * j5 & 0xff00ff00) + ((k4 & 0xff00) * i5 + (k5 & 0xff00) * j5 & 0xff0000) >> 8;
                } else {
                    j1++;
                }
            }
            i1 += 128 - k3;
            j1 += 128 - k3 - i3;
        }
        rightFlameImageProducer.drawGraphics(super.graphics, 637, 0);
    }

    private void updateOtherPlayerMovement(Stream stream) {
        int amountToUpdate = stream.readBits(8);

        if (amountToUpdate < playerCount) {
            for (int k = amountToUpdate; k < playerCount; k++) {
                removeFromUpdatingPlayersList[anInt839++] = playerIndices[k];
            }
        }

        if (amountToUpdate > playerCount) {
            Signlink.printError(myUsername + " Too many players in other player movement update packet");
            throw new RuntimeException("eek");
        }
        playerCount = 0;

        for (int i = 0; i < amountToUpdate; i++) {
            int playerId = playerIndices[i];
            Player player = playerArray[playerId];
            int movementUpdateRequired = stream.readBits(1);

            if (movementUpdateRequired == 0) {
                playerIndices[playerCount++] = playerId;
                player.loopCycle = loopCycle;
            } else {
                int movementUpdateType = stream.readBits(2);

                if (movementUpdateType == 0) {
                    playerIndices[playerCount++] = playerId;
                    player.loopCycle = loopCycle;
                    updatingPlayersList[anInt893++] = playerId;
                } else if (movementUpdateType == 1) {
                    playerIndices[playerCount++] = playerId;
                    player.loopCycle = loopCycle;
                    int movementDirection = stream.readBits(3);
                    player.moveInDir(false, movementDirection);
                    int addToUpdatingList = stream.readBits(1);

                    if (addToUpdatingList == 1) {
                        updatingPlayersList[anInt893++] = playerId;
                    }
                } else if (movementUpdateType == 2) {
                    playerIndices[playerCount++] = playerId;
                    player.loopCycle = loopCycle;
                    int lastDirection = stream.readBits(3);
                    player.moveInDir(true, lastDirection);
                    int runningDirection = stream.readBits(3);
                    player.moveInDir(true, runningDirection);
                    int addToUpdatingList = stream.readBits(1);

                    if (addToUpdatingList == 1) {
                        updatingPlayersList[anInt893++] = playerId;
                    }
                } else if (movementUpdateType == 3) {
                    removeFromUpdatingPlayersList[anInt839++] = playerId;
                }
            }
        }
    }

    private void drawLoginScreen(boolean hideLoginCancelButtons) {
        resetImageProducers();
        aRSImageProducer_1109.initDrawingArea();
        titleBoxBackground.draw(0, 0);
        int c = 360 / 2;
        int c1 = 200 / 2;

        if (loginScreenState == 0) {
            int offsetY = c1 + 80;
            aTextDrawingArea_1270.drawText(0x75a9a9, c, onDemandFetcher.status, offsetY, true);
            offsetY = c1 - 20;
            chatTextDrawingArea.drawText(0xffff00, c, "Welcome to RuneScape", offsetY, true);
            int buttonOffsetX = c - 80;
            int buttonOffsetY = c1 + 20;
            buttonBackground.draw(buttonOffsetX - 73, buttonOffsetY - 20);
            chatTextDrawingArea.drawText(0xffffff, buttonOffsetX, "New User", buttonOffsetY + 5, true);
            buttonOffsetX = c + 80;
            buttonBackground.draw(buttonOffsetX - 73, buttonOffsetY - 20);
            chatTextDrawingArea.drawText(0xffffff, buttonOffsetX, "Existing User", buttonOffsetY + 5, true);
        }

        if (loginScreenState == 2) {
            int offsetY = c1 - 40;

            if (loginMessage1.length() > 0) {
                chatTextDrawingArea.drawText(0xffff00, c, loginMessage1, offsetY - 15, true);
                chatTextDrawingArea.drawText(0xffff00, c, loginMessage2, offsetY, true);
                offsetY += 30;
            } else {
                chatTextDrawingArea.drawText(0xffff00, c, loginMessage2, offsetY - 7, true);
                offsetY += 30;
            }
            chatTextDrawingArea.drawText(true, c - 90, 0xffffff,
                    "Username: " + myUsername + ((loginScreenCursorPos == 0) & (loopCycle % 40 < 20) ? "@yel@|" : ""), offsetY);
            offsetY += 15;
            chatTextDrawingArea.drawText(true, c - 88, 0xffffff,
                    "Password: " + StringHelper.hidePassword(myPassword) + ((loginScreenCursorPos == 1) & (loopCycle % 40 < 20) ? "@yel@|" : ""), offsetY);

            if (!hideLoginCancelButtons) {
                int buttonOffsetX = c - 80;
                int buttonOffsetY = c1 + 50;
                buttonBackground.draw(buttonOffsetX - 73, buttonOffsetY - 20);
                chatTextDrawingArea.drawText(0xffffff, buttonOffsetX, "Login", buttonOffsetY + 5, true);
                buttonOffsetX = c + 80;
                buttonBackground.draw(buttonOffsetX - 73, buttonOffsetY - 20);
                chatTextDrawingArea.drawText(0xffffff, buttonOffsetX, "Cancel", buttonOffsetY + 5, true);
            }
        }

        if (loginScreenState == 3) {
            chatTextDrawingArea.drawText(0xffff00, c, "Create a free account", c1 - 60, true);
            int offsetY = c1 - 35;
            chatTextDrawingArea.drawText(0xffffff, c, "To create a new account you need to", offsetY, true);
            offsetY += 15;
            chatTextDrawingArea.drawText(0xffffff, c, "go back to the main RuneScape webpage", offsetY, true);
            offsetY += 15;
            chatTextDrawingArea.drawText(0xffffff, c, "and choose the red 'create account'", offsetY, true);
            offsetY += 15;
            chatTextDrawingArea.drawText(0xffffff, c, "button at the top right of that page.", offsetY, true);
            int buttonOffsetX = c;
            int buttonOffsetY = c1 + 50;
            buttonBackground.draw(buttonOffsetX - 73, buttonOffsetY - 20);
            chatTextDrawingArea.drawText(0xffffff, buttonOffsetX, "Cancel", buttonOffsetY + 5, true);
        }
        aRSImageProducer_1109.drawGraphics(super.graphics, 202, 171);

        if (welcomeScreenRaised) {
            welcomeScreenRaised = false;
            aRSImageProducer_1107.drawGraphics(super.graphics, 128, 0);
            aRSImageProducer_1108.drawGraphics(super.graphics, 202, 371);
            aRSImageProducer_1112.drawGraphics(super.graphics, 0, 265);
            aRSImageProducer_1113.drawGraphics(super.graphics, 562, 265);
            aRSImageProducer_1114.drawGraphics(super.graphics, 128, 171);
            aRSImageProducer_1115.drawGraphics(super.graphics, 562, 171);
        }
    }

    private void drawFlames() {
        drawingFlames = true;

        try {
            long l = System.currentTimeMillis();
            int i = 0;
            int j = 20;

            while (aBoolean831) {
                anInt1208++;
                calcFlamesPosition();
                calcFlamesPosition();
                doFlamesDrawing();

                if (++i > 10) {
                    long l1 = System.currentTimeMillis();
                    int k = (int) (l1 - l) / 10 - j;
                    j = 40 - k;

                    if (j < 5) {
                        j = 5;
                    }
                    i = 0;
                    l = l1;
                }
                try {
                    Thread.sleep(j);
                } catch (Exception e) {
                }
            }
        } catch (Exception e) {
        }
        drawingFlames = false;
    }

    public void raiseWelcomeScreen() {
        welcomeScreenRaised = true;
    }

    private void parseGroundItemPacket(Stream stream, int length) {
        if (length == 84) {
            int k = stream.readUByte();
            int x = x1 + (k >> 4 & 7);
            int y = y1 + (k & 7);
            int itemId = stream.readUShort();
            int k11 = stream.readUShort();
            int l13 = stream.readUShort();
            
            if (x >= 0 && y >= 0 && x < 104 && y < 104) {
                NodeList groundItems = groundArray[plane][x][y];

                if (groundItems != null) {
                    for (Item item = (Item) groundItems.reverseGetFirst(); item != null; item = (Item) groundItems.reverseGetNext()) {
                        if (item.ID != (itemId & 0x7fff) || item.anInt1559 != k11) {
                            continue;
                        }
                        item.anInt1559 = l13;
                        break;
                    }
                    spawnGroundItem(x, y);
                }
            }
            return;
        }

        if (length == 105) {
            int l = stream.readUByte();
            int k3 = x1 + (l >> 4 & 7);
            int j6 = y1 + (l & 7);
            int i9 = stream.readUShort();
            int l11 = stream.readUByte();
            int i14 = l11 >> 4 & 0xf;
            int i16 = l11 & 7;

            if (myPlayer.smallX[0] >= k3 - i14 && myPlayer.smallX[0] <= k3
                    + i14 && myPlayer.smallY[0] >= j6 - i14 && myPlayer.smallY[0] <= j6
                    + i14 && aBoolean848 && !lowMem && soundSlot < 50) {
                soundIds[soundSlot] = i9;
                soundVolume[soundSlot] = i16;
                soundFinishTime[soundSlot] = Sound.durations[i9];
                soundSlot++;
            }
        }

        if (length == 215) {
            int itemId = stream.readUShortA();
            int l3 = stream.readUByteS();
            int x = x1 + (l3 >> 4 & 7);
            int y = y1 + (l3 & 7);
            int i12 = stream.readUShortA();
            int j14 = stream.readUShort();
            
            if (x >= 0 && y >= 0 && x < 104 && y < 104 && i12 != playerListIndex) {
                Item item = new Item();
                item.ID = itemId;
                item.anInt1559 = j14;
                
                if (groundArray[plane][x][y] == null) {
                    groundArray[plane][x][y] = new NodeList();
                }
                groundArray[plane][x][y].insertHead(item);
                spawnGroundItem(x, y);
            }
            return;
        }
        
        if (length == 156) {
            int j1 = stream.readUByteA();
            int x = x1 + (j1 >> 4 & 7);
            int y = y1 + (j1 & 7);
            int k9 = stream.readUShort();
            
            if (x >= 0 && y >= 0 && x < 104 && y < 104) {
                NodeList list = groundArray[plane][x][y];
                if (list != null) {
                    for (Item item = (Item) list.reverseGetFirst(); item != null; item = (Item) list.reverseGetNext()) {
                        if (item.ID != (k9 & 0x7fff)) {
                            continue;
                        }
                        item.unlink();
                        break;
                    }

                    if (list.reverseGetFirst() == null) {
                        groundArray[plane][x][y] = null;
                    }
                    spawnGroundItem(x, y);
                }
            }
            return;
        }
        
        if (length == 160) {
            int k1 = stream.readUByteS();
            int x = x1 + (k1 >> 4 & 7);
            int y = y1 + (k1 & 7);
            int l9 = stream.readUByteS();
            int j12 = l9 >> 2;
            int k14 = l9 & 3;
            int j16 = anIntArray1177[j12];
            int j17 = stream.readUShortA();
            
            if (x >= 0 && y >= 0 && x < 103 && y < 103) {
                int j18 = intGroundArray[plane][x][y];
                int i19 = intGroundArray[plane][x + 1][y];
                int l19 = intGroundArray[plane][x + 1][y + 1];
                int k20 = intGroundArray[plane][x][y + 1];
                
                if (j16 == 0) {
                    Object1 obj1 = worldController.getObject1(plane, x, y);
                    if (obj1 != null) {
                        int k21 = obj1.uid >> 14 & 0x7fff;
                        
                        if (j12 == 2) {
                            obj1.anim1 = new Animable_Sub5(k21, 4 + k14, 2, i19, l19, j18, k20, j17, false);
                            obj1.anim2 = new Animable_Sub5(k21, k14 + 1 & 3, 2, i19, l19, j18, k20, j17, false);
                        } else {
                            obj1.anim1 = new Animable_Sub5(k21, k14, j12, i19, l19, j18, k20, j17, false);
                        }
                    }
                }
                
                if (j16 == 1) {
                    Object2 obj2 = worldController.getObject2(x, y, plane);
                    if (obj2 != null) {
                        obj2.anim = new Animable_Sub5(obj2.uid >> 14 & 0x7fff, 0, 4, i19, l19, j18, k20, j17, false);
                    }
                }
                if (j16 == 2) {
                    Object5 obj5 = worldController.getObject5(x, y, plane);
                    
                    if (j12 == 11) {
                        j12 = 10;
                    }
                    
                    if (obj5 != null) {
                        obj5.anim = new Animable_Sub5(obj5.uid >> 14 & 0x7fff, k14, j12, i19, l19, j18, k20, j17, false);
                    }
                }
                
                if (j16 == 3) {
                    Object3 obj3 = worldController.getObject3(y, x, plane);
                    
                    if (obj3 != null) {
                        obj3.anim = new Animable_Sub5(obj3.uid >> 14 & 0x7fff, k14, 22, i19, l19, j18, k20, j17, false);
                    }
                }
            }
            return;
        }
        
        if (length == 147) {
            int l1 = stream.readUByteS();
            int x = x1 + (l1 >> 4 & 7);
            int y = y1 + (l1 & 7);
            int i10 = stream.readUShort();
            byte byte0 = stream.readByteS();
            int l14 = stream.readUShortLE();
            byte byte1 = stream.readByteC();
            int k17 = stream.readUShort();
            int k18 = stream.readUByteS();
            int j19 = k18 >> 2;
            int i20 = k18 & 3;
            int l20 = anIntArray1177[j19];
            byte byte2 = stream.readByte();
            int objectId = stream.readUShort();
            byte byte3 = stream.readByteC();
            Player player;
            
            if (i10 == playerListIndex) {
                player = myPlayer;
            } else {
                player = playerArray[i10];
            }
            
            if (player != null) {
                ObjectDef objDef = ObjectDef.forID(objectId);
                int i22 = intGroundArray[plane][x][y];
                int j22 = intGroundArray[plane][x + 1][y];
                int k22 = intGroundArray[plane][x + 1][y + 1];
                int l22 = intGroundArray[plane][x][y + 1];
                Model model = objDef.method578(j19, i20, i22, j22, k22, l22, -1);
                
                if (model != null) {
                    method130(k17 + 1, -1, 0, l20, y, 0, plane, x, l14 + 1);
                    player.anInt1707 = l14 + loopCycle;
                    player.anInt1708 = k17 + loopCycle;
                    player.aModel_1714 = model;
                    int i23 = objDef.anInt744;
                    int j23 = objDef.anInt761;
                    
                    if (i20 == 1 || i20 == 3) {
                        i23 = objDef.anInt761;
                        j23 = objDef.anInt744;
                    }
                    player.anInt1711 = x * 128 + i23 * 64;
                    player.anInt1713 = y * 128 + j23 * 64;
                    player.anInt1712 = method42(plane, player.anInt1713, player.anInt1711);
                    
                    if (byte2 > byte0) {
                        byte byte4 = byte2;
                        byte2 = byte0;
                        byte0 = byte4;
                    }
                    
                    if (byte3 > byte1) {
                        byte byte5 = byte3;
                        byte3 = byte1;
                        byte1 = byte5;
                    }
                    player.anInt1719 = x + byte2;
                    player.anInt1721 = x + byte0;
                    player.anInt1720 = y + byte3;
                    player.anInt1722 = y + byte1;
                }
            }
        }
        
        if (length == 151) {
            int i2 = stream.readUByteA();
            int l4 = x1 + (i2 >> 4 & 7);
            int k7 = y1 + (i2 & 7);
            int j10 = stream.readUShortLE();
            int k12 = stream.readUByteS();
            int i15 = k12 >> 2;
            int k16 = k12 & 3;
            int l17 = anIntArray1177[i15];
            
            if (l4 >= 0 && k7 >= 0 && l4 < 104 && k7 < 104) {
                method130(-1, j10, k16, l17, k7, i15, plane, l4, 0);
            }
            return;
        }
        
        if (length == 4) {
            int j2 = stream.readUByte();
            int i5 = x1 + (j2 >> 4 & 7);
            int l7 = y1 + (j2 & 7);
            int k10 = stream.readUShort();
            int l12 = stream.readUByte();
            int j15 = stream.readUShort();
            
            if (i5 >= 0 && l7 >= 0 && i5 < 104 && l7 < 104) {
                i5 = i5 * 128 + 64;
                l7 = l7 * 128 + 64;
                Animable_Sub3 animable = new Animable_Sub3(plane, loopCycle, j15, k10, method42(plane, l7, i5) - l12, l7, i5);
                aClass19_1056.insertHead(animable);
            }
            return;
        }
        
        if (length == 44) {
            int itemId = stream.readUShortLEA();
            int j5 = stream.readUShort();
            int i8 = stream.readUByte();
            int x = x1 + (i8 >> 4 & 7);
            int y = y1 + (i8 & 7);
            
            if (x >= 0 && y >= 0 && x < 104 && y < 104) {
                Item item = new Item();
                item.ID = itemId;
                item.anInt1559 = j5;
                
                if (groundArray[plane][x][y] == null) {
                    groundArray[plane][x][y] = new NodeList();
                }
                groundArray[plane][x][y].insertHead(item);
                spawnGroundItem(x, y);
            }
            return;
        }
        
        if (length == 101) {
            int l2 = stream.readUByteC();
            int k5 = l2 >> 2;
            int j8 = l2 & 3;
            int i11 = anIntArray1177[k5];
            int j13 = stream.readUByte();
            int k15 = x1 + (j13 >> 4 & 7);
            int l16 = y1 + (j13 & 7);
            
            if (k15 >= 0 && l16 >= 0 && k15 < 104 && l16 < 104) {
                method130(-1, -1, j8, i11, l16, k5, plane, k15, 0);
            }
            return;
        }
        if (length == 117) {
            int i3 = stream.readUByte();
            int l5 = x1 + (i3 >> 4 & 7);
            int k8 = y1 + (i3 & 7);
            int j11 = l5 + stream.readByte();
            int k13 = k8 + stream.readByte();
            int l15 = stream.readShort();
            int i17 = stream.readUShort();
            int i18 = stream.readUByte() * 4;
            int l18 = stream.readUByte() * 4;
            int k19 = stream.readUShort();
            int j20 = stream.readUShort();
            int i21 = stream.readUByte();
            int j21 = stream.readUByte();
            if (l5 >= 0 && k8 >= 0 && l5 < 104 && k8 < 104 && j11 >= 0 && k13 >= 0 && j11 < 104 && k13 < 104 && i17 != 65535) {
                l5 = l5 * 128 + 64;
                k8 = k8 * 128 + 64;
                j11 = j11 * 128 + 64;
                k13 = k13 * 128 + 64;
                Animable_Sub4 class30_sub2_sub4_sub4 = new Animable_Sub4(i21, l18, k19 + loopCycle, j20 + loopCycle, j21, plane, method42(plane, k8, l5) - i18, k8, l5, l15, i17);
                class30_sub2_sub4_sub4.method455(k19 + loopCycle, k13, method42(plane, k13, j11) - l18, j11);
                aClass19_1013.insertHead(class30_sub2_sub4_sub4);
            }
        }
    }

    private void method139(Stream stream) {
        stream.initBitAccess();
        int k = stream.readBits(8);
        
        if (k < npcCount) {
            for (int l = k; l < npcCount; l++) {
                removeFromUpdatingPlayersList[anInt839++] = npcIndices[l];
            }

        }
        if (k > npcCount) {
            Signlink.printError(myUsername + " Too many npcs");
            throw new RuntimeException("eek");
        }
        npcCount = 0;
        
        for (int i1 = 0; i1 < k; i1++) {
            int j1 = npcIndices[i1];
            NPC npc = npcArray[j1];
            int k1 = stream.readBits(1);
            
            if (k1 == 0) {
                npcIndices[npcCount++] = j1;
                npc.loopCycle = loopCycle;
            } else {
                int l1 = stream.readBits(2);
                
                if (l1 == 0) {
                    npcIndices[npcCount++] = j1;
                    npc.loopCycle = loopCycle;
                    updatingPlayersList[anInt893++] = j1;
                } else if (l1 == 1) {
                    npcIndices[npcCount++] = j1;
                    npc.loopCycle = loopCycle;
                    int moveDir = stream.readBits(3);
                    npc.moveInDir(false, moveDir);
                    int k2 = stream.readBits(1);
                    
                    if (k2 == 1) {
                        updatingPlayersList[anInt893++] = j1;
                    }
                } else if (l1 == 2) {
                    npcIndices[npcCount++] = j1;
                    npc.loopCycle = loopCycle;
                    int j2 = stream.readBits(3);
                    npc.moveInDir(true, j2);
                    int l2 = stream.readBits(3);
                    npc.moveInDir(true, l2);
                    int i3 = stream.readBits(1);
                    
                    if (i3 == 1) {
                        updatingPlayersList[anInt893++] = j1;
                    }
                } else if (l1 == 3) {
                    removeFromUpdatingPlayersList[anInt839++] = j1;
                }
            }
        }
    }

    private void processLoginScreenInput() {
        if (loginScreenState == 0) {
            int i = super.myWidth / 2 - 80;
            int l = super.myHeight / 2 + 20;
            l += 20;
            if (super.clickMode3 == 1 && super.saveClickX >= i - 75 && super.saveClickX <= i + 75 && super.saveClickY >= l - 20 && super.saveClickY <= l + 20) {
                loginScreenState = 3;
                loginScreenCursorPos = 0;
            }
            i = super.myWidth / 2 + 80;
            if (super.clickMode3 == 1 && super.saveClickX >= i - 75 && super.saveClickX <= i + 75 && super.saveClickY >= l - 20 && super.saveClickY <= l + 20) {
                loginMessage1 = "";
                loginMessage2 = "Enter your username & password.";
                loginScreenState = 2;
                loginScreenCursorPos = 0;
            }
        } else {
            if (loginScreenState == 2) {
                int j = super.myHeight / 2 - 40;
                j += 30;
                j += 25;
                if (super.clickMode3 == 1 && super.saveClickY >= j - 15 && super.saveClickY < j) {
                    loginScreenCursorPos = 0;
                }
                j += 15;
                if (super.clickMode3 == 1 && super.saveClickY >= j - 15 && super.saveClickY < j) {
                    loginScreenCursorPos = 1;
                }
                j += 15;
                int i1 = super.myWidth / 2 - 80;
                int k1 = super.myHeight / 2 + 50;
                k1 += 20;
                if (super.clickMode3 == 1 && super.saveClickX >= i1 - 75 && super.saveClickX <= i1 + 75 && super.saveClickY >= k1 - 20 && super.saveClickY <= k1 + 20) {
                    loginFailures = 0;
                    login(myUsername, myPassword, false);

                    if (loggedIn) {
                        return;
                    }
                }
                i1 = super.myWidth / 2 + 80;
                if (super.clickMode3 == 1 && super.saveClickX >= i1 - 75 && super.saveClickX <= i1 + 75 && super.saveClickY >= k1 - 20 && super.saveClickY <= k1 + 20) {
                    loginScreenState = 0;
                    //                   myUsername = "";
                    //                   myPassword = "";
                }
                do {
                    int l1 = readChar(-796);
                    if (l1 == -1) {
                        break;
                    }
                    boolean flag1 = false;
                    for (int i2 = 0; i2 < VALID_PASSWORD_CHARACTERS.length(); i2++) {
                        if (l1 != VALID_PASSWORD_CHARACTERS.charAt(i2)) {
                            continue;
                        }
                        flag1 = true;
                        break;
                    }

                    if (loginScreenCursorPos == 0) {
                        if (l1 == 8 && myUsername.length() > 0) {
                            myUsername = myUsername.substring(0, myUsername.length() - 1);
                        }
                        if (l1 == 9 || l1 == 10 || l1 == 13) {
                            loginScreenCursorPos = 1;
                        }
                        if (flag1) {
                            myUsername += (char) l1;
                        }
                        if (myUsername.length() > 12) {
                            myUsername = myUsername.substring(0, 12);
                        }
                    } else if (loginScreenCursorPos == 1) {
                        if (l1 == 8 && myPassword.length() > 0) {
                            myPassword = myPassword.substring(0, myPassword.length() - 1);
                        }
                        if (l1 == 9 || l1 == 10 || l1 == 13) {
                            loginScreenCursorPos = 0;
                        }
                        if (flag1) {
                            myPassword += (char) l1;
                        }
                        if (myPassword.length() > 20) {
                            myPassword = myPassword.substring(0, 20);
                        }
                    }
                } while (true);
                return;
            }
            if (loginScreenState == 3) {
                int k = super.myWidth / 2;
                int j1 = super.myHeight / 2 + 50;
                j1 += 20;
                if (super.clickMode3 == 1 && super.saveClickX >= k - 75 && super.saveClickX <= k + 75 && super.saveClickY >= j1 - 20 && super.saveClickY <= j1 + 20) {
                    loginScreenState = 0;
                }
            }
        }
    }

    private void markMinimap(Sprite sprite, int i, int j) {
        int k = minimapInt1 + minimapInt2 & 0x7ff;
        int l = i * i + j * j;
        if (l > 6400) {
            return;
        }
        int i1 = Model.modelIntArray1[k];
        int j1 = Model.modelIntArray2[k];
        i1 = (i1 * 256) / (minimapInt3 + 256);
        j1 = (j1 * 256) / (minimapInt3 + 256);
        int k1 = j * i1 + i * j1 >> 16;
        int l1 = j * j1 - i * i1 >> 16;
        if (l > 2500) {
            sprite.method354(mapBack, 83 - l1 - sprite.anInt1445 / 2 - 4, ((94 + k1) - sprite.anInt1444 / 2) + 4);
        } else {
            sprite.drawSprite(((94 + k1) - sprite.anInt1444 / 2) + 4, 83 - l1 - sprite.anInt1445 / 2 - 4);
        }
    }

    private void method142(int i, int j, int k, int l, int i1, int j1, int k1) {
        if (i1 >= 1 && i >= 1 && i1 <= 102 && i <= 102) {
            if (lowMem && j != plane) {
                return;
            }
            int i2 = 0;
            if (j1 == 0) {
                i2 = worldController.getObject1Uid(j, i1, i);
            }
            if (j1 == 1) {
                i2 = worldController.getObject2Uid(j, i1, i);
            }
            if (j1 == 2) {
                i2 = worldController.getObject5Uid(j, i1, i);
            }
            if (j1 == 3) {
                i2 = worldController.getObject3Uid(j, i1, i);
            }
            if (i2 != 0) {
                int i3 = worldController.method304(j, i1, i, i2);
                int j2 = i2 >> 14 & 0x7fff;
                int k2 = i3 & 0x1f;
                int l2 = i3 >> 6;
                if (j1 == 0) {
                    worldController.method291(i1, j, i, (byte) -119);
                    ObjectDef class46 = ObjectDef.forID(j2);
                    if (class46.aBoolean767) {
                        clipMap[j].method215(l2, k2, class46.aBoolean757, i1, i);
                    }
                }
                if (j1 == 1) {
                    worldController.deleteObject2(i, j, i1);
                }
                if (j1 == 2) {
                    worldController.method293(j, i1, i);
                    ObjectDef class46_1 = ObjectDef.forID(j2);
                    if (i1 + class46_1.anInt744 > 103 || i + class46_1.anInt744 > 103 || i1 + class46_1.anInt761 > 103 || i + class46_1.anInt761 > 103) {
                        return;
                    }
                    if (class46_1.aBoolean767) {
                        clipMap[j].method216(l2, class46_1.anInt744, i1, i, class46_1.anInt761, class46_1.aBoolean757);
                    }
                }
                if (j1 == 3) {
                    worldController.deleteObject3(j, i, i1);
                    ObjectDef class46_2 = ObjectDef.forID(j2);
                    if (class46_2.aBoolean767 && class46_2.hasActions) {
                        clipMap[j].setValue2(i, i1);
                    }
                }
            }
            if (k1 >= 0) {
                int j3 = j;
                if (j3 < 3 && (byteGroundArray[1][i1][i] & 2) == 2) {
                    j3++;
                }
                ObjectManager.method188(worldController, k, i, l, j3, clipMap[j], intGroundArray, i1, k1, j);
            }
        }
    }

    private void updatePlayers(int targetOffset, Stream stream) {
        anInt839 = 0;
        anInt893 = 0;
        updateLocalPlayerMovement(stream);
        updateOtherPlayerMovement(stream);
        updatePlayerList(stream, targetOffset);
        flagBasedUpdatePlayer(stream);
        
        for (int idx = 0; idx < anInt839; idx++) {
            int playerId = removeFromUpdatingPlayersList[idx];
            
            if (playerArray[playerId].loopCycle != loopCycle) {
                playerArray[playerId] = null;
            }
        }

        if (stream.currentOffset != targetOffset) {
            Signlink.printError("Error packet size mismatch in getplayer pos:" + stream.currentOffset + " psize:" + targetOffset);
            throw new RuntimeException("eek");
        }
        
        for (int idx = 0; idx < playerCount; idx++) {
            if (playerArray[playerIndices[idx]] == null) {
                Signlink.printError(myUsername + " null entry in pl list - pos:" + idx + " size:" + playerCount);
                throw new RuntimeException("eek");
            }
        }
    }

    private void setCameraPos(int j, int cameraCurveY, int x1, int z1, int cameraCurveX, int y1) {
        int l1 = 2048 - cameraCurveY & 0x7ff;
        int i2 = 2048 - cameraCurveX & 0x7ff;
        int x2 = 0;
        int z2 = 0;
        int y2 = j;
        
        if (l1 != 0) {
            int i3 = Model.modelIntArray1[l1];
            int k3 = Model.modelIntArray2[l1];
            int i4 = z2 * k3 - y2 * i3 >> 16;
            y2 = z2 * i3 + y2 * k3 >> 16;
            z2 = i4;
        }
        
        if (i2 != 0) {
            /* XXX: Ignores camera toggle 
            if(cameratoggle){
             if(zoom == 0)
             zoom = k2;
             if(lftrit == 0)
             lftrit = j2;
             if(fwdbwd == 0)
             fwdbwd = l2;
             k2 = zoom;
             j2 = lftrit;
             l2 = fwdbwd;
            }
             */
            int j3 = Model.modelIntArray1[i2];
            int l3 = Model.modelIntArray2[i2];
            int j4 = y2 * j3 + x2 * l3 >> 16;
            y2 = y2 * l3 - x2 * j3 >> 16;
            x2 = j4;
        }
        xCameraPos = x1 - x2;
        zCameraPos = z1 - z2;
        yCameraPos = y1 - y2;
        yCameraCurve = cameraCurveY;
        xCameraCurve = cameraCurveX;
    }

    /**
     * Parses incoming packets.
     * 
     * @return 
     */
    private boolean parsePacket() {
        if (socketStream == null) {
            return false;
        }
        
        try {
            int available = socketStream.available();
            
            if (available == 0) {
                return false;
            }
            
            // Regular length, default handling
            if (packetOpcode == -1) {
                socketStream.flushInputStream(inStream.buffer, 1);
                packetOpcode = inStream.buffer[0] & 0xff;
                
                if (encryption != null) {
                    packetOpcode = packetOpcode - encryption.getNextKey() & 0xff;
                }
                packetLength = PacketConstants.LENGTHS[packetOpcode];
                available--;
            }
            
            // Variable byte length
            if (packetLength == -1) {
                if (available > 0) {
                    socketStream.flushInputStream(inStream.buffer, 1);
                    packetLength = inStream.buffer[0] & 0xff;
                    available--;
                } else {
                    return false;
                }
            }
            
            // Variable short length
            if (packetLength == -2) {
                if (available > 1) {
                    socketStream.flushInputStream(inStream.buffer, 2);
                    inStream.currentOffset = 0;
                    packetLength = inStream.readUShort();
                    available -= 2;
                } else {
                    return false;
                }
            }
            
            if (available < packetLength) {
                return false;
            }
            inStream.currentOffset = 0;
            socketStream.flushInputStream(inStream.buffer, packetLength);
            ticksSincelastPacketReceived = 0;
            anInt843 = anInt842;
            anInt842 = anInt841;
            anInt841 = packetOpcode;

            // Begin player updating
            if (packetOpcode == 81) {
                updatePlayers(packetLength, inStream);
                aBoolean1080 = false;
                packetOpcode = -1;
                return true;
            }
            
            // Open welcome screen
            if (packetOpcode == 176) {
                daysSinceRecoveryChange = inStream.readUByteC();
                unreadMessages = inStream.readUShortA();
                memberWarning = inStream.readUByte();
                lastLoggedIp = inStream.readIntMEB();
                daysSinceLastLogin = inStream.readUShort();
                
                if (lastLoggedIp != 0 && openInterfaceID == -1) {
                    Signlink.dnsLookup(StringHelper.decompressIpAddress(lastLoggedIp));
                    clearTopInterfaces();
                    char c = '\u028A';
                    
                    if (daysSinceRecoveryChange != 201 || memberWarning == 1) {
                        c = '\u028F';
                    }
                    reportAbuseInput = "";
                    canMute = false;
                    
                    for (int i = 0; i < RSInterface.interfaceCache.length; i++) {
                        if (RSInterface.interfaceCache[i] == null || RSInterface.interfaceCache[i].anInt214 != c) {
                            continue;
                        }
                        openInterfaceID = RSInterface.interfaceCache[i].parentID;
                        break;
                    }
                }
                packetOpcode = -1;
                return true;
            }
            
            if (packetOpcode == 64) {
                x1 = inStream.readUByteC();
                y1 = inStream.readUByteS();
                
                for (int x = x1; x < x1 + 8; x++) {
                    for (int y = y1; y < y1 + 8; y++) {
                        if (groundArray[plane][x][y] != null) {
                            groundArray[plane][x][y] = null;
                            spawnGroundItem(x, y);
                        }
                    }
                }

                for (Class30_Sub1 class30_sub1 = (Class30_Sub1) aClass19_1179.reverseGetFirst();
                        class30_sub1 != null;
                        class30_sub1 = (Class30_Sub1) aClass19_1179.reverseGetNext()) {
                    if (class30_sub1.x >= x1
                            && class30_sub1.x < x1 + 8
                            && class30_sub1.y >= y1
                            && class30_sub1.y < y1 + 8
                            && class30_sub1.plane == plane) {
                        class30_sub1.anInt1294 = 0;
                    }
                }
                packetOpcode = -1;
                return true;
            }
            
            // Sends the player head model to an interface
            if (packetOpcode == 185) {
                int interfaceId = inStream.readUShortLEA();
                RSInterface.interfaceCache[interfaceId].anInt233 = 3;
                
                if (myPlayer.def == null) {
                    // Generating the model, if it's not available
                    RSInterface.interfaceCache[interfaceId].mediaId = (myPlayer.anIntArray1700[0] << 25)
                            + (myPlayer.anIntArray1700[4] << 20)
                            + (myPlayer.equipment[0] << 15)
                            + (myPlayer.equipment[8] << 10)
                            + (myPlayer.equipment[11] << 5)
                            + myPlayer.equipment[1];
                } else {
                    RSInterface.interfaceCache[interfaceId].mediaId = (int) (0x12345678L + myPlayer.def.type);
                }
                packetOpcode = -1;
                return true;
            }
            
            // Reset camera position
            if (packetOpcode == 107) {
                aBoolean1160 = false;
                
                for (int l = 0; l < 5; l++) {
                    aBooleanArray876[l] = false;
                }
                packetOpcode = -1;
                return true;
            }
            
            // Clear inventory
            if (packetOpcode == 72) {
                int interfaceId = inStream.readUShortLE();
                RSInterface rsi = RSInterface.interfaceCache[interfaceId];
                
                for (int slot = 0; slot < rsi.inv.length; slot++) {
                    rsi.inv[slot] = -1;
                    rsi.inv[slot] = 0; // XXX: Should this not be invStackSizes ?
                }
                packetOpcode = -1;
                return true;
            }
            
            // Add ignore
            if (packetOpcode == 214) {
                ignoreCount = packetLength / 8;
                
                for (int i = 0; i < ignoreCount; i++) {
                    ignoreListAsLongs[i] = inStream.readULong();
                }
                packetOpcode = -1;
                return true;
            }
            
            if (packetOpcode == 166) {
                aBoolean1160 = true;
                cameraPositionX = inStream.readUByte();
                cameraPositionY = inStream.readUByte();
                cameraPositionZ = inStream.readUShort();
                anInt1101 = inStream.readUByte();
                anInt1102 = inStream.readUByte();
                
                if (anInt1102 >= 100) {
                    xCameraPos = cameraPositionX * 128 + 64;
                    yCameraPos = cameraPositionY * 128 + 64;
                    zCameraPos = method42(plane, yCameraPos, xCameraPos) - cameraPositionZ;
                }
                packetOpcode = -1;
                return true;
            }
            
            // Set skill level
            if (packetOpcode == 134) {
                needDrawTabArea = true;
                int skillId = inStream.readUByte();
                int skillExperience = inStream.readIntMES();
                int skillLevel = inStream.readUByte();
                currentExperience[skillId] = skillExperience;
                currentLevels[skillId] = skillLevel;
                maxStats[skillId] = 1;
                
                for (int i = 0; i < 98; i++) {
                    if (skillExperience >= anIntArray1019[i]) {
                        maxStats[skillId] = i + 2;
                    }
                }
                packetOpcode = -1;
                return true;
            }
            
            // Assign a sidebar to an interface (ie attack tab, magic tab etc.)
            if (packetOpcode == 71) {
                int sidebarId = inStream.readUShort();
                int interfaceId = inStream.readUByteA();
                
                if (sidebarId == 65535) {
                    sidebarId = -1;
                }
                tabInterfaceIDs[interfaceId] = sidebarId;
                needDrawTabArea = true;
                tabAreaAltered = true;
                packetOpcode = -1;
                return true;
            }
            
            // Play song
            if (packetOpcode == 74) {
                int songId = inStream.readUShortLE();
                
                if (songId == 65535) {
                    songId = -1;
                }
                
                if (songId != currentSong && musicEnabled && !lowMem && prevSong == 0) {
                    nextSong = songId;
                    songChanging = true;
                    onDemandFetcher.fetchItem(2, nextSong);
                }
                currentSong = songId;
                packetOpcode = -1;
                return true;
            }
            
            // Queue song
            if (packetOpcode == 121) {
                int nextSongId = inStream.readUShortLEA();
                int prevSongId = inStream.readUShortA();
                
                if (musicEnabled && !lowMem) {
                    nextSong = nextSongId;
                    songChanging = false;
                    onDemandFetcher.fetchItem(2, nextSong);
                    prevSong = prevSongId;
                }
                packetOpcode = -1;
                return true;
            }
            
            // Logout
            if (packetOpcode == 109) {
                resetLogout();
                packetOpcode = -1;
                return false;
            }

            // Interface offset
            if (packetOpcode == 70) {
                int offsetX = inStream.readShort();
                int offsetY = inStream.readShortLE();
                int interfaceId = inStream.readUShortLE();
                RSInterface rsi = RSInterface.interfaceCache[interfaceId];
                rsi.offsetX = offsetX;
                rsi.offsetY = offsetY;
                packetOpcode = -1;
                return true;
            }
            
            // Load/construct map region
            if (packetOpcode == 73 || packetOpcode == 241) {
                int regionX = anInt1069;
                int regionY = anInt1070;
                
                if (packetOpcode == 73) {
                    regionX = inStream.readUShortA();
                    regionY = inStream.readUShort();
                    aBoolean1159 = false;
                }
                
                if (packetOpcode == 241) {
                    regionY = inStream.readUShortA();
                    inStream.initBitAccess();
                    
                    for (int regionsZ = 0; regionsZ < 4; regionsZ++) {
                        for (int regionsX = 0; regionsX < 13; regionsX++) {
                            for (int regionsY = 0; regionsY < 13; regionsY++) {
                                int displayRegion = inStream.readBits(1);

                                if (displayRegion == 1) {
                                    mapRegionPalette[regionsZ][regionsX][regionsY] = inStream.readBits(26);
                                } else {
                                    mapRegionPalette[regionsZ][regionsX][regionsY] = -1;
                                }
                            }
                        }
                    }
                    inStream.finishBitAccess();
                    regionX = inStream.readUShort();
                    aBoolean1159 = true;
                }
                
                if (anInt1069 == regionX && anInt1070 == regionY && loadingStage == 2) {
                    packetOpcode = -1;
                    return true;
                }
                anInt1069 = regionX;
                anInt1070 = regionY;
                baseX = (anInt1069 - 6) * 8;
                baseY = (anInt1070 - 6) * 8;
                aBoolean1141 = (anInt1069 / 8 == 48 || anInt1069 / 8 == 49) && anInt1070 / 8 == 48;
                
                if (anInt1069 / 8 == 48 && anInt1070 / 8 == 148) {
                    aBoolean1141 = true;
                }
                loadingStage = 1;
                aLong824 = System.currentTimeMillis();
                aRSImageProducer_1165.initDrawingArea();
                loadingTextArea.drawText(0, "Loading - please wait.", 151, 257);
                loadingTextArea.drawText(0xffffff, "Loading - please wait.", 150, 256);
                aRSImageProducer_1165.drawGraphics(super.graphics, 4, 4);
                
                if (packetOpcode == 73) {
                    int k16 = 0;
                    
                    for (int i21 = (anInt1069 - 6) / 8; i21 <= (anInt1069 + 6) / 8; i21++) {
                        for (int k23 = (anInt1070 - 6) / 8; k23 <= (anInt1070 + 6) / 8; k23++) {
                            k16++;
                        }
                    }
                    aByteArrayArray1183 = new byte[k16][];
                    aByteArrayArray1247 = new byte[k16][];
                    anIntArray1234 = new int[k16];
                    anIntArray1235 = new int[k16];
                    anIntArray1236 = new int[k16];
                    k16 = 0;
                    
                    for (int l23 = (anInt1069 - 6) / 8; l23 <= (anInt1069 + 6) / 8; l23++) {
                        for (int j26 = (anInt1070 - 6) / 8; j26 <= (anInt1070 + 6) / 8; j26++) {
                            anIntArray1234[k16] = (l23 << 8) + j26;
                            
                            if (aBoolean1141 && (j26 == 49 || j26 == 149 || j26 == 147 || l23 == 50 || l23 == 49 && j26 == 47)) {
                                anIntArray1235[k16] = -1;
                                anIntArray1236[k16] = -1;
                                k16++;
                            } else {
                                int itemId1 = anIntArray1235[k16] = onDemandFetcher.method562(0, j26, l23);

                                if (itemId1 != -1) {
                                    onDemandFetcher.fetchItem(3, itemId1);
                                }
                                int itemId2 = anIntArray1236[k16] = onDemandFetcher.method562(1, j26, l23);

                                if (itemId2 != -1) {
                                    onDemandFetcher.fetchItem(3, itemId2);
                                }
                                k16++;
                            }
                        }
                    }
                }
                
                // Construct map region
                if (packetOpcode == 241) {
                    int l16 = 0;
                    int ai[] = new int[676];
                    
                    for (int regionsZ = 0; regionsZ < 4; regionsZ++) {
                        for (int regionsX = 0; regionsX < 13; regionsX++) {
                            for (int regionsY = 0; regionsY < 13; regionsY++) {
                                int data = mapRegionPalette[regionsZ][regionsX][regionsY];
                                
                                if (data != -1) {
                                    int k31 = data >> 14 & 0x3ff;
                                    int i32 = data >> 3 & 0x7ff;
                                    int k32 = (k31 / 8 << 8) + i32 / 8;
                                    
                                    for (int j33 = 0; j33 < l16; j33++) {
                                        if (ai[j33] != k32) {
                                            continue;
                                        }
                                        k32 = -1;
                                        break;
                                    }

                                    if (k32 != -1) {
                                        ai[l16++] = k32;
                                    }
                                }
                            }
                        }
                    }
                    aByteArrayArray1183 = new byte[l16][];
                    aByteArrayArray1247 = new byte[l16][];
                    anIntArray1234 = new int[l16];
                    anIntArray1235 = new int[l16];
                    anIntArray1236 = new int[l16];
                    
                    for (int l26 = 0; l26 < l16; l26++) {
                        int i29 = anIntArray1234[l26] = ai[l26];
                        int l30 = i29 >> 8 & 0xff;
                        int l31 = i29 & 0xff;
                        int itemId1 = anIntArray1235[l26] = onDemandFetcher.method562(0, l31, l30);
                        
                        if (itemId1 != -1) {
                            onDemandFetcher.fetchItem(3, itemId1);
                        }
                        int itemId2 = anIntArray1236[l26] = onDemandFetcher.method562(1, l31, l30);
                        
                        if (itemId2 != -1) {
                            onDemandFetcher.fetchItem(3, itemId2);
                        }
                    }
                }
                int i17 = baseX - anInt1036;
                int j21 = baseY - anInt1037;
                anInt1036 = baseX;
                anInt1037 = baseY;
                
                for (int npcId = 0; npcId < 16384; npcId++) {
                    NPC npc = npcArray[npcId];
                    
                    if (npc != null) {
                        for (int idx = 0; idx < 10; idx++) {
                            npc.smallX[idx] -= i17;
                            npc.smallY[idx] -= j21;
                        }
                        npc.x -= i17 * 128;
                        npc.y -= j21 * 128;
                    }
                }

                for (int playerId = 0; playerId < maxPlayers; playerId++) {
                    Player player = playerArray[playerId];
                    
                    if (player != null) {
                        for (int idx = 0; idx < 10; idx++) {
                            player.smallX[idx] -= i17;
                            player.smallY[idx] -= j21;
                        }
                        player.x -= i17 * 128;
                        player.y -= j21 * 128;
                    }
                }
                aBoolean1080 = true;
                byte byte1 = 0;
                byte byte2 = 104;
                byte byte3 = 1;
                
                if (i17 < 0) {
                    byte1 = 103;
                    byte2 = -1;
                    byte3 = -1;
                }
                byte byte4 = 0;
                byte byte5 = 104;
                byte byte6 = 1;
                
                if (j21 < 0) {
                    byte4 = 103;
                    byte5 = -1;
                    byte6 = -1;
                }

                for (int x = byte1; x != byte2; x += byte3) {
                    for (int y = byte4; y != byte5; y += byte6) {
                        int x1 = x + i17;
                        int y1 = y + j21;
                        
                        for (int z = 0; z < 4; z++) {
                            if (x1 >= 0 && y1 >= 0 && x1 < 104 && y1 < 104) {
                                groundArray[z][x][y] = groundArray[z][x1][y1];
                            } else {
                                groundArray[z][x][y] = null;
                            }
                        }
                    }
                }

                for (Class30_Sub1 class30_sub1_1 = (Class30_Sub1) aClass19_1179.reverseGetFirst();
                        class30_sub1_1 != null;
                        class30_sub1_1 = (Class30_Sub1) aClass19_1179.reverseGetNext()) {
                    class30_sub1_1.x -= i17;
                    class30_sub1_1.y -= j21;
                    
                    if (class30_sub1_1.x < 0 || class30_sub1_1.y < 0 || class30_sub1_1.x >= 104 || class30_sub1_1.y >= 104) {
                        class30_sub1_1.unlink();
                    }
                }

                if (destX != 0) {
                    destX -= i17;
                    destY -= j21;
                }
                aBoolean1160 = false;
                packetOpcode = -1;
                return true;
            }
            
            // Show walkable interface
            if (packetOpcode == 208) {
                int interfaceId = inStream.readShortLE();
                
                if (interfaceId >= 0) {
                    showWalkableInterface(interfaceId);
                }
                currentInterfaceId = interfaceId;
                packetOpcode = -1;
                return true;
            }
            
            // Minimap state
            if (packetOpcode == 99) {
                minimapState = inStream.readUByte();
                packetOpcode = -1;
                return true;
            }

            // Npc face on interface
            if (packetOpcode == 75) {
                int mediaId = inStream.readUShortLEA();
                int interfaceId = inStream.readUShortLEA();
                RSInterface.interfaceCache[interfaceId].anInt233 = 2;
                RSInterface.interfaceCache[interfaceId].mediaId = mediaId;
                packetOpcode = -1;
                return true;
            }
            
            // System update (time until)
            if (packetOpcode == 114) {
                timeUntilSystemUpdate = inStream.readUShortLE() * 30;
                packetOpcode = -1;
                return true;
            }

            // Handle ground items
            if (packetOpcode == 60) {
                y1 = inStream.readUByte();
                x1 = inStream.readUByteC();
                
                while (inStream.currentOffset < packetLength) {
                    int length = inStream.readUByte();
                    parseGroundItemPacket(inStream, length);
                }
                packetOpcode = -1;
                return true;
            }
            
            // Camera shake
            if (packetOpcode == 35) {
                int parameter = inStream.readUByte();
                int jitter = inStream.readUByte();
                int amplitude = inStream.readUByte();
                int frequency = inStream.readUByte();
                aBooleanArray876[parameter] = true;
                anIntArray873[parameter] = jitter;
                anIntArray1203[parameter] = amplitude;
                anIntArray928[parameter] = frequency;
                anIntArray1030[parameter] = 0;
                packetOpcode = -1;
                return true;
            }

            // Audio
            if (packetOpcode == 174) {
                int soundId = inStream.readUShort();
                int volume = inStream.readUByte();
                int delay = inStream.readUShort();
                
                if (aBoolean848 && !lowMem && soundSlot < 50) {
                    soundIds[soundSlot] = soundId;
                    soundVolume[soundSlot] = volume;
                    soundFinishTime[soundSlot] = delay + Sound.durations[soundId];
                    soundSlot++;
                }
                packetOpcode = -1;
                return true;
            }
            
            // Add player right-click option
            if (packetOpcode == 104) {
                int optionPosition = inStream.readUByteC();
                int flag = inStream.readUByteA();
                String action = inStream.readString();
                
                if (optionPosition >= 1 && optionPosition <= 5) {
                    if (action.equalsIgnoreCase("null")) {
                        action = null;
                    }
                    atPlayerActions[optionPosition - 1] = action;
                    atPlayerArray[optionPosition - 1] = flag == 0;
                }
                packetOpcode = -1;
                return true;
            }
            
            // Reset destination (and stop walking)
            if (packetOpcode == 78) {
                destX = 0;
                packetOpcode = -1;
                return true;
            }
            
            // Send Message
            if (packetOpcode == 253) {
                String msg = inStream.readString();
                
                if (msg.endsWith(":tradereq:")) {
                    String s3 = msg.substring(0, msg.indexOf(":"));
                    long name = StringHelper.longForName(s3);
                    boolean flag2 = false;
                    
                    for (int i = 0; i < ignoreCount; i++) {
                        if (ignoreListAsLongs[i] != name) {
                            continue;
                        }
                        flag2 = true;
                        break;
                    }

                    if (!flag2 && anInt1251 == 0) {
                        pushMessage("wishes to trade with you.", 4, s3);
                    }
                } else if (msg.endsWith(":duelreq:")) {
                    String s4 = msg.substring(0, msg.indexOf(":"));
                    long l18 = StringHelper.longForName(s4);
                    boolean flag3 = false;
                    
                    for (int i = 0; i < ignoreCount; i++) {
                        if (ignoreListAsLongs[i] != l18) {
                            continue;
                        }
                        flag3 = true;
                        break;
                    }

                    if (!flag3 && anInt1251 == 0) {
                        pushMessage("wishes to duel with you.", 8, s4);
                    }
                } else if (msg.endsWith(":chalreq:")) {
                    String s5 = msg.substring(0, msg.indexOf(":"));
                    long l19 = StringHelper.longForName(s5);
                    boolean flag4 = false;
                    
                    for (int i = 0; i < ignoreCount; i++) {
                        if (ignoreListAsLongs[i] != l19) {
                            continue;
                        }
                        flag4 = true;
                        break;
                    }

                    if (!flag4 && anInt1251 == 0) {
                        String s8 = msg.substring(msg.indexOf(":") + 1, msg.length() - 9);
                        pushMessage(s8, 8, s5);
                    }
                } else {
                    pushMessage(msg, 0, "");
                }
                packetOpcode = -1;
                //serverMessage(s);
                return true;
            }
            
            // Animation reset
            if (packetOpcode == 1) {
                for (Player plyr : playerArray) {
                    if (plyr != null) {
                        plyr.currentAnimationId = -1;
                    }
                }

                for (NPC npc : npcArray) {
                    if (npc != null) {
                        npc.currentAnimationId = -1;
                    }
                }
                packetOpcode = -1;
                return true;
            }
            
            // Send [add] friend [data]
            if (packetOpcode == 50) {
                long name = inStream.readULong();
                int nodeId = inStream.readUByte();
                String friendName = StringHelper.fixName(StringHelper.nameForLong(name));
                
                for (int i = 0; i < friendsCount; i++) {
                    if (name != friendsListAsLongs[i]) {
                        continue;
                    }
                    
                    if (friendsNodeIDs[i] != nodeId) {
                        friendsNodeIDs[i] = nodeId;
                        needDrawTabArea = true;
                        
                        if (nodeId > 0) {
                            pushMessage(friendName + " has logged in.", 5, "");
                        }
                        
                        if (nodeId == 0) {
                            pushMessage(friendName + " has logged out.", 5, "");
                        }
                    }
                    friendName = null;
                    break;
                }

                if (friendName != null && friendsCount < 200) {
                    friendsListAsLongs[friendsCount] = name;
                    friendsList[friendsCount] = friendName;
                    friendsNodeIDs[friendsCount] = nodeId;
                    friendsCount++;
                    needDrawTabArea = true;
                }
                
                for (boolean flag6 = false; !flag6;) {
                    flag6 = true;
                    
                    for (int i = 0; i < friendsCount - 1; i++) {
                        if (friendsNodeIDs[i] != nodeId && friendsNodeIDs[i + 1] == nodeId
                                || friendsNodeIDs[i] == 0 && friendsNodeIDs[i + 1] != 0) {
                            int j31 = friendsNodeIDs[i];
                            friendsNodeIDs[i] = friendsNodeIDs[i + 1];
                            friendsNodeIDs[i + 1] = j31;
                            String s10 = friendsList[i];
                            friendsList[i] = friendsList[i + 1];
                            friendsList[i + 1] = s10;
                            long l32 = friendsListAsLongs[i];
                            friendsListAsLongs[i] = friendsListAsLongs[i + 1];
                            friendsListAsLongs[i + 1] = l32;
                            needDrawTabArea = true;
                            flag6 = false;
                        }
                    }
                }
                packetOpcode = -1;
                return true;
            }
            
            // Run energy
            if (packetOpcode == 110) {
                if (tabID == 12) {
                    needDrawTabArea = true;
                }
                runEnergy = inStream.readUByte();
                packetOpcode = -1;
                return true;
            }

            // Display hint icon
            if (packetOpcode == 254) {
                iconType = inStream.readUByte();

                // Npc icon
                if (iconType == 1) {
                    iconTargetNpcId = inStream.readUShort();
                }

                // Coordinate icon
                if (iconType >= 2 && iconType <= 6) {
                    if (iconType == 2) {
                        anInt937 = 64;
                        anInt938 = 64;
                    }
                    
                    if (iconType == 3) {
                        anInt937 = 0;
                        anInt938 = 64;
                    }
                    
                    if (iconType == 4) {
                        anInt937 = 128;
                        anInt938 = 64;
                    }
                    
                    if (iconType == 5) {
                        anInt937 = 64;
                        anInt938 = 0;
                    }
                    
                    if (iconType == 6) {
                        anInt937 = 64;
                        anInt938 = 128;
                    }
                    iconType = 2;
                    iconX = inStream.readUShort();
                    iconY = inStream.readUShort();
                    iconDrawHeight = inStream.readUByte();
                }

                // Player icon
                if (iconType == 10) {
                    iconPlayerTargetId = inStream.readUShort();
                }
                packetOpcode = -1;
                return true;
            }

            // Inventory overlay
            if (packetOpcode == 248) {
                int interfaceId = inStream.readUShortA();
                int inentoryOverlayInterfaceId = inStream.readUShort();
                
                if (backDialogID != -1) {
                    backDialogID = -1;
                    inputTaken = true;
                }
                
                if (inputDialogState != 0) {
                    inputDialogState = 0;
                    inputTaken = true;
                }
                openInterfaceID = interfaceId;
                invOverlayInterfaceID = inentoryOverlayInterfaceId;
                needDrawTabArea = true;
                tabAreaAltered = true;
                aBoolean1149 = false;
                packetOpcode = -1;
                return true;
            }

            // Scroll position
            if (packetOpcode == 79) {
                int interfaceId = inStream.readUShortLE();
                int scrollPosition = inStream.readUShortA();
                RSInterface rsi = RSInterface.interfaceCache[interfaceId];
                
                if (rsi != null && rsi.type == 0) {
                    if (scrollPosition < 0) {
                        scrollPosition = 0;
                    }
                    
                    if (scrollPosition > rsi.maximumScrollPosition - rsi.height) {
                        scrollPosition = rsi.maximumScrollPosition - rsi.height;
                    }
                    rsi.scrollPosition = scrollPosition;
                }
                packetOpcode = -1;
                return true;
            }
            
            // Reset button state
            if (packetOpcode == 68) {
                for (int i = 0; i < currentUserSetting.length; i++) {
                    if (currentUserSetting[i] != defaultUserSetting[i]) {
                        currentUserSetting[i] = defaultUserSetting[i];
                        handleSettingId(i);
                        needDrawTabArea = true;
                    }
                }
                packetOpcode = -1;
                return true;
            }
            
            // Send private message
            if (packetOpcode == 196) {
                long name = inStream.readULong();
                int msgCounter = inStream.readUInt();
                int rights = inStream.readUByte();
                boolean flag5 = false;
                
                for (int i = 0; i < 100; i++) {
                    if (anIntArray1240[i] != msgCounter) {
                        continue;
                    }
                    flag5 = true;
                    break;
                }

                if (rights <= 1) {
                    for (int i = 0; i < ignoreCount; i++) {
                        if (ignoreListAsLongs[i] != name) {
                            continue;
                        }
                        flag5 = true;
                        break;
                    }
                }
                
                if (!flag5 && anInt1251 == 0) {
                    try {
                        anIntArray1240[anInt1169] = msgCounter;
                        anInt1169 = (anInt1169 + 1) % 100;
                        String message = TextInput.constructInput(packetLength - 13, inStream);
                        
                        if (rights != 3) {
                            message = Censor.apply(message);
                        }
                        
                        if (rights == 2 || rights == 3) {
                            pushMessage(message, 7, "@cr2@" + StringHelper.fixName(StringHelper.nameForLong(name)));
                        } else if (rights == 1) {
                            pushMessage(message, 7, "@cr1@" + StringHelper.fixName(StringHelper.nameForLong(name)));
                        } else {
                            pushMessage(message, 3, StringHelper.fixName(StringHelper.nameForLong(name)));
                        }
                    } catch (Exception ex) {
                        Signlink.printError("cde1");
                    }
                }
                packetOpcode = -1;
                return true;
            }
            
            if (packetOpcode == 85) {
                y1 = inStream.readUByteC();
                x1 = inStream.readUByteC();
                packetOpcode = -1;
                return true;
            }
            
            // Flash side-bar
            if (packetOpcode == 24) {
                flashSidebarId = inStream.readUByteS();
                
                if (flashSidebarId == tabID) {
                    if (flashSidebarId == 3) {
                        tabID = 1;
                    } else {
                        tabID = 3;
                    }
                    needDrawTabArea = true;
                }
                packetOpcode = -1;
                return true;
            }
            
            // Interface item
            if (packetOpcode == 246) {
                int interfaceId = inStream.readUShortLE();
                int zoomFactor = inStream.readUShort();
                int itemId = inStream.readUShort();
                
                if (itemId == 65535) {
                    RSInterface.interfaceCache[interfaceId].anInt233 = 0;
                    packetOpcode = -1;
                    return true;
                } else {
                    ItemDef itemDef = ItemDef.forID(itemId);
                    RSInterface.interfaceCache[interfaceId].anInt233 = 4;
                    RSInterface.interfaceCache[interfaceId].mediaId = itemId;
                    RSInterface.interfaceCache[interfaceId].modelRotation1 = itemDef.modelRotation1;
                    RSInterface.interfaceCache[interfaceId].anInt271 = itemDef.modelRotation2;
                    RSInterface.interfaceCache[interfaceId].zoomFactor = (itemDef.modelZoom * 100) / zoomFactor;
                    packetOpcode = -1;
                    return true;
                }
            }
            
            if (packetOpcode == 171) {
                boolean flag = inStream.readUByte() == 1;
                int interfaceId = inStream.readUShort();
                RSInterface.interfaceCache[interfaceId].aBoolean266 = flag;
                packetOpcode = -1;
                return true;
            }
            
            if (packetOpcode == 142) {
                int interfaceId = inStream.readUShortLE();
                showWalkableInterface(interfaceId);
                
                if (backDialogID != -1) {
                    backDialogID = -1;
                    inputTaken = true;
                }
                
                if (inputDialogState != 0) {
                    inputDialogState = 0;
                    inputTaken = true;
                }
                invOverlayInterfaceID = interfaceId;
                needDrawTabArea = true;
                tabAreaAltered = true;
                openInterfaceID = -1;
                aBoolean1149 = false;
                packetOpcode = -1;
                return true;
            }
            
            // Set interface text
            if (packetOpcode == 126) {
                String message = inStream.readString();
                int interfaceId = inStream.readUShortA();
                RSInterface.interfaceCache[interfaceId].message = message;
                
                if (RSInterface.interfaceCache[interfaceId].parentID == tabInterfaceIDs[tabID]) {
                    needDrawTabArea = true;
                }
                packetOpcode = -1;
                return true;
            }
            
            // Chat settings
            if (packetOpcode == 206) {
                publicChatMode = inStream.readUByte();
                privateChatMode = inStream.readUByte();
                tradeMode = inStream.readUByte();
                aBoolean1233 = true;
                inputTaken = true;
                packetOpcode = -1;
                return true;
            }
            
            // Set weight
            if (packetOpcode == 240) {
                if (tabID == 12) {
                    needDrawTabArea = true;
                }
                weight = inStream.readShort();
                packetOpcode = -1;
                return true;
            }
            
            if (packetOpcode == 8) {
                int interfaceId = inStream.readUShortLEA();
                int mediaId = inStream.readUShort();
                RSInterface.interfaceCache[interfaceId].anInt233 = 1;
                RSInterface.interfaceCache[interfaceId].mediaId = mediaId;
                packetOpcode = -1;
                return true;
            }
            
            // Set interface text colour
            if (packetOpcode == 122) {
                int interfaceId = inStream.readUShortLEA();
                int textColour = inStream.readUShortLEA();
                int i19 = textColour >> 10 & 0x1f;
                int i22 = textColour >> 5 & 0x1f;
                int l24 = textColour & 0x1f;
                RSInterface.interfaceCache[interfaceId].textColor = (i19 << 19) + (i22 << 11) + (l24 << 3);
                packetOpcode = -1;
                return true;
            }
            
            // Update item container
            if (packetOpcode == 53) {
                needDrawTabArea = true;
                int interfaceId = inStream.readUShort();
                RSInterface rsi = RSInterface.interfaceCache[interfaceId];
                int length = inStream.readUShort();
                
                for (int i = 0; i < length; i++) {
                    int amount = inStream.readUByte();
                    
                    if (amount == 255) {
                        amount = inStream.readIntMEB();
                    }
                    rsi.inv[i] = inStream.readUShortLEA();
                    rsi.invStackSizes[i] = amount;
                }

                for (int i = length; i < rsi.inv.length; i++) {
                    rsi.inv[i] = 0;
                    rsi.invStackSizes[i] = 0;
                }
                packetOpcode = -1;
                return true;
            }
            
            
            if (packetOpcode == 230) {
                int zoomFactor = inStream.readUShortA();
                int interfaceId = inStream.readUShort();
                int modelRotation1 = inStream.readUShort();
                int k22 = inStream.readUShortLEA();
                
                RSInterface.interfaceCache[interfaceId].modelRotation1 = modelRotation1;
                RSInterface.interfaceCache[interfaceId].anInt271 = k22;
                RSInterface.interfaceCache[interfaceId].zoomFactor = zoomFactor;
                packetOpcode = -1;
                return true;
            }
            
            // Friends list load status
            if (packetOpcode == 221) {
                friendsListLoadStatus = inStream.readUByte();
                needDrawTabArea = true;
                packetOpcode = -1;
                return true;
            }
            
            if (packetOpcode == 177) {
                aBoolean1160 = true;
                anInt995 = inStream.readUByte();
                anInt996 = inStream.readUByte();
                anInt997 = inStream.readUShort();
                anInt998 = inStream.readUByte();
                anInt999 = inStream.readUByte();
                
                if (anInt999 >= 100) {
                    int k7 = anInt995 * 128 + 64;
                    int k14 = anInt996 * 128 + 64;
                    int i20 = method42(plane, k14, k7) - anInt997;
                    int l22 = k7 - xCameraPos;
                    int k25 = i20 - zCameraPos;
                    int j28 = k14 - yCameraPos;
                    int i30 = (int) Math.sqrt(l22 * l22 + j28 * j28);
                    yCameraCurve = (int) (Math.atan2(k25, i30) * 325.94900000000001D) & 0x7ff;
                    xCameraCurve = (int) (Math.atan2(l22, j28) * -325.94900000000001D) & 0x7ff;
                    
                    if (yCameraCurve < 128) {
                        yCameraCurve = 128;
                    }
                    
                    if (yCameraCurve > 383) {
                        yCameraCurve = 383;
                    }
                }
                packetOpcode = -1;
                return true;
            }
            
            // Initialize player
            if (packetOpcode == 249) {
                membershipFlag = inStream.readUByteA();
                playerListIndex = inStream.readUShortLEA();
                packetOpcode = -1;
                return true;
            }
            
            if (packetOpcode == 65) {
                updateNPCs(inStream, packetLength);
                packetOpcode = -1;
                return true;
            }
            
            // Show input amount prompt
            if (packetOpcode == 27) {
                messagePromptRaised = false;
                inputDialogState = 1;
                amountOrNameInput = "";
                inputTaken = true;
                packetOpcode = -1;
                return true;
            }
            
            // Show enter name prompt
            if (packetOpcode == 187) {
                messagePromptRaised = false;
                inputDialogState = 2;
                amountOrNameInput = "";
                inputTaken = true;
                packetOpcode = -1;
                return true;
            }
            
            // Show interface
            if (packetOpcode == 97) {
                int interfaceId = inStream.readUShort();
                showWalkableInterface(interfaceId);
                
                if (invOverlayInterfaceID != -1) {
                    invOverlayInterfaceID = -1;
                    needDrawTabArea = true;
                    tabAreaAltered = true;
                }
                
                if (backDialogID != -1) {
                    backDialogID = -1;
                    inputTaken = true;
                }
                
                if (inputDialogState != 0) {
                    inputDialogState = 0;
                    inputTaken = true;
                }
                openInterfaceID = interfaceId;
                aBoolean1149 = false;
                packetOpcode = -1;
                return true;
            }
            
            // Open chatbox interface
            if (packetOpcode == 218) {
                int interfaceId = inStream.readShortLEA();
                dialogID = interfaceId;
                inputTaken = true;
                packetOpcode = -1;
                return true;
            }
            
            // Set default button state id
            if (packetOpcode == 87) {
                int settingId = inStream.readUShortLE();
                int defaultValue = inStream.readIntMES();
                defaultUserSetting[settingId] = defaultValue;
                
                if (currentUserSetting[settingId] != defaultValue) {
                    currentUserSetting[settingId] = defaultValue;
                    handleSettingId(settingId);
                    needDrawTabArea = true;
                    
                    if (dialogID != -1) {
                        inputTaken = true;
                    }
                }
                packetOpcode = -1;
                return true;
            }
            
            // Set default button state id
            if (packetOpcode == 36) {
                int settingId = inStream.readUShortLE();
                byte value = inStream.readByte();
                defaultUserSetting[settingId] = value;
                
                if (currentUserSetting[settingId] != value) {
                    currentUserSetting[settingId] = value;
                    handleSettingId(settingId);
                    needDrawTabArea = true;
                    
                    if (dialogID != -1) {
                        inputTaken = true;
                    }
                }
                packetOpcode = -1;
                return true;
            }
            
            // Show multi-combat flag
            if (packetOpcode == 61) {
                multiCombatFlag = inStream.readUByte();
                packetOpcode = -1;
                return true;
            }
            
            // Interface animation
            if (packetOpcode == 200) {
                int interfaceId = inStream.readUShort();
                int animationId = inStream.readShort();
                RSInterface rsi = RSInterface.interfaceCache[interfaceId];
                rsi.animationId = animationId;
                
                if (animationId == -1) {
                    rsi.anInt246 = 0;
                    rsi.anInt208 = 0;
                }
                packetOpcode = -1;
                return true;
            }
            
            // Clear screen
            if (packetOpcode == 219) {
                if (invOverlayInterfaceID != -1) {
                    invOverlayInterfaceID = -1;
                    needDrawTabArea = true;
                    tabAreaAltered = true;
                }
                
                if (backDialogID != -1) {
                    backDialogID = -1;
                    inputTaken = true;
                }
                
                if (inputDialogState != 0) {
                    inputDialogState = 0;
                    inputTaken = true;
                }
                openInterfaceID = -1;
                aBoolean1149 = false;
                packetOpcode = -1;
                return true;
            }
            
            if (packetOpcode == 34) {
                needDrawTabArea = true;
                int interfaceId = inStream.readUShort();
                RSInterface rsi = RSInterface.interfaceCache[interfaceId];
                
                while (inStream.currentOffset < packetLength) {
                    int invSlot = inStream.readSmarts();
                    int itemId = inStream.readUShort();
                    int itemAmount = inStream.readUByte();
                    
                    if (itemAmount == 255) {
                        itemAmount = inStream.readUInt();
                    }
                    
                    if (invSlot >= 0 && invSlot < rsi.inv.length) {
                        rsi.inv[invSlot] = itemId;
                        rsi.invStackSizes[invSlot] = itemAmount;
                    }
                }
                packetOpcode = -1;
                return true;
            }
            
            if (packetOpcode == 105 || packetOpcode == 84 || packetOpcode == 147
                    || packetOpcode == 215 || packetOpcode == 4
                    || packetOpcode == 117 || packetOpcode == 156
                    || packetOpcode == 44 || packetOpcode == 160
                    || packetOpcode == 101 || packetOpcode == 151) {
                parseGroundItemPacket(inStream, packetOpcode);
                packetOpcode = -1;
                return true;
            }
            
            if (packetOpcode == 106) {
                tabID = inStream.readUByteC();
                needDrawTabArea = true;
                tabAreaAltered = true;
                packetOpcode = -1;
                return true;
            }
            
            // Chat interface
            if (packetOpcode == 164) {
                int interfaceId = inStream.readUShortLE();
                showWalkableInterface(interfaceId);
                
                if (invOverlayInterfaceID != -1) {
                    invOverlayInterfaceID = -1;
                    needDrawTabArea = true;
                    tabAreaAltered = true;
                }
                backDialogID = interfaceId;
                inputTaken = true;
                openInterfaceID = -1;
                aBoolean1149 = false;
                packetOpcode = -1;
                return true;
            }
            Signlink.printError("T1 - opcode:" + packetOpcode + ", len:" + packetLength + " - " + anInt842 + "," + anInt843);
            resetLogout();
        } catch (IOException _ex) {
            connectionLost();
        } catch (Exception ex) {
            String errorMsg = "T2 - opcode:" + packetOpcode + "," + anInt842 + "," + anInt843
                    + " - len:" + packetLength + "," + (baseX + myPlayer.smallX[0])
                    + "," + (baseY + myPlayer.smallY[0]) + " - ";
            
            for (int i = 0; i < packetLength && i < 50; i++) {
                errorMsg = errorMsg + inStream.buffer[i] + ",";
            }
            Signlink.printError(errorMsg);
            resetLogout();
        }
        return true;
    }

    private void method146() {
        anInt1265++;
        method47(true);
        method26(true);
        method47(false);
        method26(false);
        method55();
        method104();
        
        if (!aBoolean1160) {
            int i = anInt1184;
            
            if (anInt984 / 256 > i) {
                i = anInt984 / 256;
            }
            
            if (aBooleanArray876[4] && anIntArray1203[4] + 128 > i) {
                i = anIntArray1203[4] + 128;
            }
            int k = minimapInt1 + anInt896 & 0x7ff;
            setCameraPos(600 + i * 3, i, anInt1014, method42(plane, myPlayer.y, myPlayer.x) - 50, k, anInt1015);
        }
        int j;
        
        if (!aBoolean1160) {
            j = method120();
        } else {
            j = method121();
        }
        int tmpCameraPosX = xCameraPos;
        int tmpCameraPosZ = zCameraPos;
        int tmpCameraPosY = yCameraPos;
        int tmpCameraCurveY = yCameraCurve;
        int tmpCameraCurveX = xCameraCurve;
        
        for (int i2 = 0; i2 < 5; i2++) {
            if (aBooleanArray876[i2]) {
                int tmpOffset = (int) ((Math.random() * (double) (anIntArray873[i2] * 2 + 1) - (double) anIntArray873[i2]) + Math.sin((double) anIntArray1030[i2] * ((double) anIntArray928[i2] / 100D)) * (double) anIntArray1203[i2]);
                
                if (i2 == 0) {
                    xCameraPos += tmpOffset;
                }
                
                if (i2 == 1) {
                    zCameraPos += tmpOffset;
                }
                
                if (i2 == 2) {
                    yCameraPos += tmpOffset;
                }
                
                if (i2 == 3) {
                    xCameraCurve = xCameraCurve + tmpOffset & 0x7ff;
                }
                
                if (i2 == 4) {
                    yCameraCurve += tmpOffset;
                    
                    if (yCameraCurve < 128) {
                        yCameraCurve = 128;
                    }
                    
                    if (yCameraCurve > 383) {
                        yCameraCurve = 383;
                    }
                }
            }
        }
        int k2 = Texture.anInt1481;
        Model.aBoolean1684 = true;
        Model.anInt1687 = 0;
        Model.anInt1685 = super.mouseX - 4;
        Model.anInt1686 = super.mouseY - 4;
        DrawingArea.clear();
        // XXX: disables graphics            if(graphicsEnabled){
        worldController.method313(xCameraPos, yCameraPos, xCameraCurve, zCameraPos, j, yCameraCurve);
        worldController.clearObj5Cache();
        updateEntities();
        drawHeadIcon();
        method37(k2);
        draw3dScreen();
        aRSImageProducer_1165.drawGraphics(super.graphics, 4, 4);
        xCameraPos = tmpCameraPosX;
        zCameraPos = tmpCameraPosZ;
        yCameraPos = tmpCameraPosY;
        yCameraCurve = tmpCameraCurveY;
        xCameraCurve = tmpCameraCurveX;
        //       }
    }

    private void clearTopInterfaces() {
        stream.writePacketHeaderEnc(130);

        if (invOverlayInterfaceID != -1) {
            invOverlayInterfaceID = -1;
            needDrawTabArea = true;
            aBoolean1149 = false;
            tabAreaAltered = true;
        }

        if (backDialogID != -1) {
            backDialogID = -1;
            inputTaken = true;
            aBoolean1149 = false;
        }
        openInterfaceID = -1;
    }
}