package com.bensoft.utils;

/**
 * Created by user on 12/29/2014.
 */
import com.bensoft.entities.OtherCrops;
import com.bensoft.entities.FoliarFeed;
import com.bensoft.entities.Herbicides;
import com.bensoft.entities.FarmOtherCrops;
import com.bensoft.entities.FarmAssFormTypes;
import com.bensoft.entities.FarmAssFormsMajor;
import com.bensoft.entities.FarmerTime;
import com.bensoft.entities.TrainingType;
import com.bensoft.entities.SignedDoc;
import com.bensoft.entities.InputsFromServer;
import com.bensoft.entities.KmlMetaData;
import com.bensoft.entities.Countries;
import com.bensoft.entities.Farmers;
import com.bensoft.entities.Companies;
import com.bensoft.entities.Farm;
import com.bensoft.entities.Villages;
import com.bensoft.entities.Wards;
import com.bensoft.entities.SubVillages;
import com.bensoft.entities.Training2;
import com.bensoft.entities.Training;
import com.bensoft.entities.Regions;
import com.bensoft.entities.Users;
import com.bensoft.entities.Districts;
import com.bensoft.entities.Assigns;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.bensoft.entities.FarmAssFormsMedium;
import com.bensoft.entities.PlantingRains;

import com.bensoft.forms.FingerFiveBack;
import com.bensoft.forms.FingerFourBack;
import com.bensoft.forms.FingerOneBack;
import com.bensoft.forms.FingerThreeBack;
import com.bensoft.forms.FingerTwoBack;
import java.io.UnsupportedEncodingException;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "farmManager";

    // table names
    private static final String TABLE_FARMERS = "farmers";
    private static final String TABLE_FARMINPUTS = "farminputs";
    private static final String TABLE_ASSIGNED_INPUTS = "assigned_inputs";

    // Farmers Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_ID_NO = "id_no";
    private static final String KEY_FNAME = "fname";
    private static final String KEY_LNAME = "lname";
    private static final String KEY_GENDER = "gender";
    private static final String KEY_PH_NO = "phone_number";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_POST = "post";
    private static final String KEY_COUNTRY = "country";
    private static final String KEY_REGION = "region";
    private static final String KEY_DISTRICT = "district";
    private static final String KEY_WARD = "ward";
    private static final String KEY_VILLAGE = "village";
    private static final String KEY_SUB_VILLAGE = "sub_village";
    private static final String KEY_PIC = "pic";
    private static final String KEY_FINGER = "finger";

    static final String KEY_INPUT_ID = "inputid";
    private static final String KEY_INPUT_CATEGORY = "inputcat";
    public static final String KEY_INPUT_TYPE = "inputtyp";

    private static final String TABLE_COUNTRIES = "countries";
    private static final String KEY_COUNTRY_ID = "countryid";
    private static final String KEY_COUNTRY_NAME = "country_name";

    private static final String TABLE_REGIONS = "regions";
    private static final String KEY_REGION_ID = "regionid";
    private static final String KEY_REGION_NAME = "region_name";

    private static final String TABLE_DISTRICTS = "districts";
    private static final String KEY_DISTRICT_ID = "districtid";
    private static final String KEY_DISTRICT_NAME = "district_name";

    private static final String TABLE_WARDS = "wards";
    private static final String KEY_WARD_ID = "wardid";
    private static final String KEY_WARD_NAME = "ward_name";

    private static final String TABLE_VILLAGES = "villages";
    private static final String KEY_VILLAGE_ID = "villageid";
    private static final String KEY_VILLAGE_NAME = "village_name";

    private static final String TABLE_SUBVILLAGES = "subvillages";
    private static final String KEY_SUBVILLAGE_ID = "subvillageid";
    private static final String KEY_SUBVILLAGE_NAME = "subvillage_name";
    public static final String KEY_GEN_ID = "gen_id";

    private static final String TABLE_TRAININGS = "trainings";
    static final String KEY_TRAIN_ID = "tid";
    private static final String KEY_TRAIN_NAME = "train_name";
    public static final String KEY_TRAIN_DATE = "train_date";
    private static final String TABLE_ASSIGNED_TRAININGS = "assigned_trainings";
    private static final String KEY_LAT = "lat";
    private static final String KEY_LONGT = "longt";

    private static final String TABLE_USERS = "users";
    private static final String KEY_USER_ID = "id2";
    private static final String KEY_USER_NAME = "name";
    private static final String KEY_USER_PWD = "pwd";
    static final String KEY_COMPANY_ID = "company_id";
    private static final String KEY_COMPANY_NAME = "company_name";

    private static final String TABLE_COMPANIES = "companies";
    public static final String KEY_TRAIN_CAT = "train_cat";
    private static final String TABLE_TRAIN_CATEGORIES = "train_categories";
    private static final String KEY_TRAIN_CAT_ID = "tcatid";
    private static final String KEY_TRAIN_TYPE = "train_type";
    private static final String KEY_ROW_ID = "_id";
    static final String KEY_TRAIN_STOP_TIME = "train_stop_time";
    static final String KEY_TRAIN_START_TIME = "train_start_time";
    private static final String TABLE_FARMER_TIMES = "farmer_times";
    public static final String KEY_FID = "fid";
    private static final String KEY_FARMER_DATE = "farmer_date";
    private static final String KEY_FARMER_TIME = "farmer_time";
    private static final String KEY_TRAIN_STATUS = "train_status";
    private static final String TABLE_FINGER_ONE = "finger_one";
    private static final String TABLE_FINGER_TWO = "finger_two";
    private static final String TABLE_FINGER_THREE = "finger_three";
    private static final String TABLE_FINGER_FOUR = "finger_four";
    private static final String TABLE_FINGER_FIVE = "finger_five";
    private static final String KEY_FINGER_ID = "finger_id";
    private static final String KEY_SOIL_TYPE = "soil_type";
    private static final String KEY_CORRECT_SEED = "correct_seed";
    private static final String KEY_GAP_FILL = "gap_fill";
    private static final String KEY_FIRST_BRANCH = "first_branch";
    private static final String KEY_PEST_LEVEL = "pest_level";
    private static final String KEY_WATER_LOG_RISK = "water_log_risk";
    private static final String KEY_ROW_SPACING = "row_spacing";
    private static final String KEY_EROSION_PREV = "erosion_prev";
    private static final String KEY_CROP_ROTATION = "crop_rotation";
    private static final String KEY_RATOON = "ratoon";
    private static final String KEY_CROP_RESIDUES = "crop_residues";
    private static final String KEY_MANURE = "manure";
    private static final String KEY_LAND_PREP = "land_prep";
    private static final String KEY_SEED_BED_PREP = "seed_bed_prep";
    private static final String KEY_SEED_PER_STAT = "seed_per_stat";
    private static final String KEY_PLANTING_TIME = "planting_time";
    private static final String KEY_GAP_FILL_EMER = "gap_fill_emer";
    private static final String KEY_THIN_NUM = "thin_num";
    private static final String KEY_THIN_NUM_EMER = "thin_num_emer";
    private static final String KEY_FOLIAR = "foliar";
    private static final String KEY_WEEDS = "weeds";
    private static final String KEY_PEG_BOARD_AVAIL = "peg_board_avail";
    private static final String KEY_PEST_DAMAGE = "pest_damage";
    private static final String KEY_LAST_SCOUT = "last_scout";
    private static final String KEY_EMPTY_CANS = "empty_cans";
    private static final String KEY_SCOUT_METHOD = "scount_method";
    private static final String KEY_SPRAY_TIME = "spray_time";
    private static final String KEY_PEST_ABS_DURATION = "pest_abs_duration";
    private static final String KEY_CORRECT_USE_PESTICIDE = "correct_use_pesticide";
    private static final String KEY_SAFE_USAGE_CANS = "safe_usage_cans";
    private static final String TABLE_FARMS = "farms";
    private static final String KEY_FARM_ID = "pid";
    private static final String KEY_FARM_NAME = "plot_name";
    private static final String KEY_FARM_SIZE = "plot_size";
    private static final String KEY_FARM_PERI = "plot_peri";
    static final String KEY_TRAIN_LAT = "train_lat";
    static final String KEY_TRAIN_LONGT = "train_longt";
    private static final String TABLE_SIGNED_DOCS = "signed_docs";
    private static final String KEY_SIGN_DATE = "sign_date";
    private static final String KEY_REG_DATE = "reg_date";
    private static final String KEY_FORM_DATE = "form_date";
    private static final String TABLE_KML_META_DATA = "kml_meta_data";
    static final String KEY_RCPT_NUM = "rcpt_num";

    private int companyCount;
    private List<Villages> villages;
    private List<Training> allTrainingsCats;
    private List<String> allFarmerTimes;
    private List<String> allTrainingDates;
    private FarmerTime farmerTimeID;
    private final String TABLE_TRAIN_TYPES = "train_types";
    private final String TABLE_FARM_INPUTS = "farm_inputs";
    static String KEY_INPUT_QUANTITY = "input_quantity";
    public static String KEY_INPUT_PRICE = "input_price";
    public static String KEY_INPUT_UNIT = "input_unit";
    public static String KEY_INPUT_TOTAL = "input_total";
    // private static final String KEY_REG_DATE = "reg_date";
    static final String KEY_ASS_INPUT_ID = "assfinputs_id";
    private String KEY_EST_FARM_AREA = "est_farm_area";
    private String KEY_EST_FARM_AREA1 = "est_farm_area1";
    private String KEY_EST_FARM_AREA2 = "est_farm_area2";
    private String KEY_EST_FARM_AREA3 = "est_farm_area3";
    //Plot assesment table structure
    private String TABLE_FARM_ASS_MAJOR = "activity_log_major";
    private String KEY_FORM_TYPE_ID = "activity_type_id";
    private String KEY_ACTIVITY_METHOD = "activity_method";
    private String KEY_ACTIVITY_DATE = "activity_date";
    private String KEY_FAMILY_HOURS = "family_hours";
    private String KEY_HIRED_HOURS = "hired_hours";
    private String KEY_COLLECT_DATE = "collect_date";
    private String KEY_MONEY_OUT = "money_out";
    private String KEY_REMARKS = "remarks";
    //other crops table
    private String TABLE_FARM_OTHER_CROPS = "farm_other_crops";
    private String KEY_CROP_ID1 = "crop_id1";
    private String KEY_CROP_ID2 = "crop_id2";
    private String KEY_CROP_ID3 = "crop_id3";
    //table first food planting rains
    private String TABLE_PLANTING_RAINS = "planting_rains";
    private String KEY_RAIN_DATE = "rain_date";
    //table activity log medium
    private String TABLE_FARM_ASS_MEDIUM;
    private String KEY_SPRAY_METHOD = "spray_method";
    private String TABLE_GERMINATION = "germination";
    private String KEY_GERM_DATE = "germ_date";
    //table molasses trap catches
    private String TABLE_MOLASSES_TRAP_CATCHES = "molasses_trap_catches";
    private String KEY_TRAP_DATE = "trap_date";
    private String KEY_ACTION_TAKEN = "action_taken";
    private String KEY_TRAP1 = "trap1";
    private String KEY_TRAP2 = "trap2";
    //yable yield estimate
    private String TABLE_YIELD_ESTIMATE = "yield_estimate";
    private String KEY_NUM_OF_PLANTS = "num_of_plants";
    private String KEY_NUM_OF_BALLS = "num_of_balls";
    private String KEY_DIS_TO_LEFT = "dis_to_left";
    private String KEY_DIS_TO_RIGHT = "dis_to_right";
    //table farm production
    private String TABLE_FARM_PRODUCTION = "farm_production";
    private String KEY_PICKING_ID = "picking_id";
    private String KEY_GRADE_A = "grade_a";
    private final String KEY_GRADE_B = "grade_b";
    private String KEY_GRADE_C = "grade_c";
    //table transport house to market
    private String TABLE_TRANS_HSE_TO_MARKET = "trans_hse_to_market";
    private String KEY_DELIVERY_ID = "delivery_id";
    private String KEY_TRANS_DATE = "trans_date";
    private String KEY_TRANS_MODE_ID = "trans_mode_id";
    private String KEY_DELIVERY_DATE = "delivery_date";
    //table farm income
    private String TABLE_FARM_INCOME = "farm_income";
