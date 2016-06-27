package com.linheimx.app.linlock.app;

import android.app.Application;
import android.content.Context;
import android.content.ContextWrapper;

import com.linheimx.app.linlock.R;
import com.linheimx.app.linlock.model.Joke;
import com.linheimx.app.linlock.model.Joke_Table;
import com.linheimx.app.linlock.model.LinLockDB;
import com.linheimx.app.linlock.util.Prefs;
import com.linheimx.app.linlock.util.ToastUtils;
import com.raizlabs.android.dbflow.config.DatabaseDefinition;
import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;
import com.raizlabs.android.dbflow.structure.database.transaction.ITransaction;
import com.raizlabs.android.dbflow.structure.database.transaction.ProcessModelTransaction;
import com.raizlabs.android.dbflow.structure.database.transaction.Transaction;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by LJIAN on 2016/5/15.
 */
public class App extends Application {

    public static Context _context;

    @Override
    public void onCreate() {
        super.onCreate();

        _context = this;

        /**
         *  db
         */
        FlowConfig flowConfig = new FlowConfig.Builder(this)
                .openDatabasesOnInit(false)
                .build();
        FlowManager.init(flowConfig);

        /**
         *  toast
         */
        ToastUtils.register(this);

        /**
         *  sp
         */
        // Initialize the Prefs class
        new Prefs.Builder()
                .setContext(this)
                .setMode(ContextWrapper.MODE_PRIVATE)
                .setPrefsName(getPackageName())
                .setUseDefaultSharedPreference(true)
                .build();


        // 数据库中插入一些笑话
        long count = SQLite.selectCountOf(Joke_Table.id).from(Joke.class).count();
        if (count <= 0) {

            List<Joke> jokeList = new ArrayList<>();

            Joke joke = new Joke();
            joke.title = "高中语文考试";
            joke.content = "高中语文考试，有道填空题：山对海说：“你是如此的宽广、如此的澎湃、如此的博大……”然后下面的填空是海对山说：（ ）。大家都极尽所能发挥想象，结果，卷子发下来时，有个同学在空格里填了：“谢谢。”";
            jokeList.add(joke);

            Joke joke2 = new Joke();
            joke2.title = "锦囊";
            joke2.content = "临别之际师父送我一枚锦囊，并嘱咐我紧要关头再打开。隔年，大军压境，城内人心涣散。麻爪的我突然想起师傅曾赐我锦囊。将其打开只见上书八个大字：“你现在一定很着急”。我惊声叹道：“准！”";
            jokeList.add(joke2);

            Joke joke3 = new Joke();
            joke3.title = "乌鸦嘴";
            joke3.content = "乌鸦妈妈生病了，小乌鸦看着病床上妈妈憔悴的模样，心疼得大哭：“妈妈，你快点好起来，千万不要离开我呀！”妈妈感动地说：“乖孩子，你如果不是乌鸦嘴该多好啊…”乌鸦妈妈，卒。";
            jokeList.add(joke3);

            Joke joke4 = new Joke();
            joke4.title = "能给点儿蛋糕吗？";
            joke4.content = "乞丐：“大姐，我两天没吃饭了，能给点儿蛋糕吗？” 大姐：“蛋糕？我这儿只有米饭！！” 乞丐：“要是平常也就算了，可今天是我的生日！”";
            jokeList.add(joke4);

            Joke joke5 = new Joke();
            joke5.title = "调戏算命的";
            joke5.content = "一男子在街上走着，突然看到街边上有一算命摊，于是决定去调戏算命的。该男子一走过去，算命的就说：“你有两个儿子。”男子说：“哈哈，你怎么知道。我其实有三个儿子。”算命的笑道：“哈哈，你又怎么知道三个都是你的呢。”";
            jokeList.add(joke5);

            ProcessModelTransaction<Joke> processModelTransaction =
                    new ProcessModelTransaction.Builder<>(new ProcessModelTransaction.ProcessModel<Joke>() {
                        @Override
                        public void processModel(Joke model) {
                            model.save();
                        }
                    }).addAll(jokeList).build();
            DatabaseDefinition db = FlowManager.getDatabase(LinLockDB.class);
            Transaction transaction = db.beginTransactionAsync(processModelTransaction).build();
            transaction.execute();
        }
    }


    public static Context get(){
        return _context;
    }
}
