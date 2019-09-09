# Room_demo
room的多表使用    
数据库的选型：http://andyken.github.io/2017/10/26/Android%E6%B5%81%E8%A1%8CORM%E6%A1%86%E6%9E%B6%E6%80%A7%E8%83%BD%E5%AF%B9%E6%AF%94%E5%8F%8ARoom%E8%B8%A9%E5%9D%91%E6%80%BB%E7%BB%93/              

具体使用：
1.引入
首先在project的gradle文件中添加 Google Maven 仓库    
allprojects {    
    repositories {    
        jcenter()    
        google()    
    }    
}    

然后在APP的gradle文件中添加依赖    
    // Room (use 1.1.0-beta2 for latest beta)    
    implementation "android.arch.persistence.room:runtime:1.0.0"    
    annotationProcessor "android.arch.persistence.room:compiler:1.0.0"    
2.三大组成部分    
Room由三大部分组成

Entity：数据库中表对应的Java实体
DAO：操作数据库的方法
Database：创建数据库

2.1Entity
Entity就是一般的Java实体类
这里只涉及到@Entity、 @PrimaryKey，2个注解。其实有其他好多注解，比如 @ColumnInfo（列名）、 @ForeignKey（外键）、@Index（索引）等等，具体用法还是看官方文档好点
@Entity
public class UserEntity {    
    @PrimaryKey(autoGenerate = true)    
    private int uid;    
    private String name;    
    private String address;    

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "uid=" + uid +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}

2.2 DAO
DAO的话就是创建一些访问数据库的方法
通过注解的方式实现增（@Insert）删（@Delete）改（@Update）查（@Query）
可以通过“:xxx”的方式引入参数
    
@Dao    
public interface UserDao {    
    @Insert
    void insert(UserEntity userEntity);    

    @Insert
    void insertAll(List<UserEntity> userEntities);

    @Delete
    void delete(UserEntity userEntity);

    @Delete
    void deleteAll(List<UserEntity> userEntities);

    @Update
    void update(UserEntity userEntity);

    @Query("SELECT * FROM UserEntity")
    List<UserEntity> getAll();

    @Query("SELECT * FROM UserEntity WHERE uid  = :uid")
    UserEntity getByUid(int uid);
}

2.3 Database
上面两个文件建好后，就可以创建数据库了    
要在里面声明你创建的DAO：public abstract UserDao userDao();    
多表创建的数据库样式，相应只需多创建一个BookDao及BooKEntity，然后具体数据库的创建如下：    
@Database(entities = {UserEntity.class, BookEntity.class}, version = 2, exportSchema = false)        
public abstract class AppDataBase extends RoomDatabase {

    private static final String DB_NAME = "data_base.db";
    private static volatile AppDataBase instance;

    public static AppDataBase getInstance(Context context) {
        if (instance == null) {
            synchronized (AppDataBase.class) {
                if (instance == null) {
                    instance = create(context);
                }
            }

        }
        return instance;
    }

    private static AppDataBase create(final Context context) {
        return Room.databaseBuilder(
                context,
                AppDataBase.class,
                DB_NAME).
                addMigrations(MIGRATION_1_2).allowMainThreadQueries().build();
    }

    public abstract UserDao getUserDao();

    public abstract BookDao getBookDao();


    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {

            database.execSQL("CREATE TABLE 'book_table' (`bookId` TEXT, "
                    + "`name` TEXT,'publish' TEXT, PRIMARY KEY(`bookId`))");
        }
    };
3数据库升级
数据库升级要创建Migration类，在migrate方法中添加改变的SQL语句，然后version加1    
static final Migration MIGRATION_1_2 = new Migration(1, 2) {    
        @Override    
        public void migrate(SupportSQLiteDatabase database) {    
            database.execSQL("CREATE TABLE 'book_table' (`bookId` TEXT, "    
                    + "`name` TEXT,'publish' TEXT, PRIMARY KEY(`bookId`))");    
        }    
    };    

在build()之前添加addMigrations
return Room.databaseBuilder(appContext, AppDatabase.class, DATABASE_NAME)    
                .addMigrations(MIGRATION_1_2)    
                .build();    
    
@Database(entities = {UserEntity.class},version = 2)    

4访问数据库
三大组件创建好，你就可以使用数据库啦    
AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                AppDataBase.getInstance(getApplicationContext()).getBookDao().insertList(DataGenertor.generateBooks());
                final List<BookEntity> entityList = AppDataBase.getInstance(getApplicationContext()).getBookDao().queryAll();
                AppExecutors.getInstance().mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        newtableContent.setText(entityList.toString());
                    }
                });
            }
        });