//    //table farm assesment form names
//    private String TABLE_FARM_ASS_FORM_NAMES = "farm_ass_form_names";
//    private String KEY_FORM_ID = "form_id";
//    private String KEY_FORM_NAME = "form_name";
//    //table form types
//    private String TABLE_FARM_ASS_FORM_TYPES = "farm_ass_form_types";
//    private String KEY_FORM_TYPE = "form_type";
    private String KEY_SPRAY_TYPE = "spray_type";
    private String TABLE_HERBICIDES = "herbicides";
    private String TABLE_FOLIAR_FEED = "foliar_feed";
    //table other crops
    private String TABLE_OTHER_CROPS="other_crops";
    private String KEY_CROP_ID="crop_id";
    private String KEY_CROP_NAME="crop_name";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_COUNTRIES = "CREATE TABLE IF NOT EXISTS " + TABLE_COUNTRIES + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_COUNTRY_ID + " TEXT," + KEY_COUNTRY_NAME + " TEXT" + ")";

        String CREATE_TABLE_REGIONS = "CREATE TABLE IF NOT EXISTS " + TABLE_REGIONS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_COUNTRY_ID + " TEXT,"
                + KEY_REGION_ID + " TEXT," + KEY_REGION_NAME + " TEXT" + ")";

        String CREATE_TABLE_DISTRICTS = "CREATE TABLE IF NOT EXISTS " + TABLE_DISTRICTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_REGION_ID + " TEXT,"
                + KEY_DISTRICT_ID + " TEXT," + KEY_DISTRICT_NAME + " TEXT" + ")";

        String CREATE_TABLE_WARDS = "CREATE TABLE IF NOT EXISTS " + TABLE_WARDS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_DISTRICT_ID + " TEXT,"
                + KEY_WARD_ID + " TEXT," + KEY_WARD_NAME + " TEXT" + ")";

        String CREATE_TABLE_VILLAGES = "CREATE TABLE IF NOT EXISTS " + TABLE_VILLAGES + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_WARD_ID + " TEXT,"
                + KEY_VILLAGE_ID + " TEXT," + KEY_VILLAGE_NAME + " TEXT" + ")";

        String CREATE_TABLE_SUBVILLAGES = "CREATE TABLE IF NOT EXISTS " + TABLE_SUBVILLAGES + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_VILLAGE_ID + " TEXT,"
                + KEY_SUBVILLAGE_ID + " TEXT," + KEY_SUBVILLAGE_NAME + " TEXT" + ")";

        String CREATE_FARMERS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_FARMERS + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_FNAME + " TEXT," + KEY_LNAME + " TEXT," + KEY_GENDER + " TEXT," + KEY_ID_NO + " TEXT,"
                + KEY_PH_NO + " TEXT," + KEY_EMAIL + " TEXT," + KEY_POST + " TEXT," + KEY_VILLAGE + " TEXT," + KEY_SUB_VILLAGE + " TEXT," + KEY_PIC + " TEXT," + KEY_FINGER + " TEXT," + KEY_LAT + " TEXT," + KEY_LONGT + " TEXT," + KEY_EST_FARM_AREA + " TEXT," + KEY_REG_DATE + " TEXT," + KEY_COMPANY_ID + " TEXT" + ")";

        String CREATE_FARMINPUTS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_FARMINPUTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_INPUT_ID + " TEXT," + KEY_INPUT_CATEGORY + " TEXT," + KEY_INPUT_TYPE + " TEXT" + ")";

        String CREATE_TABLE_ASSIGNED_INPUTS = "CREATE TABLE IF NOT EXISTS " + TABLE_ASSIGNED_INPUTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_ASS_INPUT_ID + " TEXT," + KEY_INPUT_ID + " TEXT," + KEY_INPUT_TYPE + " TEXT," + KEY_INPUT_UNIT + " TEXT," + KEY_FID + " TEXT," + KEY_GEN_ID + " TEXT," + KEY_RCPT_NUM + " TEXT," + KEY_INPUT_PRICE + " TEXT," + KEY_INPUT_TOTAL + " TEXT" + ")";

        String CREATE_TABLE_TRAININGS = "CREATE TABLE IF NOT EXISTS " + TABLE_TRAININGS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_TRAIN_ID + " TEXT," + KEY_TRAIN_DATE + " TEXT," + KEY_FARM_ID + " TEXT" + ")";

        String CREATE_TABLE_TRAINING_CATEGORIES = "CREATE TABLE IF NOT EXISTS " + TABLE_TRAIN_CATEGORIES + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_TRAIN_CAT_ID + " TEXT," + KEY_TRAIN_CAT + " TEXT" + ")";

        String CREATE_TABLE_TRAINING_TYPES = "CREATE TABLE IF NOT EXISTS " + TABLE_TRAIN_TYPES + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_TRAIN_CAT_ID + " TEXT," + KEY_TRAIN_ID + " TEXT," + KEY_TRAIN_TYPE + " TEXT" + ")";

        String CREATE_TABLE_ASSIGNED_TRAININGS = "CREATE TABLE IF NOT EXISTS " + TABLE_ASSIGNED_TRAININGS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_TRAIN_ID + " TEXT," + KEY_TRAIN_START_TIME + " TEXT," + KEY_TRAIN_STOP_TIME + " TEXT," + KEY_TRAIN_LAT + " TEXT," + KEY_TRAIN_LONGT + " TEXT," + KEY_COMPANY_ID + " TEXT" + ")";

        String CREATE_TABLE_USERS = "CREATE TABLE IF NOT EXISTS " + TABLE_USERS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_USER_ID + " TEXT," + KEY_USER_NAME + " TEXT," + KEY_USER_PWD + " TEXT," + KEY_VILLAGE_ID + " TEXT," + KEY_COMPANY_ID + " TEXT" + ")";

        String CREATE_TABLE_COMPANIES = "CREATE TABLE IF NOT EXISTS " + TABLE_COMPANIES + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_COMPANY_ID + " TEXT," + KEY_COMPANY_NAME + " TEXT" + ")";

        String CREATE_TABLE_FARMER_TIMES = "CREATE TABLE IF NOT EXISTS " + TABLE_FARMER_TIMES + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_FID + " TEXT," + KEY_USER_ID + " TEXT," + KEY_FARMER_TIME + " TEXT" + ")";

        String CREATE_TABLE_FINGER_ONE = "CREATE TABLE IF NOT EXISTS " + TABLE_FINGER_ONE + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_FINGER_ID + " TEXT," + KEY_COMPANY_ID + " TEXT," + KEY_USER_ID + " TEXT," + KEY_SOIL_TYPE + " TEXT," + KEY_WATER_LOG_RISK + " TEXT," + KEY_EROSION_PREV + " TEXT," + KEY_CROP_ROTATION + " TEXT," + KEY_RATOON + " TEXT," + KEY_CROP_RESIDUES + " TEXT," + KEY_MANURE + " TEXT," + KEY_LAND_PREP + " TEXT," + KEY_FARM_ID + " TEXT," + KEY_SEED_BED_PREP + " TEXT," + KEY_FORM_DATE + " TEXT" + ")";

        String CREATE_TABLE_FINGER_TWO = "CREATE TABLE IF NOT EXISTS " + TABLE_FINGER_TWO + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_FINGER_ID + " TEXT," + KEY_COMPANY_ID + " TEXT," + KEY_USER_ID + " TEXT," + KEY_CORRECT_SEED + " TEXT," + KEY_ROW_SPACING + " TEXT," + KEY_SEED_PER_STAT + " TEXT," + KEY_FARM_ID + " TEXT," + KEY_PLANTING_TIME + " TEXT," + KEY_FORM_DATE + " TEXT" + ")";

        String CREATE_TABLE_FINGER_THREE = "CREATE TABLE IF NOT EXISTS " + TABLE_FINGER_THREE + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_FINGER_ID + " TEXT," + KEY_COMPANY_ID + " TEXT," + KEY_USER_ID + " TEXT," + KEY_GAP_FILL + " TEXT," + KEY_GAP_FILL_EMER + " TEXT," + KEY_THIN_NUM + " TEXT," + KEY_FARM_ID + " TEXT," + KEY_THIN_NUM_EMER + " TEXT," + KEY_FORM_DATE + " TEXT" + ")";

        String CREATE_TABLE_FINGER_FOUR = "CREATE TABLE IF NOT EXISTS " + TABLE_FINGER_FOUR + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_FINGER_ID + " TEXT," + KEY_COMPANY_ID + " TEXT," + KEY_USER_ID + " TEXT," + KEY_FIRST_BRANCH + " TEXT," + KEY_FOLIAR + " TEXT," + KEY_FARM_ID + " TEXT," + KEY_WEEDS + " TEXT," + KEY_FORM_DATE + " TEXT" + ")";

        String CREATE_TABLE_FINGER_FIVE = "CREATE TABLE IF NOT EXISTS " + TABLE_FINGER_FIVE + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_FINGER_ID + " TEXT," + KEY_COMPANY_ID + " TEXT," + KEY_USER_ID + " TEXT," + KEY_PEST_LEVEL + " TEXT," + KEY_PEST_DAMAGE + " TEXT," + KEY_LAST_SCOUT + " TEXT," + KEY_EMPTY_CANS + " TEXT," + KEY_PEG_BOARD_AVAIL + " TEXT," + KEY_SCOUT_METHOD + " TEXT," + KEY_SPRAY_TIME + " TEXT," + KEY_PEST_ABS_DURATION + " TEXT," + KEY_CORRECT_USE_PESTICIDE + " TEXT," + KEY_FARM_ID + " TEXT," + KEY_SAFE_USAGE_CANS + " TEXT," + KEY_FORM_DATE + " TEXT" + ")";

        String CREATE_TABLE_FARMS = "CREATE TABLE IF NOT EXISTS " + TABLE_FARMS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_FARM_ID + " TEXT," + KEY_FARM_NAME + " TEXT," + KEY_FARM_SIZE + " TEXT," + KEY_FARM_PERI + " TEXT," + KEY_LAT + " TEXT," + KEY_LONGT + " TEXT," + KEY_COMPANY_ID + " TEXT" + ")";

        String CREATE_TABLE_SIGNED_DOCS = "CREATE TABLE IF NOT EXISTS " + TABLE_SIGNED_DOCS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_FID + " TEXT," + KEY_SIGN_DATE + " TEXT," + KEY_USER_ID + " TEXT," + KEY_COMPANY_ID + " TEXT" + ")";

        String CREATE_TABLE_KML_META_DATA = "CREATE TABLE IF NOT EXISTS " + TABLE_KML_META_DATA + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_FID + " TEXT," + KEY_COMPANY_ID + " TEXT," + KEY_USER_ID + " TEXT," + KEY_FARM_ID + " TEXT" + ")";

//        String CREATE_TABLE_FARM_ASS_FORM_NAMES = "CREATE TABLE IF NOT EXISTS " + TABLE_FARM_ASS_FORM_NAMES + "("
//                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_FORM_ID + " TEXT," + KEY_FORM_NAME + " TEXT" + ")";
//
//        String CREATE_TABLE_FARM_ASS_FORM_TYPES = "CREATE TABLE IF NOT EXISTS " + TABLE_FARM_ASS_FORM_TYPES + "("
//                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_FORM_TYPE_ID + " TEXT," + KEY_FORM_ID + " TEXT," + KEY_FORM_TYPE + " TEXT" + ")";

        String CREATE_TABLE_FARM_ASS_MAJOR = "CREATE TABLE IF NOT EXISTS " + TABLE_FARM_ASS_MAJOR + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_FORM_TYPE_ID + " TEXT," + KEY_ACTIVITY_METHOD + " TEXT," + KEY_FARM_ID + " TEXT," + KEY_ACTIVITY_DATE + " TEXT," + KEY_COLLECT_DATE + " TEXT," + KEY_FAMILY_HOURS + " TEXT," + KEY_HIRED_HOURS + " TEXT," + KEY_MONEY_OUT + " TEXT," + KEY_REMARKS + " TEXT," + KEY_USER_ID + " TEXT," + KEY_LAT + " TEXT," + KEY_LONGT + " TEXT," + KEY_COMPANY_ID + " TEXT" + ")";

        String CREATE_TABLE_FARM_OTHER_CROPS = "CREATE TABLE IF NOT EXISTS " + TABLE_FARM_OTHER_CROPS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_FARM_ID + " TEXT," + KEY_CROP_ID1 + " TEXT," + KEY_CROP_ID2 + " TEXT," + KEY_CROP_ID3 + " TEXT," + " TEXT," + KEY_USER_ID + " TEXT," + KEY_LAT + " TEXT," + KEY_LONGT + " TEXT," + KEY_COMPANY_ID + " TEXT" + ")";

        String CREATE_TABLE_PLANTING_RAINS = "CREATE TABLE IF NOT EXISTS " + TABLE_PLANTING_RAINS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_FARM_ID + " TEXT," + KEY_RAIN_DATE + " TEXT," + KEY_COLLECT_DATE + " TEXT," + " TEXT," + KEY_USER_ID + " TEXT," + KEY_LAT + " TEXT," + KEY_LONGT + " TEXT," + KEY_COMPANY_ID + " TEXT" + ")";

        String CREATE_TABLE_FARM_ASS_MEDIUM = "CREATE TABLE IF NOT EXISTS " + TABLE_FARM_ASS_MEDIUM + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_FARM_ID + " TEXT," + KEY_FORM_TYPE_ID + " TEXT," + KEY_ACTIVITY_DATE + " TEXT," + KEY_COLLECT_DATE + " TEXT," + KEY_FAMILY_HOURS + " TEXT," + KEY_HIRED_HOURS + " TEXT," + KEY_MONEY_OUT + "TEXT," + KEY_INPUT_ID + "TEXT," + KEY_INPUT_QUANTITY + "TEXT," + KEY_SPRAY_METHOD + "TEXT," + KEY_USER_ID + " TEXT," + KEY_LAT + " TEXT," + KEY_LONGT + " TEXT," + KEY_COMPANY_ID + " TEXT" + ")";

        String CREATE_TABLE_HERBICIDES = "CREATE TABLE IF NOT EXISTS " + TABLE_HERBICIDES + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_INPUT_ID + " TEXT," + KEY_INPUT_TYPE + " TEXT" + ")";

        String CREATE_TABLE_FOLIAR_FEED = "CREATE TABLE IF NOT EXISTS " + TABLE_FOLIAR_FEED+ "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_INPUT_ID + " TEXT," + KEY_INPUT_TYPE + " TEXT" + ")";

        String CREATE_TABLE_GERMINATION = "CREATE TABLE IF NOT EXISTS " + TABLE_GERMINATION + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_FARM_ID + " TEXT," + KEY_FORM_TYPE_ID + " TEXT," + KEY_GERM_DATE + " TEXT," + KEY_COLLECT_DATE + " TEXT," + KEY_REMARKS + " TEXT," + KEY_USER_ID + " TEXT," + KEY_LAT + " TEXT," + KEY_LONGT + " TEXT," + KEY_COMPANY_ID + " TEXT" + ")";

        String CREATE_TABLE_MOLASSES_TRAP_CATCHES = "CREATE TABLE IF NOT EXISTS " + TABLE_MOLASSES_TRAP_CATCHES + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_FARM_ID + " TEXT," + KEY_TRAP_DATE + " TEXT," + KEY_COLLECT_DATE + " TEXT," + KEY_TRAP1 + " TEXT," + KEY_TRAP2 + " TEXT," + KEY_ACTION_TAKEN + " TEXT," + KEY_USER_ID + " TEXT," + KEY_COMPANY_ID + " TEXT" + ")";

        String CREATE_TABLE_YIELD_ESTIMATE = "CREATE TABLE IF NOT EXISTS " + TABLE_YIELD_ESTIMATE + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_FARM_ID + " TEXT," + KEY_NUM_OF_PLANTS + " TEXT," + KEY_NUM_OF_BALLS + " TEXT," + KEY_DIS_TO_LEFT + " TEXT," + KEY_DIS_TO_RIGHT + " TEXT," + KEY_COLLECT_DATE + " TEXT," + KEY_LAT + " TEXT," + KEY_LONGT + " TEXT," + KEY_USER_ID + " TEXT," + KEY_COMPANY_ID + " TEXT" + ")";

        String CREATE_TABLE_FARM_PRODUCTION = "CREATE TABLE IF NOT EXISTS " + TABLE_FARM_PRODUCTION + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_FARM_ID + " TEXT," + KEY_PICKING_ID + " TEXT," + KEY_GRADE_A + " TEXT," + KEY_GRADE_B + " TEXT," + KEY_GRADE_C + " TEXT," + KEY_COLLECT_DATE + " TEXT," + KEY_USER_ID + " TEXT," + KEY_COMPANY_ID + " TEXT" + ")";

        String CREATE_TABLE_TRANS_HSE_TO_MARKET = "CREATE TABLE IF NOT EXISTS " + TABLE_TRANS_HSE_TO_MARKET + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_FARM_ID + " TEXT," + KEY_DELIVERY_ID + " TEXT," + KEY_TRANS_DATE + " TEXT," + KEY_TRANS_MODE_ID + " TEXT," + KEY_MONEY_OUT + " TEXT," + KEY_COLLECT_DATE + " TEXT," + KEY_USER_ID + " TEXT," + KEY_COMPANY_ID + " TEXT" + ")";

        String CREATE_TABLE_FARM_INCOME = "CREATE TABLE IF NOT EXISTS " + TABLE_FARM_INCOME + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_FARM_ID + " TEXT," + KEY_DELIVERY_ID + " TEXT," + KEY_DELIVERY_DATE + " TEXT," + KEY_GRADE_A + " TEXT," + KEY_GRADE_B + " TEXT," + KEY_GRADE_C + " TEXT," + KEY_COLLECT_DATE + " TEXT," + KEY_USER_ID + " TEXT," + KEY_COMPANY_ID + " TEXT" + ")";

        db.execSQL(CREATE_FARMERS_TABLE);
        db.execSQL(CREATE_FARMINPUTS_TABLE);
        db.execSQL(CREATE_TABLE_COUNTRIES);
        db.execSQL(CREATE_TABLE_REGIONS);
        db.execSQL(CREATE_TABLE_DISTRICTS);
        db.execSQL(CREATE_TABLE_WARDS);
        db.execSQL(CREATE_TABLE_VILLAGES);
        db.execSQL(CREATE_TABLE_SUBVILLAGES);
        db.execSQL(CREATE_TABLE_ASSIGNED_INPUTS);
        db.execSQL(CREATE_TABLE_TRAININGS);
        db.execSQL(CREATE_TABLE_ASSIGNED_TRAININGS);
        db.execSQL(CREATE_TABLE_USERS);
        db.execSQL(CREATE_TABLE_COMPANIES);
        db.execSQL(CREATE_TABLE_TRAINING_CATEGORIES);
        db.execSQL(CREATE_TABLE_TRAINING_TYPES);
        db.execSQL(CREATE_TABLE_FARMER_TIMES);
        db.execSQL(CREATE_TABLE_FINGER_ONE);
        db.execSQL(CREATE_TABLE_FINGER_TWO);
        db.execSQL(CREATE_TABLE_FINGER_THREE);
        db.execSQL(CREATE_TABLE_FINGER_FOUR);
        db.execSQL(CREATE_TABLE_FINGER_FIVE);
        db.execSQL(CREATE_TABLE_FARMS);
        db.execSQL(CREATE_TABLE_SIGNED_DOCS);
        db.execSQL(CREATE_TABLE_KML_META_DATA);
//        db.execSQL(CREATE_TABLE_FARM_ASS_FORM_TYPES);
        db.execSQL(CREATE_TABLE_FARM_ASS_MAJOR);
        db.execSQL(CREATE_TABLE_FARM_OTHER_CROPS);
        db.execSQL(CREATE_TABLE_PLANTING_RAINS);
        db.execSQL(CREATE_TABLE_HERBICIDES);
        db.execSQL(CREATE_TABLE_FOLIAR_FEED);
        db.execSQL(CREATE_TABLE_FARM_ASS_MEDIUM);

    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FARMERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FARMINPUTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COUNTRIES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REGIONS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DISTRICTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WARDS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VILLAGES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SUBVILLAGES);
        db.execSQL("DROP TABLE IF EXISTS" + TABLE_ASSIGNED_INPUTS);
        db.execSQL("DROP TABLE IF EXISTS" + TABLE_TRAININGS);
        db.execSQL("DROP TABLE IF EXISTS" + TABLE_ASSIGNED_TRAININGS);
        db.execSQL("DROP TABLE IF EXISTS" + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS" + TABLE_COMPANIES);
        // Create tables again
        onCreate(db);
    }

    /**
     * Method to insert data into planting rains table
     *
     * @param rains
     */
    public void addPlantingRains(PlantingRains rains) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_FARM_ID, rains.getFarmID());
        values.put(KEY_RAIN_DATE, rains.getRainDate());
        values.put(KEY_COLLECT_DATE, rains.getCollectDate());
        values.put(KEY_USER_ID, rains.getUserID());
        values.put(KEY_LAT, rains.getLat());
        values.put(KEY_LONGT, rains.getLongt());
        values.put(KEY_COMPANY_ID, rains.getCID());

        db.insert(TABLE_PLANTING_RAINS, null, values);
        db.close();
    }

    /**
     * Method to insert data into farm other crops table
     *
     * @param farm_other_crops
     */
    public void addFarmOtherCrops(FarmOtherCrops farm_other_crops) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_FARM_ID, farm_other_crops.getFarmID());
        values.put(KEY_CROP_ID1, farm_other_crops.getCropID1());
        values.put(KEY_CROP_ID2, farm_other_crops.getCropID2());
        values.put(KEY_CROP_ID3, farm_other_crops.getCropID3());
        values.put(KEY_USER_ID, farm_other_crops.getUserID());
        values.put(KEY_COLLECT_DATE, farm_other_crops.getCollectDate());
        values.put(KEY_LAT, farm_other_crops.getLat());
        values.put(KEY_LONGT, farm_other_crops.getLongt());
        values.put(KEY_COMPANY_ID, farm_other_crops.getCID());

        db.insert(TABLE_FARM_OTHER_CROPS, null, values);
        db.close();
    }
    
     /**
     * Method to get data in other crops table
     *
     * @return list of other crops
     */
    public List<OtherCrops> allOtherCrops( ){
         List<OtherCrops> other_crops_list = new ArrayList<OtherCrops>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_OTHER_CROPS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                OtherCrops other_crops = new OtherCrops();
                other_crops.setCropID(cursor.getString(cursor.getColumnIndex(KEY_CROP_ID)));
                other_crops.setCropName(cursor.getString(cursor.getColumnIndex(KEY_CROP_NAME)));

                // Adding other crops to list
                other_crops_list.add(other_crops);
            } while (cursor.moveToNext());
        }
        return other_crops_list;
    }
    
    /**
     * Method to insert all data into other crops table
     *
     * @param other_crops
     */
    public void addOtherCrops(OtherCrops other_crops) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_CROP_ID, other_crops.getCropID());
        values.put(KEY_CROP_NAME, other_crops.getCropName());

        db.insert(TABLE_OTHER_CROPS, null, values);
        db.close();
    }

    /**
     * adds data to herbicides table
     *
     * @param herbicides
     */
    public void addHerbicides(Herbicides herbicides) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_INPUT_ID, herbicides.getInputID());
        values.put(KEY_INPUT_TYPE, herbicides.getInputType());

        db.insert(TABLE_HERBICIDES, null, values);
        db.close();
    }

    /**
     * adds data to foliar feed table
     *
     * @param foliarfeed
     */
    public void addFoliarFeed(FoliarFeed foliarfeed) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_INPUT_ID, foliarfeed.getInputID());
        values.put(KEY_INPUT_TYPE, foliarfeed.getInputType());

        db.insert(TABLE_FOLIAR_FEED, null, values);
        db.close();
    }
//
//    /**
//     * adds data to farm assessment form types
//     *
//     * @param form_types
//     */
//    public void addFarmAssFormTypes(FarmAssFormTypes form_types) {
//        SQLiteDatabase db = this.getReadableDatabase();
//        ContentValues values = new ContentValues();
//        values.put(KEY_FORM_TYPE_ID, form_types.getFormTypeID());
//        values.put(KEY_FORM_ID, form_types.getFormID());
//        values.put(KEY_FORM_TYPE, form_types.getFormType());
//
//        db.insert(TABLE_FARM_ASS_FORM_TYPES, null, values);
//        db.close();
//    }

    /**
     * method to add a new activity log major in to table.
     *
     * @param major
     */
    public void addFarmAssMajor(FarmAssFormsMajor major) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_FARM_ID, major.getFarmID());
        values.put(KEY_FORM_TYPE_ID, major.getFormTypeID());
        values.put(KEY_ACTIVITY_METHOD, major.getActivityMethod());
        values.put(KEY_ACTIVITY_DATE, major.getActDate());
        values.put(KEY_FAMILY_HOURS, major.getFamilyHours());
        values.put(KEY_HIRED_HOURS, major.getHiredHours());
        values.put(KEY_COLLECT_DATE, major.getCollectDate());
        values.put(KEY_MONEY_OUT, major.getMoneyOut());
        values.put(KEY_REMARKS, major.getRemarks());
//        values.put(KEY_INPUT_ID, major.getInputID());
//        values.put(KEY_INPUT_QUANTITY, major.getInputQuantity());
//        values.put(KEY_SPRAY_TYPE, major.getSprayType());
        values.put(KEY_USER_ID, major.getUserID());
        values.put(KEY_COMPANY_ID, major.getCoID());
        Log.i("FAFM: ", values.getAsString(KEY_FARM_ID));

        db.insert(TABLE_FARM_ASS_MAJOR, null, values);
        db.close();
    }

    public void addUser(Users user) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_USER_ID, user.getUserID());
        values.put(KEY_USER_NAME, user.getUserName());
        values.put(KEY_USER_PWD, user.getUserPwd());
        values.put(KEY_VILLAGE_ID, user.getVID());
        values.put(KEY_COMPANY_ID, user.getCoID());
        db.insert(TABLE_USERS, null, values);
        db.close();
    }

    public void addCompany(Companies company) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_COMPANY_ID, company.getCoID());
        values.put(KEY_COMPANY_NAME, company.getCoName());
        db.insert(TABLE_COMPANIES, null, values);
        db.close();
    }

    public void addAssignedInput(Assigns a) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ASS_INPUT_ID, a.getInputAssInputID());
        values.put(KEY_INPUT_ID, a.getInputID());
        values.put(KEY_INPUT_TYPE, a.getInputType());
        values.put(KEY_INPUT_PRICE, a.getInputPrice());
        values.put(KEY_INPUT_UNIT, a.getInputUnit());
        values.put(KEY_INPUT_TOTAL, a.getInputTotal());
        values.put(KEY_FID, a.getFID());
        values.put(KEY_GEN_ID, a.getGenID());
        values.put(KEY_RCPT_NUM, a.getRcptNum());

        db.insert(TABLE_ASSIGNED_INPUTS, null, values);
        db.close();
    }

    public void addAssignedTrainings(Training t) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        //values.put(KEY_INPUT_ID,a.getInputID());
        values.put(KEY_TRAIN_ID, t.getTID());
        values.put(KEY_TRAIN_START_TIME, t.getTStartTime());
        values.put(KEY_TRAIN_STOP_TIME, t.getTStopTime());
        values.put(KEY_TRAIN_LAT, t.getTLat());
        values.put(KEY_TRAIN_LONGT, t.getTLongt());
        values.put(KEY_COMPANY_ID, t.getCID());

        db.insert(TABLE_ASSIGNED_TRAININGS, null, values);
        db.close();
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */
    //Adding new countries
    public void addCountries(Countries country) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_COUNTRY_ID, country.getCountryID());
        values.put(KEY_COUNTRY_NAME, country.getCountryName());

        db.insert(TABLE_COUNTRIES, null, values);
        db.close();
    }

    //Adding new regions
    public void addRegions(Regions region) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_REGION_ID, region.getRegionID());
        values.put(KEY_COUNTRY_ID, region.getCountryID());
        values.put(KEY_REGION_NAME, region.getRegionName());

        db.insert(TABLE_REGIONS, null, values);
        db.close();
    }

    //Adding new districts
    public void addDistricts(Districts district) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_DISTRICT_ID, district.getDistrictID());
        values.put(KEY_REGION_ID, district.getRegionID());
        values.put(KEY_DISTRICT_NAME, district.getDistrictName());

        db.insert(TABLE_DISTRICTS, null, values);
        db.close();
    }

    //Adding new wards
    public void addWards(Wards ward) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_WARD_ID, ward.getWardID());
        values.put(KEY_DISTRICT_ID, ward.getDistrictID());
        values.put(KEY_WARD_NAME, ward.getWardName());

        db.insert(TABLE_WARDS, null, values);
        db.close();
    }

    //Adding new villages
    public void addVillages(Villages village) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_VILLAGE_ID, village.getVillageID());
        values.put(KEY_WARD_ID, village.getWardID());
        values.put(KEY_VILLAGE_NAME, village.getVillageName());

        db.insert(TABLE_VILLAGES, null, values);
        db.close();
    }

    //Adding new subvillages
    public void addSubvillages(SubVillages subvillage) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_SUBVILLAGE_ID, subvillage.getSubVillageID());
        values.put(KEY_VILLAGE_ID, subvillage.getVillageID());
        values.put(KEY_SUBVILLAGE_NAME, subvillage.getSubVillageName());

        db.insert(TABLE_SUBVILLAGES, null, values);
        db.close();
    }

    // Adding new farmer
    public void addFarmer(Farmers farmer) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_FNAME, farmer.getFName());
        values.put(KEY_LNAME, farmer.getLName());// Farmers Name
        values.put(KEY_GENDER, farmer.getGender());
        values.put(KEY_ID_NO, farmer.getIDNO());
        values.put(KEY_PH_NO, farmer.getPhoneNumber()); // Farmers Phone
        values.put(KEY_EMAIL, farmer.getEmail());
        values.put(KEY_POST, farmer.getPost());
        values.put(KEY_VILLAGE, farmer.getVillage());
        values.put(KEY_SUB_VILLAGE, farmer.getSubVillage());
        values.put(KEY_PIC, farmer.getPicPath());
        values.put(KEY_LAT, farmer.getLat());
        values.put(KEY_LONGT, farmer.getLong());
        values.put(KEY_EST_FARM_AREA, farmer.getEstFarmArea());
        values.put(KEY_REG_DATE, farmer.getRegDate());
        values.put(KEY_COMPANY_ID, farmer.getCID());

        // Inserting Row
        db.insert(TABLE_FARMERS, null, values);
        db.close(); // Closing database connection
    }

    // Adding new input
    public void addInput(InputsFromServer inputs) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_INPUT_ID, inputs.getInputId());
        values.put(KEY_INPUT_CATEGORY, inputs.getInputCat());
        values.put(KEY_INPUT_TYPE, inputs.getInputType());

        // Inserting Row
        db.insert(TABLE_FARMINPUTS, null, values);
        db.close(); // Closing database connection
    }

    public void addTraining(Training2 train) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_TRAIN_ID, train.getTID());
        values.put(KEY_TRAIN_DATE, train.getTDate1());
        values.put(KEY_FARM_ID, train.getVillageID());

        // Inserting Row
        db.insert(TABLE_TRAININGS, null, values);
        db.close(); // Closing database connection
    }

    public List<Countries> getAllCountries() {
        List<Countries> countriesList = new ArrayList<Countries>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_COUNTRIES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Countries country = new Countries();
                country.setCountryID(cursor.getString(cursor.getColumnIndex(KEY_COUNTRY_ID)));
                country.setCountryName(cursor.getString(cursor.getColumnIndex(KEY_COUNTRY_NAME)));

                // Adding farmer to list
                countriesList.add(country);
            } while (cursor.moveToNext());
        }
        return countriesList;
    }
    
     public List<FarmOtherCrops> getAllFarmOtherCrops() {
        List<FarmOtherCrops> farm_other_crops_list = new ArrayList<FarmOtherCrops>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_FARM_OTHER_CROPS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                FarmOtherCrops other_crops = new FarmOtherCrops();
                other_crops.setFarmID(cursor.getString(cursor.getColumnIndex(KEY_FARM_ID)));
                other_crops.setCropID1(cursor.getString(cursor.getColumnIndex(KEY_CROP_ID1)));
                other_crops.setCropID2(cursor.getString(cursor.getColumnIndex(KEY_CROP_ID2)));
                other_crops.setCropID3(cursor.getString(cursor.getColumnIndex(KEY_CROP_ID3)));
                other_crops.setUserID(cursor.getString(cursor.getColumnIndex(KEY_USER_ID)));
                other_crops.setCollectDate(cursor.getString(cursor.getColumnIndex(KEY_COLLECT_DATE)));
                other_crops.setLat(cursor.getString(cursor.getColumnIndex(KEY_LAT)));
                other_crops.setLongt(cursor.getString(cursor.getColumnIndex(KEY_LONGT)));
                other_crops.setCID(cursor.getString(cursor.getColumnIndex(KEY_COMPANY_ID)));

                // Adding farm other crops to list
                farm_other_crops_list.add(other_crops);
            } while (cursor.moveToNext());
        }
        return farm_other_crops_list;
    }

//    public List<FarmAssFormTypes> getAllFarmAssFormTypes() {
//        List<FarmAssFormTypes> form_types_list = new ArrayList<FarmAssFormTypes>();
//        // Select All Query
//        String selectQuery = "SELECT  * FROM " + TABLE_FARM_ASS_FORM_TYPES;
//
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor cursor = db.rawQuery(selectQuery, null);
//
//        // looping through all rows and adding to list
//        if (cursor.moveToFirst()) {
//            do {
//                FarmAssFormTypes form_types = new FarmAssFormTypes();
//                form_types.setFormTypeID(cursor.getString(cursor.getColumnIndex(KEY_FORM_TYPE_ID)));
//                form_types.setFormID(cursor.getString(cursor.getColumnIndex(KEY_FORM_ID)));
//                form_types.setFormType(cursor.getString(cursor.getColumnIndex(KEY_FORM_TYPE)));
//
//                // Adding farmer to list
//                form_types_list.add(form_types);
//            } while (cursor.moveToNext());
//        }
//        return form_types_list;
//    }

    public String getUserID(String s) {
        String usersList = null;
        // Select All Query
        String selectQuery = "SELECT  " + KEY_USER_ID + " FROM " + TABLE_USERS + " WHERE " + KEY_USER_NAME + " = '" + s + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Users user = new Users();
                user.setUserID(cursor.getString(cursor.getColumnIndex(KEY_USER_ID)));
                // user.setUserName(cursor.getString(cursor.getColumnIndex(KEY_USER_NAME)));
                //  user.setUserPwd(cursor.getString(cursor.getColumnIndex(KEY_USER_PWD)));
                //   user.setCoID(cursor.getString(cursor.getColumnIndex(KEY_COMPANY_ID)));

                // Adding farmer to list
                usersList = user.getUserID();
            } while (cursor.moveToNext());
        }
        return usersList;
    }

    public List<Users> getAllUsers() {
        List<Users> usersList = new ArrayList<Users>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_USERS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Users user = new Users();
                user.setUserID(cursor.getString(cursor.getColumnIndex(KEY_USER_ID)));
                user.setUserName(cursor.getString(cursor.getColumnIndex(KEY_USER_NAME)));
                user.setUserPwd(cursor.getString(cursor.getColumnIndex(KEY_USER_PWD)));
                user.setCoID(cursor.getString(cursor.getColumnIndex(KEY_COMPANY_ID)));

                // Adding farmer to list
                usersList.add(user);
            } while (cursor.moveToNext());
        }
        return usersList;
    }

    /**
     *
     * @return list of all rows in activity logs major table
     */
    public List<FarmAssFormsMajor> getAllActivityLogsMajor() {
        List<FarmAssFormsMajor> activitiesList = new ArrayList<FarmAssFormsMajor>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_FARM_ASS_MAJOR;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                FarmAssFormsMajor major = new FarmAssFormsMajor();
                major.setFarmID(cursor.getString(cursor.getColumnIndex(KEY_FARM_ID)));
                major.setFormTypeID(cursor.getString(cursor.getColumnIndex(KEY_FORM_TYPE_ID)));
                major.setActivityMethodID(cursor.getString(cursor.getColumnIndex(KEY_ACTIVITY_METHOD)));
                major.setActivityDate(cursor.getString(cursor.getColumnIndex(KEY_ACTIVITY_DATE)));
                major.setFamilyHours(cursor.getString(cursor.getColumnIndex(KEY_FAMILY_HOURS)));
                major.setHiredHours(cursor.getString(cursor.getColumnIndex(KEY_HIRED_HOURS)));
                major.setCollectDate(cursor.getString(cursor.getColumnIndex(KEY_COLLECT_DATE)));
                major.setMoneyOut(cursor.getString(cursor.getColumnIndex(KEY_MONEY_OUT)));
                major.setRemarks(cursor.getString(cursor.getColumnIndex(KEY_REMARKS)));
                major.setUserID(cursor.getString(cursor.getColumnIndex(KEY_USER_ID)));
                major.setCoID(cursor.getString(cursor.getColumnIndex(KEY_COMPANY_ID)));

                // Adding forms to list
                activitiesList.add(major);
            } while (cursor.moveToNext());
        }
        return activitiesList;
    }
    
       /**
     *
     * @return list of all rows in activity logs major table
     */
    public List<FarmAssFormsMedium> getAllActivityLogsMedium() {
        List<FarmAssFormsMedium> activitiesList = new ArrayList<FarmAssFormsMedium>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_FARM_ASS_MEDIUM;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                FarmAssFormsMedium medium = new FarmAssFormsMedium();
                medium.setFarmID(cursor.getString(cursor.getColumnIndex(KEY_FARM_ID)));
                medium.setFormTypeID(cursor.getString(cursor.getColumnIndex(KEY_FORM_TYPE_ID)));
                medium.setActivityMethodID(cursor.getString(cursor.getColumnIndex(KEY_ACTIVITY_METHOD)));
                medium.setActivityDate(cursor.getString(cursor.getColumnIndex(KEY_ACTIVITY_DATE)));
                medium.setFamilyHours(cursor.getString(cursor.getColumnIndex(KEY_FAMILY_HOURS)));
                medium.setHiredHours(cursor.getString(cursor.getColumnIndex(KEY_HIRED_HOURS)));
                medium.setCollectDate(cursor.getString(cursor.getColumnIndex(KEY_COLLECT_DATE)));
                medium.setMoneyOut(cursor.getString(cursor.getColumnIndex(KEY_MONEY_OUT)));
                medium.setRemarks(cursor.getString(cursor.getColumnIndex(KEY_REMARKS)));
                medium.setInputID(cursor.getString(cursor.getColumnIndex(KEY_INPUT_ID)));
                medium.setInputQuantity(cursor.getString(cursor.getColumnIndex(KEY_INPUT_QUANTITY)));
                medium.setSprayType(cursor.getString(cursor.getColumnIndex(KEY_SPRAY_TYPE)));
                medium.setUserID(cursor.getString(cursor.getColumnIndex(KEY_USER_ID)));
                medium.setCoID(cursor.getString(cursor.getColumnIndex(KEY_COMPANY_ID)));

                // Adding forms to list
                activitiesList.add(medium);
            } while (cursor.moveToNext());
        }
        return activitiesList;
    }

    /**
     *
     * @return list of all companies stored in companies table.
     */
    public List<Companies> getAllCompanies() {
        List<Companies> companyList = new ArrayList<Companies>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_COMPANIES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Companies company = new Companies();
                company.setCoID(cursor.getString(cursor.getColumnIndex(KEY_COMPANY_ID)));
                company.setCoName(cursor.getString(cursor.getColumnIndex(KEY_COMPANY_NAME)));

                // Adding farmer to list
                companyList.add(company);
            } while (cursor.moveToNext());
        }
        return companyList;
    }

    /**
     *
     * @param user_name username to be checked.
     * @param pwd password to be checked.
     * @param cid company id to be checked.
     * @return true if username and password are ok.
     */
    public boolean validateUser(String user_name, String pwd, String cid) {
        final String HEXES = "0123456789ABCDEF";
        List<Users> usersList = new ArrayList<Users>();
        // Select All Query
        //hash password
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        String pwd2 = pwd + "78(%#xd9_DO";

        try {
            md.update(pwd2.getBytes("UTF-8")); // Change this to "UTF-16" if needed
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        byte[] digest = md.digest();
        final StringBuilder final_pwd = new StringBuilder(2 * digest.length);
        for (final byte b : digest) {
            final_pwd.append(HEXES.charAt((b & 0xF0) >> 4))
                    .append(HEXES.charAt((b & 0x0F)));
        }

        String selectQuery = "SELECT  * FROM " + TABLE_USERS + " WHERE " + KEY_USER_NAME + " = '" + user_name + "' AND trim(" + KEY_USER_PWD + ") = '" + final_pwd.toString().toLowerCase().trim() + "' AND " + KEY_COMPANY_ID + " = '" + cid + "'";
        Log.i("Val Pwd", final_pwd.toString().toLowerCase());
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        int count = 0;
        boolean status;
        if (cursor != null && !cursor.isClosed()) {
            count = cursor.getCount();
            cursor.close();
        }
        if (count == 1) {
            status = true;
        } else if (count == 0) {
            status = false;
        } else {
            Log.e("User count:", String.valueOf(count));
            status = false;
        }
        return status;
    }

    public List<Training2> getAllTrainings() {
        List<Training2> trainingsList = new ArrayList<Training2>();
        // Select All Query
        //String selectQuery = "SELECT  * FROM " + TABLE_TRAININGS;
        String selectQuery = "SELECT  * FROM " + TABLE_TRAININGS + " WHERE DATE(" + KEY_TRAIN_DATE + ") = DATE('NOW')";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Training2 training = new Training2();
                training.setTID(cursor.getString(cursor.getColumnIndex(KEY_TRAIN_ID)));
                training.setTDate1(cursor.getString(cursor.getColumnIndex(KEY_TRAIN_DATE)));
                training.setFarmID(cursor.getString(cursor.getColumnIndex(KEY_FARM_ID)));

                // Adding farmer to list
                trainingsList.add(training);
            } while (cursor.moveToNext());
        }
        return trainingsList;
    }

    public List<Herbicides> getAllHerbicides() {
        List<Herbicides> herbicidesList = new ArrayList<Herbicides>();
        // Select All Query
        //String selectQuery = "SELECT  * FROM " + TABLE_TRAININGS;
        String selectQuery = "SELECT  * FROM " + TABLE_HERBICIDES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Herbicides herbicides = new Herbicides();
                herbicides.setInputID(cursor.getString(cursor.getColumnIndex(KEY_INPUT_ID)));
                herbicides.setInputType(cursor.getString(cursor.getColumnIndex(KEY_INPUT_TYPE)));

                // Adding farmer to list
                herbicidesList.add(herbicides);
            } while (cursor.moveToNext());
        }
        return herbicidesList;
    }
    
      public List<FoliarFeed> getAllFoliarFeed() {
        List<FoliarFeed> feedList = new ArrayList<FoliarFeed>();
        // Select All Query
        //String selectQuery = "SELECT  * FROM " + TABLE_TRAININGS;
        String selectQuery = "SELECT  * FROM " + TABLE_HERBICIDES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                FoliarFeed feed = new FoliarFeed();
                feed.setInputID(cursor.getString(cursor.getColumnIndex(KEY_INPUT_ID)));
                feed.setInputType(cursor.getString(cursor.getColumnIndex(KEY_INPUT_TYPE)));

                // Adding farmer to list
                feedList.add(feed);
            } while (cursor.moveToNext());
        }
        return feedList;
    }

    // Getting All Farmers
    public List<Farmers> getAllFarmers() {
        List<Farmers> contactList = new ArrayList<Farmers>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_FARMERS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Farmers farmer = new Farmers();
                farmer.setID(Integer.parseInt(cursor.getString(0)));
                farmer.setFName(cursor.getString(1));
                farmer.setLName(cursor.getString(2));
                farmer.setGender(cursor.getString(3));
                farmer.setIDNO(cursor.getString(4));
                farmer.setPhoneNumber(cursor.getString(5));
                farmer.setEmail(cursor.getString(6));
                farmer.setPost(cursor.getString(7));
                farmer.setVillage(cursor.getString(8));
                farmer.setSubVillage(cursor.getString(9));
                farmer.setPicPath(cursor.getString(10));
                farmer.setLat(cursor.getString(cursor.getColumnIndex(KEY_LAT)));
                farmer.setLongt(cursor.getString(cursor.getColumnIndex(KEY_LONGT)));
                farmer.setEstFarmArea(cursor.getString(cursor.getColumnIndex(KEY_EST_FARM_AREA)));
                farmer.setRegDate(cursor.getString(cursor.getColumnIndex(KEY_REG_DATE)));
                farmer.setCID(cursor.getString(14));
                // Adding farmer to list
                contactList.add(farmer);
            } while (cursor.moveToNext());
        }
        return contactList;
    }

    // Getting All Inputs
    public List<InputsFromServer> getAllInputs() {
        List<InputsFromServer> inputsList = new ArrayList<InputsFromServer>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_FARMINPUTS + " GROUP BY " + KEY_INPUT_CATEGORY;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                InputsFromServer inputs = new InputsFromServer();
                inputs.setInputId(cursor.getString(1));
                inputs.setInputCat(cursor.getString(2));
                inputs.setInputType(cursor.getString(3));

                inputsList.add(inputs);
            } while (cursor.moveToNext());
        }
        // return farmer list
        return inputsList;
    }

    public Cursor getAllAssignedInputs() {
        // Select Query
        String selectQuery = "SELECT  *," + KEY_ID + " as _id FROM " + TABLE_ASSIGNED_INPUTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // return farmer list
        return cursor;
    }

    public List<Training> getAllAssignedTrainings() {
        List<Training> assignedTrainingList = new ArrayList<Training>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_ASSIGNED_TRAININGS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Training assignedTrainings = new Training();
                //assignedInputs.setInputId(cursor.getString(cursor.getColumnIndex(KEY_INPUT_ID)));
                assignedTrainings.setTID(cursor.getString(cursor.getColumnIndex(KEY_TRAIN_ID)));
                assignedTrainings.setTStartTime(cursor.getString(cursor.getColumnIndex(KEY_TRAIN_START_TIME)));
                assignedTrainings.setTStopTime(cursor.getString(cursor.getColumnIndex(KEY_TRAIN_STOP_TIME)));
                assignedTrainings.setTLat(cursor.getString(cursor.getColumnIndex(KEY_TRAIN_LAT)));
                assignedTrainings.setTLongt(cursor.getString(cursor.getColumnIndex(KEY_TRAIN_LONGT)));
                assignedTrainings.setCID(cursor.getString(cursor.getColumnIndex(KEY_COMPANY_ID)));

                assignedTrainingList.add(assignedTrainings);
            } while (cursor.moveToNext());
        }
        // return farmer list
        return assignedTrainingList;
    }

    // Updating single farmer
    public int updateFarmer(Farmers farmer) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_FNAME, farmer.getLName());
        values.put(KEY_PH_NO, farmer.getPhoneNumber());

        // updating row
        return db.update(TABLE_FARMERS, values, KEY_ID + " = ?",
                new String[]{String.valueOf(farmer.getID())});
    }

    // Deleting single farmer
    public void deleteContact(Farmers farmer) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_FARMERS, KEY_ID + " = ?",
                new String[]{String.valueOf(farmer.getID())});
        db.close();
    }

    public int getFarmersCount() {
        int count = 0;
        String countQuery = "SELECT  * FROM " + TABLE_FARMERS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        if (cursor != null && !cursor.isClosed()) {
            count = cursor.getCount();

        }
        cursor.close();
        db.close();
        return count;
    }

    public int getAssignedCount() {
        int count = 0;
        String countQuery = "SELECT  * FROM " + TABLE_ASSIGNED_INPUTS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        if (cursor != null && !cursor.isClosed()) {
            count = cursor.getCount();
            cursor.close();
        }
        return count;
    }

    public List<InputsFromServer> getDynamicInputs(String cat) {
        List<InputsFromServer> inputsList = new ArrayList<InputsFromServer>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_FARMINPUTS + " WHERE " + KEY_INPUT_CATEGORY + " = '" + cat + "'";
        Log.e("Q inputs types: ", selectQuery);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                InputsFromServer inputs = new InputsFromServer();
                inputs.setInputId(cursor.getString(1));
                inputs.setInputCat(cursor.getString(2));
                inputs.setInputType(cursor.getString(cursor.getColumnIndex(KEY_INPUT_TYPE)));

                // Adding farmer to list
                inputsList.add(inputs);
            } while (cursor.moveToNext());
        }

        return inputsList;
    }

    public List<Countries> getAllDynamicCountries(String region_id) {
        List<Countries> countriesList = new ArrayList<Countries>();
        List<Countries> countriesList2 = new ArrayList<Countries>();
        // Select All Query
        String selectQuery = "SELECT  " + KEY_COUNTRY_ID + " FROM " + TABLE_REGIONS + " WHERE " + KEY_REGION_ID + " = '" + region_id + "'";
        Log.e("Q Countries: ", selectQuery);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Countries country = new Countries();
                country.setCountryID(cursor.getString(cursor.getColumnIndex(KEY_COUNTRY_ID)));

                countriesList.add(country);
            } while (cursor.moveToNext());
        }
        if (countriesList.size() > 0) {
            String selectQuery2 = "SELECT  * FROM " + TABLE_COUNTRIES + " WHERE " + KEY_COUNTRY_ID + " = '" + countriesList.get(0).getCountryID() + "'";
            Log.e("Q Regions: ", selectQuery2);
            // SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor2 = db.rawQuery(selectQuery2, null);

            // looping through all rows and adding to list
            if (cursor2.moveToFirst()) {
                do {
                    Countries country = new Countries();
                    country.setCountryID(cursor2.getString(cursor2.getColumnIndex(KEY_COUNTRY_ID)));
                    country.setCountryName(cursor2.getString(cursor2.getColumnIndex(KEY_COUNTRY_NAME)));

                    countriesList2.add(country);
                } while (cursor2.moveToNext());
            }

            return countriesList2;
        } else {
            return null;
        }
    }

    public List<Regions> getDynamicRegions(String district_id) {
        List<Regions> regionList = new ArrayList<Regions>();
        List<Regions> regionList2 = new ArrayList<Regions>();
        // Select All Query
        String selectQuery = "SELECT  " + KEY_REGION_ID + " FROM " + TABLE_DISTRICTS + " WHERE " + KEY_DISTRICT_ID + " = '" + district_id + "'";
        Log.e("Q Regions: ", selectQuery);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Regions region = new Regions();
                region.setRegionID(cursor.getString(cursor.getColumnIndex(KEY_REGION_ID)));

                regionList.add(region);
            } while (cursor.moveToNext());
        }
        if (regionList.size() > 0) {
            String selectQuery2 = "SELECT  * FROM " + TABLE_REGIONS + " WHERE " + KEY_REGION_ID + " = '" + regionList.get(0).getRegionID() + "'";
            Log.e("Q Regions: ", selectQuery2);
            // SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor2 = db.rawQuery(selectQuery2, null);

            // looping through all rows and adding to list
            if (cursor2.moveToFirst()) {
                do {
                    Regions region = new Regions();
                    region.setRegionID(cursor2.getString(cursor2.getColumnIndex(KEY_REGION_ID)));
                    region.setRegionName(cursor2.getString(cursor2.getColumnIndex(KEY_REGION_NAME)));

                    regionList2.add(region);
                } while (cursor2.moveToNext());
            }

            return regionList2;
        } else {
            return null;
        }
    }

    public List<Districts> getDynamicDistricts(String ward_id) {
        List<Districts> dwistrictList = new ArrayList<Districts>();
        List<Districts> dwistrictList2 = new ArrayList<Districts>();
        // Select All Query
        String selectQuery = "SELECT  " + KEY_DISTRICT_ID + " FROM " + TABLE_WARDS + " WHERE " + KEY_WARD_ID + " = '" + ward_id + "'";
        Log.e("Q Districts: ", selectQuery);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Districts district = new Districts();
                district.setDistrictID(cursor.getString(cursor.getColumnIndex(KEY_DISTRICT_ID)));

                dwistrictList.add(district);
            } while (cursor.moveToNext());
        }
        if (dwistrictList.size() > 0) {
            String selectQuery2 = "SELECT  * FROM " + TABLE_DISTRICTS + " WHERE " + KEY_DISTRICT_ID + " = '" + dwistrictList.get(0).getDistrictID() + "'";
            Log.e("Q Districts: ", selectQuery2);
            // SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor2 = db.rawQuery(selectQuery2, null);

            // looping through all rows and adding to list
            if (cursor2.moveToFirst()) {
                do {
                    Districts district = new Districts();
                    district.setDistrictID(cursor2.getString(cursor2.getColumnIndex(KEY_DISTRICT_ID)));
                    district.setDistrictName(cursor2.getString(cursor2.getColumnIndex(KEY_DISTRICT_NAME)));

                    dwistrictList2.add(district);
                } while (cursor2.moveToNext());
            }

            return dwistrictList2;
        } else {
            return null;
        }
    }

    public List<Wards> getDynamicWards(String village_id) {
        List<Wards> wardList = new ArrayList<Wards>();
        List<Wards> wardList2 = new ArrayList<Wards>();
        // Select All Query
        String selectQuery = "SELECT  wardid FROM " + TABLE_VILLAGES + " WHERE " + KEY_VILLAGE_ID + " = '" + village_id + "'";
        Log.e("Q Wards: ", selectQuery);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Wards ward = new Wards();
                ward.setWardID(cursor.getString(cursor.getColumnIndex(KEY_WARD_ID)));

                wardList.add(ward);
            } while (cursor.moveToNext());
        }
        if (wardList.size() > 0) {
            String selectQuery2 = "SELECT  * FROM " + TABLE_WARDS + " WHERE " + KEY_WARD_ID + " = '" + wardList.get(0).getWardID() + "'";
            Log.e("Q Wards: ", selectQuery2);
            // SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor2 = db.rawQuery(selectQuery2, null);

            // looping through all rows and adding to list
            if (cursor2.moveToFirst()) {
                do {
                    Wards ward = new Wards();
                    ward.setWardID(cursor2.getString(cursor2.getColumnIndex(KEY_WARD_ID)));
                    ward.setWardName(cursor2.getString(cursor2.getColumnIndex(KEY_WARD_NAME)));

                    wardList2.add(ward);
                } while (cursor2.moveToNext());
            }

            return wardList2;
        } else {
            return null;
        }
    }

    public List<Villages> getDynamicVillages(String ward) {
        List<Villages> villageList = new ArrayList<Villages>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_VILLAGES + " WHERE " + KEY_WARD_ID + " = '" + ward + "'";
        Log.e("Q Villages: ", selectQuery);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Villages village = new Villages();
                village.setVillageID(cursor.getString(cursor.getColumnIndex(KEY_VILLAGE_ID)));
                village.setVillageName(cursor.getString(cursor.getColumnIndex(KEY_VILLAGE_NAME)));

                villageList.add(village);
            } while (cursor.moveToNext());
        }

        return villageList;
    }

    public List<SubVillages> getDynamicSubVillages(String village) {
        List<SubVillages> subVillageList = new ArrayList<SubVillages>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_SUBVILLAGES + " WHERE " + KEY_VILLAGE_ID + " = '" + village + "'";
        Log.e("Q Sub Villages: ", selectQuery);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                SubVillages subvillage = new SubVillages();
                subvillage.setSubVillageID(cursor.getString(cursor.getColumnIndex(KEY_SUBVILLAGE_ID)));
                subvillage.setSubVillageName(cursor.getString(cursor.getColumnIndex(KEY_SUBVILLAGE_NAME)));

                subVillageList.add(subvillage);
            } while (cursor.moveToNext());
        }

        return subVillageList;
    }

    public void clearFarmers() {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("delete from " + TABLE_FARMERS);
    }

    public void clearInputs() {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("delete from " + TABLE_FARMINPUTS);
    }

    public void clearCountries() {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("delete from " + TABLE_COUNTRIES);
    }

    public void clearRegions() {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("delete from " + TABLE_REGIONS);
    }

    public void clearDistricts() {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("delete from " + TABLE_DISTRICTS);
    }

    public void clearWards() {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("delete from " + TABLE_WARDS);
    }

    public void clearVillages() {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("delete from " + TABLE_VILLAGES);
    }

    public void clearSubvillages() {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("delete from " + TABLE_SUBVILLAGES);
    }

    public void clearAsignedInputs() {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("delete from " + TABLE_ASSIGNED_INPUTS);
    }

    public void clearTrainings() {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("delete from " + TABLE_TRAININGS);
    }

    public int getAssignedTrainsCount() {
        int count = 0;
        String countQuery = "SELECT  * FROM " + TABLE_ASSIGNED_TRAININGS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        if (cursor != null && !cursor.isClosed()) {
            count = cursor.getCount();
            cursor.close();
        }
        return count;
    }

    public void clearAssignedTrainings() {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("delete from " + TABLE_ASSIGNED_TRAININGS);
    }

    public int getUserCount() {
        int count = 0;
        String countQuery = "SELECT  * FROM " + TABLE_USERS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        if (cursor != null && !cursor.isClosed()) {
            count = cursor.getCount();
            cursor.close();
        }
        return count;
    }

    public void clearCompanies() {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("delete from " + TABLE_COMPANIES);
    }

    public void clearUsers() {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("delete from " + TABLE_USERS);
    }

    public int getCompanyCount() {
        int count = 0;
        String countQuery = "SELECT  * FROM " + TABLE_COMPANIES;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        if (cursor != null && !cursor.isClosed()) {
            count = cursor.getCount();
            cursor.close();
        }
        return count;
    }

    public int getVillageCount() {
        int count = 0;
        String countQuery = "SELECT  * FROM " + TABLE_VILLAGES;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        if (cursor != null && !cursor.isClosed()) {
            count = cursor.getCount();
            cursor.close();
        }
        db.close();
        return count;
    }

    public List<Villages> getVillages() {
        List<Villages> villageList = new ArrayList<Villages>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_VILLAGES;
        Log.e("Q Villages: ", selectQuery);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Villages village = new Villages();
                village.setVillageID(cursor.getString(cursor.getColumnIndex(KEY_VILLAGE_ID)));
                village.setVillageName(cursor.getString(cursor.getColumnIndex(KEY_VILLAGE_NAME)));

                villageList.add(village);
            } while (cursor.moveToNext());
        }

        return villageList;
    }

    public Cursor fetchGroup() {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  tcatid,train_cat," + KEY_ID + " as _id FROM " + TABLE_TRAIN_CATEGORIES;
        Cursor cursor = db.rawQuery(selectQuery, null);
        Log.e("", selectQuery);
        return cursor;

    }

    public Cursor fetchTrainCats(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  " + TABLE_TRAIN_TYPES + "." + KEY_TRAIN_ID + ","
                + "" + TABLE_TRAIN_TYPES + "." + KEY_TRAIN_TYPE + ","
                + "" + TABLE_TRAIN_TYPES + "." + KEY_ID + " as _id "
                + "FROM " + TABLE_TRAIN_TYPES + ""
                + " INNER JOIN " + TABLE_TRAININGS + " "
                + "ON " + TABLE_TRAIN_TYPES + "." + KEY_TRAIN_ID + " = " + TABLE_TRAININGS + "." + KEY_TRAIN_ID + ""
                + " INNER JOIN " + TABLE_TRAIN_CATEGORIES + " "
                + "ON " + TABLE_TRAIN_TYPES + "." + KEY_TRAIN_CAT_ID + " = " + TABLE_TRAIN_CATEGORIES + ".'" + KEY_TRAIN_CAT_ID + "'"
                + " WHERE " + TABLE_TRAIN_TYPES + "." + KEY_TRAIN_CAT_ID + " = '" + id + "' "
                + "AND DATE(" + TABLE_TRAININGS + "." + KEY_TRAIN_DATE + ") = DATE('NOW')";
        Cursor cursor = db.rawQuery(selectQuery, null);
        Log.e("", selectQuery);
        return cursor;
    }

    public void addTrainingCat(Training training) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TRAIN_CAT_ID, training.getTCatID2());
        values.put(KEY_TRAIN_CAT, training.getTCat2());

        db.insert(TABLE_TRAIN_CATEGORIES, null, values);
        db.close();
    }

    public void addTrainingType(TrainingType training) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TRAIN_CAT_ID, training.getTcatID());
        values.put(KEY_TRAIN_ID, training.getTID());
        values.put(KEY_TRAIN_TYPE, training.getTType());

        db.insert(TABLE_TRAIN_TYPES, null, values);
        db.close();
    }

    public void clearTrainingsTypes() {

        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("delete from " + TABLE_TRAIN_TYPES);
    }

    public void clearTrainingsCats() {

        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("delete from " + TABLE_TRAIN_CATEGORIES);
    }

    public List<Training> getAllTrainingsCats() {
        List<Training> trainingList = new ArrayList<Training>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_TRAIN_CATEGORIES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Training training = new Training();
                training.setTCatID2(cursor.getString(cursor.getColumnIndex(KEY_TRAIN_CAT_ID)));
                training.setTCat2(cursor.getString(cursor.getColumnIndex(KEY_TRAIN_CAT)));

                // Adding farmer to list
                trainingList.add(training);
            } while (cursor.moveToNext());
        }
        return trainingList;
    }

    /**
     *
     * @return TrainType List all Training Types
     */
    public List<TrainingType> getAllTrainingsTypes() {
        List<TrainingType> trainingTypeList = new ArrayList<TrainingType>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_TRAIN_TYPES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                TrainingType training = new TrainingType();
                training.setTrainCatID(cursor.getString(cursor.getColumnIndex(KEY_TRAIN_CAT_ID)));
                training.setTID(cursor.getString(cursor.getColumnIndex(KEY_TRAIN_ID)));
                training.setTType(cursor.getString(cursor.getColumnIndex(KEY_TRAIN_TYPE)));

                // Adding training type to list
                trainingTypeList.add(training);
            } while (cursor.moveToNext());
        }
        return trainingTypeList;
    }

    public void addFarmerTimes(FarmerTime farmerTime) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_FID, farmerTime.getFID());
        values.put(KEY_FARMER_TIME, farmerTime.getFTime());
        values.put(KEY_USER_ID, farmerTime.getUserID());

        db.insert(TABLE_FARMER_TIMES, null, values);
        db.close();
    }

    public List<FarmerTime> getAllFarmerTimes() {
        List<FarmerTime> allFarmerTimes = new ArrayList<FarmerTime>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_FARMER_TIMES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                FarmerTime times = new FarmerTime();
                times.setFID(cursor.getString(cursor.getColumnIndex(KEY_FID)));
                times.setFarmerTime(cursor.getString(cursor.getColumnIndex(KEY_FARMER_TIME)));
                times.setUserID(cursor.getString(cursor.getColumnIndex(KEY_USER_ID)));
                // Adding farmer to list
                allFarmerTimes.add(times);
            } while (cursor.moveToNext());
        }
        db.close();
        return allFarmerTimes;
    }

    public int getTrainingCount() {

        int count = 0;
        String countQuery = "SELECT  * FROM " + TABLE_TRAININGS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        if (cursor != null && !cursor.isClosed()) {
            count = cursor.getCount();
            cursor.close();
        }
        db.close();
        return count;
    }

    public void updateTrainingStatus(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String updateQuery = "UPDATE " + TABLE_TRAININGS + " SET " + KEY_TRAIN_STATUS + " = '0' WHERE " + KEY_TRAIN_ID + " = '" + id + "'";
        Log.e("SQL: ", updateQuery);
        db.execSQL(updateQuery);
        //db.close();
    }

    public void deleteTraining(String tid) {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("delete from " + TABLE_TRAININGS + " WHERE " + KEY_TRAIN_ID + " = '" + tid + "'");
    }

    public void addFarm(Farm farm) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_FARM_ID, farm.getFarmID());
        values.put(KEY_FARM_NAME, farm.getFarmName());
        values.put(KEY_FARM_SIZE, farm.getFarmSize());
        values.put(KEY_FARM_PERI, farm.getFarmPeri());
        values.put(KEY_COMPANY_ID, farm.getCID());

        db.insert(TABLE_FARMS, null, values);
        db.close();
    }

    public void addFingerOne(FingerOneBack finger) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_COMPANY_ID, finger.getCID());
        values.put(KEY_USER_ID, finger.getUserID());
        values.put(KEY_FARM_ID, finger.getFarmID());
        values.put(KEY_SOIL_TYPE, finger.getSoilType());
        values.put(KEY_WATER_LOG_RISK, finger.getWLRisk());// Farmers Name
        values.put(KEY_EROSION_PREV, finger.getEPrev());
        values.put(KEY_CROP_ROTATION, finger.getCRotation());
        values.put(KEY_RATOON, finger.getRatoon()); // Farmers Phone
        values.put(KEY_CROP_RESIDUES, finger.getCResidues());
        values.put(KEY_MANURE, finger.getManure());
        values.put(KEY_LAND_PREP, finger.getLPrep());
        values.put(KEY_SEED_BED_PREP, finger.getSBedPrep());
        values.put(KEY_FORM_DATE, finger.getFormDate());

        // Inserting Row
        db.insert(TABLE_FINGER_ONE, null, values);
        db.close(); // Closing database connection
    }

    public void addFingerTwo(FingerTwoBack finger) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_COMPANY_ID, finger.getCID());
        values.put(KEY_USER_ID, finger.getUserID());
        values.put(KEY_FARM_ID, finger.getFarmID());
        values.put(KEY_CORRECT_SEED, finger.getCorectSeed());
        values.put(KEY_ROW_SPACING, finger.getRowSpacing());// Farmers Name
        values.put(KEY_SEED_PER_STAT, finger.getSeedPerStat());
        values.put(KEY_PLANTING_TIME, finger.getPlantingTIme());
        values.put(KEY_FORM_DATE, finger.getFormDate());

        // Inserting Row
        db.insert(TABLE_FINGER_TWO, null, values);
        db.close(); // Closing database connection
    }

    public void addFingerThree(FingerThreeBack finger) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_COMPANY_ID, finger.getCID());
        values.put(KEY_USER_ID, finger.getUserID());
        values.put(KEY_FARM_ID, finger.getFarmID());
        values.put(KEY_GAP_FILL, finger.getGapFill());
        values.put(KEY_GAP_FILL_EMER, finger.getFillOnEmer());// Farmers Name
        values.put(KEY_THIN_NUM, finger.getThinNum());
        values.put(KEY_THIN_NUM_EMER, finger.getThinNumEmer());
        values.put(KEY_FORM_DATE, finger.getFormDate());

        // Inserting Row
        db.insert(TABLE_FINGER_THREE, null, values);
        db.close(); // Closing database connection
    }

    public void addFingerFour(FingerFourBack finger) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_COMPANY_ID, finger.getCID());
        values.put(KEY_USER_ID, finger.getUserID());
        values.put(KEY_FARM_ID, finger.getFarmID());
        values.put(KEY_FIRST_BRANCH, finger.getFirstBranch());
        values.put(KEY_FOLIAR, finger.getFoliar());// Farmers Name
        values.put(KEY_WEEDS, finger.getWeeds());
        values.put(KEY_FORM_DATE, finger.getFormDate());

        // Inserting Row
        db.insert(TABLE_FINGER_FOUR, null, values);
        db.close(); // Closing database connection
    }

    public void addFingerFive(FingerFiveBack finger) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_COMPANY_ID, finger.getCID());
        values.put(KEY_USER_ID, finger.getUserID());
        values.put(KEY_FARM_ID, finger.getFarmID());
        values.put(KEY_PEST_LEVEL, finger.getPestLevel());
        values.put(KEY_PEST_DAMAGE, finger.getPestDamage());// Farmers Name
        values.put(KEY_LAST_SCOUT, finger.getLastScout());
        values.put(KEY_EMPTY_CANS, finger.getEmptyCans());
        values.put(KEY_PEG_BOARD_AVAIL, finger.getPegBoardAvail()); // Farmers Phone
        values.put(KEY_SCOUT_METHOD, finger.getScoutMethod());
        values.put(KEY_SPRAY_TIME, finger.getSprayTime());
        values.put(KEY_PEST_ABS_DURATION, finger.getPestAbsDur());
        values.put(KEY_CORRECT_USE_PESTICIDE, finger.getCorrectPestUse());
        values.put(KEY_SAFE_USAGE_CANS, finger.getSafeUsageCans());
        values.put(KEY_FORM_DATE, finger.getFormDate());

        // Inserting Row
        db.insert(TABLE_FINGER_FIVE, null, values);
        db.close(); // Closing database connection
    }

    public List<FingerOneBack> getAllFingerOne() {
        List<FingerOneBack> allFingerOnes = new ArrayList<FingerOneBack>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_FINGER_ONE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                FingerOneBack finger = new FingerOneBack();
                finger.setCID(cursor.getString(cursor.getColumnIndex(KEY_COMPANY_ID)));
                finger.setUserID(cursor.getString(cursor.getColumnIndex(KEY_USER_ID)));
                finger.setFarmID(cursor.getString(cursor.getColumnIndex(KEY_FARM_ID)));
                finger.setSoilType(cursor.getString(cursor.getColumnIndex(KEY_SOIL_TYPE)));
                finger.setWLRisk(cursor.getString(cursor.getColumnIndex(KEY_WATER_LOG_RISK)));
                finger.setErosionPrev(cursor.getString(cursor.getColumnIndex(KEY_EROSION_PREV)));
                finger.setCropRotation(cursor.getString(cursor.getColumnIndex(KEY_CROP_ROTATION)));
                finger.setRatoon(cursor.getString(cursor.getColumnIndex(KEY_RATOON)));
                finger.setCropResidues(cursor.getString(cursor.getColumnIndex(KEY_CROP_RESIDUES)));
                finger.setManure(cursor.getString(cursor.getColumnIndex(KEY_MANURE)));
                finger.setLandPrep(cursor.getString(cursor.getColumnIndex(KEY_LAND_PREP)));
                finger.setSBedPrep(cursor.getString(cursor.getColumnIndex(KEY_SEED_BED_PREP)));
                // Adding farmer to list
                allFingerOnes.add(finger);
            } while (cursor.moveToNext());
        }
        db.close();
        return allFingerOnes;
    }

    public List<FingerTwoBack> getAllFingerTwo() {
        List<FingerTwoBack> allFingerOnes = new ArrayList<FingerTwoBack>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_FINGER_TWO;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                FingerTwoBack finger = new FingerTwoBack();
                finger.setCID(cursor.getString(cursor.getColumnIndex(KEY_COMPANY_ID)));
                finger.setUserID(cursor.getString(cursor.getColumnIndex(KEY_USER_ID)));
                finger.setFarmID(cursor.getString(cursor.getColumnIndex(KEY_FARM_ID)));
                finger.setCorrectSeed(cursor.getString(cursor.getColumnIndex(KEY_CORRECT_SEED)));
                finger.setRowSpacing(cursor.getString(cursor.getColumnIndex(KEY_ROW_SPACING)));
                finger.setSeedPerStat(cursor.getString(cursor.getColumnIndex(KEY_SEED_PER_STAT)));
                finger.setPlantingTime(cursor.getString(cursor.getColumnIndex(KEY_PLANTING_TIME)));
                allFingerOnes.add(finger);
            } while (cursor.moveToNext());
        }
        db.close();
        return allFingerOnes;
    }

    public List<FingerThreeBack> getAllFingerThree() {
        List<FingerThreeBack> allFingerThree = new ArrayList<FingerThreeBack>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_FINGER_THREE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                FingerThreeBack finger = new FingerThreeBack();
                finger.setCID(cursor.getString(cursor.getColumnIndex(KEY_COMPANY_ID)));
                finger.setUserID(cursor.getString(cursor.getColumnIndex(KEY_USER_ID)));
                finger.setFarmID(cursor.getString(cursor.getColumnIndex(KEY_FARM_ID)));
                finger.setGapFill(cursor.getString(cursor.getColumnIndex(KEY_GAP_FILL)));
                finger.setGapFillEmer(cursor.getString(cursor.getColumnIndex(KEY_GAP_FILL_EMER)));
                finger.setThinNum(cursor.getString(cursor.getColumnIndex(KEY_THIN_NUM)));
                finger.setThinNumEmer(cursor.getString(cursor.getColumnIndex(KEY_THIN_NUM_EMER)));
                allFingerThree.add(finger);
            } while (cursor.moveToNext());
        }
        db.close();
        return allFingerThree;
    }

    public List<FingerFourBack> getAllFingerFour() {
        List<FingerFourBack> allFingerFour = new ArrayList<FingerFourBack>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_FINGER_FOUR;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                FingerFourBack finger = new FingerFourBack();
                finger.setCID(cursor.getString(cursor.getColumnIndex(KEY_COMPANY_ID)));
                finger.setUserID(cursor.getString(cursor.getColumnIndex(KEY_USER_ID)));
                finger.setFarmID(cursor.getString(cursor.getColumnIndex(KEY_FARM_ID)));
                finger.setFirstBranch(cursor.getString(cursor.getColumnIndex(KEY_FIRST_BRANCH)));
                finger.setFoliar(cursor.getString(cursor.getColumnIndex(KEY_FOLIAR)));
                finger.setWeeds(cursor.getString(cursor.getColumnIndex(KEY_WEEDS)));
                allFingerFour.add(finger);
            } while (cursor.moveToNext());
        }
        db.close();
        return allFingerFour;
    }

    public List<FingerFiveBack> getAllFingerFive() {
        List<FingerFiveBack> allFingerFive = new ArrayList<FingerFiveBack>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_FINGER_FIVE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                FingerFiveBack finger = new FingerFiveBack();
                finger.setCID(cursor.getString(cursor.getColumnIndex(KEY_COMPANY_ID)));
                finger.setUserID(cursor.getString(cursor.getColumnIndex(KEY_USER_ID)));
                finger.setFarmID(cursor.getString(cursor.getColumnIndex(KEY_FARM_ID)));
                finger.setPestLevel(cursor.getString(cursor.getColumnIndex(KEY_PEST_LEVEL)));
                finger.setPestDamage(cursor.getString(cursor.getColumnIndex(KEY_PEST_DAMAGE)));
                finger.setLastScout(cursor.getString(cursor.getColumnIndex(KEY_LAST_SCOUT)));
                finger.setEmptyCans(cursor.getString(cursor.getColumnIndex(KEY_EMPTY_CANS)));
                finger.setPegBoardAvail(cursor.getString(cursor.getColumnIndex(KEY_PEG_BOARD_AVAIL)));
                finger.setScoutMethod(cursor.getString(cursor.getColumnIndex(KEY_SCOUT_METHOD)));
                finger.setSprayTime(cursor.getString(cursor.getColumnIndex(KEY_SPRAY_TIME)));
                finger.setPestADur(cursor.getString(cursor.getColumnIndex(KEY_PEST_ABS_DURATION)));
                finger.setPestCUse(cursor.getString(cursor.getColumnIndex(KEY_CORRECT_USE_PESTICIDE)));
                finger.setSafeCanUsage(cursor.getString(cursor.getColumnIndex(KEY_SAFE_USAGE_CANS)));
                // Adding farmer to list
                allFingerFive.add(finger);
            } while (cursor.moveToNext());
        }
        db.close();
        return allFingerFive;
    }

    public int getFingerOneCount() {
        int count = 0;
        String countQuery = "SELECT  * FROM " + TABLE_FINGER_ONE;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        if (cursor != null && !cursor.isClosed()) {
            count = cursor.getCount();
            cursor.close();
        }
        db.close();
        return count;
    }

    public int getFingerTwoCount() {
        int count = 0;
        String countQuery = "SELECT  * FROM " + TABLE_FINGER_TWO;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        if (cursor != null && !cursor.isClosed()) {
            count = cursor.getCount();
            cursor.close();
        }
        db.close();
        return count;
    }

    public int getFingerThreeCount() {
        int count = 0;
        String countQuery = "SELECT  * FROM " + TABLE_FINGER_THREE;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        if (cursor != null && !cursor.isClosed()) {
            count = cursor.getCount();
            cursor.close();
        }
        db.close();
        return count;
    }

    public int getFingerFourCount() {
        int count = 0;
        String countQuery = "SELECT  * FROM " + TABLE_FINGER_FOUR;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        if (cursor != null && !cursor.isClosed()) {
            count = cursor.getCount();
            cursor.close();
        }
        db.close();
        return count;
    }

    public int getFingerFiveCount() {
        int count = 0;
        String countQuery = "SELECT  * FROM " + TABLE_FINGER_FIVE;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        if (cursor != null && !cursor.isClosed()) {
            count = cursor.getCount();
            cursor.close();
        }
        db.close();
        return count;
    }

    public List<Farm> getAllFarms() {
        List<Farm> allFarms = new ArrayList<Farm>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_FARMS;
        Log.e("Farms SQL: ", selectQuery);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Farm farm = new Farm();
                farm.setFarmID(cursor.getString(cursor.getColumnIndex(KEY_FARM_ID)));
                farm.setFarmName(cursor.getString(cursor.getColumnIndex(KEY_FARM_NAME)));
                farm.setFarmSize(cursor.getString(cursor.getColumnIndex(KEY_FARM_SIZE)));
                farm.setFarmPeri(cursor.getString(cursor.getColumnIndex(KEY_FARM_PERI)));
                farm.setCID(cursor.getString(cursor.getColumnIndex(KEY_COMPANY_ID)));

                allFarms.add(farm);
            } while (cursor.moveToNext());
        }
        db.close();
        return allFarms;
    }

    public List<Farm> getAllFarms2(String gen_id) {
        List<Farm> allFarms = new ArrayList<Farm>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_FARMS + " WHERE " + KEY_FARM_NAME + " = '" + gen_id + "'";
        Log.e("Farms SQL: ", selectQuery);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Farm farm = new Farm();
                farm.setFarmID(cursor.getString(cursor.getColumnIndex(KEY_FARM_ID)));
                farm.setFarmName(cursor.getString(cursor.getColumnIndex(KEY_FARM_NAME)));
                farm.setFarmSize(cursor.getString(cursor.getColumnIndex(KEY_FARM_SIZE)));
                farm.setFarmPeri(cursor.getString(cursor.getColumnIndex(KEY_FARM_PERI)));
                farm.setCID(cursor.getString(cursor.getColumnIndex(KEY_COMPANY_ID)));

                allFarms.add(farm);
            } while (cursor.moveToNext());
        }
        db.close();
        return allFarms;
    }

    public int getFarmCount() {
        int count = 0;
        String countQuery = "SELECT  * FROM " + TABLE_FARMS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        if (cursor != null && !cursor.isClosed()) {
            count = cursor.getCount();
            cursor.close();
        }
        db.close();
        return count;
    }

    public int getFarmersTimeCount() {
        int count = 0;
        String countQuery = "SELECT  * FROM " + TABLE_FARMER_TIMES;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        if (cursor != null && !cursor.isClosed()) {
            count = cursor.getCount();
            cursor.close();
        }
        db.close();
        return count;
    }

    public void clearFingerOne() {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("delete from " + TABLE_FINGER_ONE);
    }

    public void clearFingerFive() {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("delete from " + TABLE_FINGER_FIVE);
        db.close();
    }

    public void clearFingerFour() {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("delete from " + TABLE_FINGER_FOUR);
        db.close();
    }

    public void clearFingerThree() {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("delete from " + TABLE_FINGER_THREE);
    }

    public void clearFingerTwo() {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("delete from " + TABLE_FINGER_TWO);
    }

    public void clearFarms() {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("delete from " + TABLE_FARMS);
    }

    public List<TrainingType> getMyTrainings(String date) {
        List<TrainingType> trainingsList = new ArrayList<TrainingType>();
        // Select All Query
        //String selectQuery = "SELECT  * FROM " + TABLE_TRAININGS;
        String selectQuery = "SELECT  * FROM " + TABLE_TRAIN_TYPES + ""
                + " INNER JOIN " + TABLE_TRAININGS + " "
                + " ON " + TABLE_TRAIN_TYPES + "." + KEY_TRAIN_ID + " = " + TABLE_TRAININGS + "." + KEY_TRAIN_ID + ""
                + " WHERE DATE(" + TABLE_TRAININGS + "." + KEY_TRAIN_DATE + ") = '" + date + "'";
        Log.e("SCHEDULE SQL:", selectQuery);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                TrainingType training = new TrainingType();
                training.setTType(cursor.getString(cursor.getColumnIndex(KEY_TRAIN_TYPE)));
                training.setTID(cursor.getString(cursor.getColumnIndex(KEY_TRAIN_ID)));
                trainingsList.add(training);
            } while (cursor.moveToNext());
        }
        db.close();
        return trainingsList;
    }

    public List<Training2> getAllTrainingDates() {
        List<Training2> allTrainingDates = new ArrayList<Training2>();
        // Select All Query
        //String selectQuery = "SELECT  * FROM " + TABLE_TRAININGS;
        String selectQuery = "SELECT date(" + KEY_TRAIN_DATE + ") as train_date FROM " + TABLE_TRAININGS;
        Log.e("SCHEDULE SQL:", selectQuery);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Training2 training = new Training2();
                training.setTDate1(cursor.getString(cursor.getColumnIndex(KEY_TRAIN_DATE)));

                // Adding farmer to list
                allTrainingDates.add(training);
            } while (cursor.moveToNext());
        }
        db.close();
        return allTrainingDates;
    }

    public List<Farm> getMyTrainingLocations(String id) {
        List<Farm> trainingsLocList = new ArrayList<Farm>();
        String farm_id = null;
        // Select All Query
        //String selectQuery = "SELECT  * FROM " + TABLE_TRAININGS;
        String selectQuery = "SELECT  " + KEY_FARM_ID + " FROM " + TABLE_TRAININGS + " WHERE " + KEY_TRAIN_ID + " = '" + id + "'";
        Log.e("LOCATION SCHEDULE SQL:", selectQuery);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Training2 train = new Training2();
                train.setFarmID(cursor.getString(cursor.getColumnIndex(KEY_FARM_ID)));
                farm_id = train.getFarmID();
                // Log.i("MyTrainLocations: ",farm_id);

            } while (cursor.moveToNext());
        }
        String selectQuery2 = "SELECT  * FROM " + TABLE_FARMS + " WHERE " + KEY_FARM_ID + " = '" + farm_id + "'";
        Log.e("LOCATION SCHEDULE SQL2:", selectQuery2);
        Cursor cursor2 = db.rawQuery(selectQuery2, null);
        if (cursor2.moveToFirst()) {
            do {
                Farm farm = new Farm();
                farm.setFarmName(cursor2.getString(cursor2.getColumnIndex(KEY_FARM_NAME)));
                farm.setLat(cursor2.getString(cursor2.getColumnIndex(KEY_LAT)));
                farm.setLongt(cursor2.getString(cursor2.getColumnIndex(KEY_LONGT)));
                // Adding farmer to list
                trainingsLocList.add(farm);
            } while (cursor2.moveToNext());
        }
        db.close();
        return trainingsLocList;
    }

    public void addSignedDoc(SignedDoc signed_doc) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_FID, signed_doc.getFID());
        values.put(KEY_SIGN_DATE, signed_doc.getSignDate());
        values.put(KEY_USER_ID, signed_doc.getUserID());
        values.put(KEY_COMPANY_ID, signed_doc.getCID());

        db.insert(TABLE_SIGNED_DOCS, null, values);
        db.close();
    }

    public List<SignedDoc> getAllSignedDocs() {
        List<SignedDoc> allSignedDocs = new ArrayList<SignedDoc>();
        // Select All Query
        //String selectQuery = "SELECT  * FROM " + TABLE_TRAININGS;
        String selectQuery = "SELECT  * FROM " + TABLE_SIGNED_DOCS;
        Log.e("SIGNED SQL:", selectQuery);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                SignedDoc doc = new SignedDoc();
                doc.setFID(cursor.getString(cursor.getColumnIndex(KEY_FID)));
                doc.setSignDate(cursor.getString(cursor.getColumnIndex(KEY_SIGN_DATE)));
                doc.setUserID(cursor.getString(cursor.getColumnIndex(KEY_USER_ID)));
                doc.setCID(cursor.getString(cursor.getColumnIndex(KEY_COMPANY_ID)));

                // Adding farmer to list
                allSignedDocs.add(doc);
            } while (cursor.moveToNext());
        }
        db.close();
        return allSignedDocs;
    }

    public int getSignedDocsCount() {
        int count = 0;
        String countQuery = "SELECT  * FROM " + TABLE_SIGNED_DOCS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        if (cursor != null && !cursor.isClosed()) {
            count = cursor.getCount();
            cursor.close();
        }
        db.close();
        return count;
    }

    public void clearSignedDocs() {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("delete from " + TABLE_SIGNED_DOCS);
    }

    public void clearFarmerTimes() {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("delete from " + TABLE_FARMER_TIMES);
    }

    public List<FarmerTime> getFarmerTimeID(String fid, String time) {
        List<FarmerTime> ids = new ArrayList<FarmerTime>();
        // Select All Query
        //String selectQuery = "SELECT  * FROM " + TABLE_TRAININGS;
        String selectQuery = "SELECT  " + KEY_ID + " as id FROM " + TABLE_FARMER_TIMES + " WHERE " + KEY_FARMER_TIME + " = '" + time + "' AND " + KEY_FID + " = '" + fid + "'";
        Log.e("SIGNED SQL:", selectQuery);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                FarmerTime doc = new FarmerTime();
                doc.setID(cursor.getString(cursor.getColumnIndex(KEY_ID)));

                // Adding farmer to list
                ids.add(doc);
            } while (cursor.moveToNext());
        }
        db.close();
        return ids;
    }

    public void addKmlSupportData(KmlMetaData meta_data) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_USER_ID, meta_data.getUserID());
        values.put(KEY_COMPANY_ID, meta_data.getCID());
        values.put(KEY_FID, meta_data.getFID());
        values.put(KEY_FARM_ID, meta_data.getFarmID());

        // Inserting Row
        db.insert(TABLE_KML_META_DATA, null, values);
        db.close(); // Closing database connection
    }

    public List<KmlMetaData> getAllKmlMeta(String farmer_id) {
        List<KmlMetaData> data = new ArrayList<KmlMetaData>();
        // Select All Query
        //String selectQuery = "SELECT  * FROM " + TABLE_TRAININGS;
        String selectQuery = "SELECT  * FROM " + TABLE_KML_META_DATA + " WHERE " + KEY_FID + " = '" + farmer_id + "'";
        Log.e("KML_META SQL:", selectQuery);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                KmlMetaData doc = new KmlMetaData();
                doc.setUserID(cursor.getString(cursor.getColumnIndex(KEY_USER_ID)));
                doc.setCID(cursor.getString(cursor.getColumnIndex(KEY_COMPANY_ID)));
                doc.setFID(cursor.getString(cursor.getColumnIndex(KEY_FID)));
                doc.setFarmID(cursor.getString(cursor.getColumnIndex(KEY_FARM_ID)));

                // Adding farmer to list
                data.add(doc);
            } while (cursor.moveToNext());
        }
        db.close();
        return data;
    }

    public void clearKmlMetaData() {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("delete from " + TABLE_KML_META_DATA);
    }

    public String getUserVillageID(String user_id) {
        String vid = null;
        // Select All Query
        String selectQuery = "SELECT  " + KEY_VILLAGE_ID + " FROM " + TABLE_USERS + " WHERE " + KEY_USER_ID + " = '" + user_id + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Users user = new Users();
                user.setUserID(cursor.getString(cursor.getColumnIndex(KEY_VILLAGE_ID)));

                // Adding farmer to list
                vid = user.getUserID();
            } while (cursor.moveToNext());
        }
        return vid;
    }

    public String getFarmArea(String farm_id) {
        String farm_area = null;
        // Select All Query
        String selectQuery = "SELECT  " + KEY_FARM_SIZE + " FROM " + TABLE_FARMS + " WHERE " + KEY_FARM_ID + " = '" + farm_id + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Farm farm = new Farm();
                farm.setFarmSize(cursor.getString(cursor.getColumnIndex(KEY_FARM_SIZE)));

                // Adding farmer to list
                farm_area = farm.getFarmSize();
            } while (cursor.moveToNext());
        }
        return farm_area;
    }

    public Cursor fetchInputsByGenID(String inputText) {
        Cursor mCursor = null;
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            if (inputText == null || inputText.length() == 0) {
                mCursor = db.query(TABLE_ASSIGNED_INPUTS, new String[]{KEY_ID + " as _id ", KEY_ASS_INPUT_ID, KEY_GEN_ID,
                    KEY_INPUT_TYPE, KEY_INPUT_PRICE, KEY_INPUT_UNIT, KEY_INPUT_TOTAL},
                        null, null, null, null, null, null);

            } else {
                mCursor = db.query(true, TABLE_ASSIGNED_INPUTS, new String[]{KEY_ID + " as _id ", KEY_ASS_INPUT_ID, KEY_GEN_ID,
                    KEY_INPUT_TYPE, KEY_INPUT_PRICE, KEY_INPUT_UNIT, KEY_INPUT_TOTAL},
                        KEY_GEN_ID + " like '%" + inputText + "%'", null,
                        null, null, null, null);
            }
            if (mCursor != null) {
                mCursor.moveToFirst();
            }
        } catch (Exception ex) {
            Log.e("Ex DB: ", ex.getMessage());
        }
        return mCursor;
    }

    public boolean checkFarmAssFormsMajor(String FORM_TYPE_ID, String collect_date) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        return true;
    }

    public void deleteSingleFarmAssFormsMajor(String farm_id, String form_type_id, String collect_date) {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("delete from " + TABLE_FARM_ASS_MAJOR + " WHERE " + KEY_FORM_TYPE_ID + " = " + form_type_id + " AND DATE(" + KEY_COLLECT_DATE + ") = DATE('" + collect_date + "') AND " + KEY_FARM_ID + " = " + farm_id);
    }
    
    

    public void addFarmAssMedium(FarmAssFormsMedium farmAssMedium) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_FARM_ID, farmAssMedium.getFarmID());
        values.put(KEY_FORM_TYPE_ID, farmAssMedium.getFormTypeID());
        values.put(KEY_ACTIVITY_METHOD, farmAssMedium.getActivityMethod());
        values.put(KEY_ACTIVITY_DATE, farmAssMedium.getActDate());
        values.put(KEY_FAMILY_HOURS, farmAssMedium.getFamilyHours());
        values.put(KEY_HIRED_HOURS, farmAssMedium.getHiredHours());
        values.put(KEY_COLLECT_DATE, farmAssMedium.getCollectDate());
        values.put(KEY_MONEY_OUT, farmAssMedium.getMoneyOut());
        values.put(KEY_REMARKS, farmAssMedium.getRemarks());
        values.put(KEY_INPUT_ID, farmAssMedium.getInputID());
        values.put(KEY_INPUT_QUANTITY, farmAssMedium.getInputQuantity());
        values.put(KEY_SPRAY_TYPE, farmAssMedium.getSprayType());
        values.put(KEY_USER_ID, farmAssMedium.getUserID());
        values.put(KEY_COMPANY_ID, farmAssMedium.getCoID());

        db.insert(TABLE_FARM_ASS_MEDIUM, null, values);
        db.close();
    }

    public int getFormAssFormsMajorCount() {
        int count = 0;
        String countQuery = "SELECT  * FROM " + TABLE_FARM_ASS_MAJOR;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        if (cursor != null && !cursor.isClosed()) {
            count = cursor.getCount();
            cursor.close();
        }
        db.close();
        return count;
    }
    
    public int getFormAssFormsMediumCount() {
        int count = 0;
        String countQuery = "SELECT  * FROM " + TABLE_FARM_ASS_MEDIUM;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        if (cursor != null && !cursor.isClosed()) {
            count = cursor.getCount();
            cursor.close();
        }
        db.close();
        return count;
    }

    public void clearFarmAssFormsMajor() {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("delete from " + TABLE_FARM_ASS_MAJOR);
    }
    
     public void clearFarmAssFormsMedium() {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("delete from " + TABLE_FARM_ASS_MEDIUM);
    }

    public void clearHerbicides() {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("delete from " + TABLE_HERBICIDES);
    }
    
     public void clearFoliarFeed() {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("delete from " + TABLE_FOLIAR_FEED);
    }

    public void clearOtherCrops() {
      SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("delete from " + TABLE_OTHER_CROPS);
    }
}
