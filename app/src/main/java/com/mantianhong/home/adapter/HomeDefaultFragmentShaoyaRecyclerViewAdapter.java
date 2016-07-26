package com.mantianhong.home.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mantianhong.R;
import com.mantianhong.bean.Article;
import com.mantianhong.common.SingletonImageCollection;
import com.mantianhong.home.activity.HomeArticleDetailActivity;

import java.util.List;

/**
 * Created by new pc on 2016/7/6.
 */
public class HomeDefaultFragmentShaoyaRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private LayoutInflater mInflater;
    private Context mContext;
    private List<Article>  mListArticle;
    private int mType;

    private static final  int TOP=0;
    private static final  int THREE_IMAGE=3;
    private static final  int TWO_IMAGE=2;
    private static final  int ONE_IMAGE=1;
    private static final  int FOOTER=4;

    private SingletonImageCollection picCollection;

    /*
    构造函数，可以通过构造函数传递外面的数据进来
    Context 传递环境的目的是方便实例化 private LayoutInflater mInflater;
     */
    public HomeDefaultFragmentShaoyaRecyclerViewAdapter(List<Article>  mListArticle, Context context, int type){
        this.mListArticle = mListArticle;
        this.mContext = context;
        this.mType = type;
        this.mInflater = LayoutInflater.from(mContext);

        picCollection = SingletonImageCollection.getInstance();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view=null;
        if(view==null) {
            switch (viewType) {
                case TOP:
                    view = mInflater.inflate(R.layout.home_fragment_common_top, parent,false);
                    return new ArticleNoImageViewHolder(view);

                case THREE_IMAGE:
                    view = mInflater.inflate(R.layout.home_fragment_common_threeimage, parent,false);
                    return new ArticleThreeImageViewHolder(view);

                case TWO_IMAGE:
                    view = mInflater.inflate(R.layout.home_fragment_common_twoimage, parent,false);
                    return new ArticleTwoImageViewHolder(view);

                case ONE_IMAGE:
                    view = mInflater.inflate(R.layout.home_fragment_common_oneimage, parent,false);
                    return new ArticleOneImageViewHolder(view);

                case FOOTER:
                    view = mInflater.inflate(R.layout.home_fragment_common_footer, parent,false);
                    return new ArticleFooterImageViewHolder(view);

                default:
                    break;
            }
       }

        return null;
    }

    /*
    根部不同的mType实现不同的数据，有了position，就可以从数据中拿到具体的数据，然后给view中的具体控件赋值
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        final Article artitle = mListArticle.get(position);
        String strImage = artitle.getMImageList();
        String[] images = strImage.split("\\|");

        if(holder instanceof ArticleThreeImageViewHolder){
            ((ArticleThreeImageViewHolder) holder).mTitle.setText(artitle.getMTitle());
            ((ArticleThreeImageViewHolder) holder).mViews.setText("浏览:"+artitle.getMViews());
            ((ArticleThreeImageViewHolder) holder).mReplies.setText("回复:"+artitle.getMReplies());
            ((ArticleThreeImageViewHolder) holder).mPostDatetime.setText("日期:"+artitle.getMPostDatetime());
            //Picasso.with(mContext).load("http://www.1316818.com/upload/" + images[0]).into(((ArticleThreeImageViewHolder) holder).mImageList01);
            //Picasso.with(mContext).load("http://www.1316818.com/upload/" + images[a1]).into(((ArticleThreeImageViewHolder) holder).mImageList02);
            //Picasso.with(mContext).load("http://www.1316818.com/upload/" + images[2]).into(((ArticleThreeImageViewHolder) holder).mImageList03);
            SingletonImageCollection.loadImage(mContext,"http://www.1316818.com/upload/" + images[0],((ArticleThreeImageViewHolder) holder).mImageList01);
            SingletonImageCollection.loadImage(mContext,"http://www.1316818.com/upload/" + images[1],((ArticleThreeImageViewHolder) holder).mImageList02);
            SingletonImageCollection.loadImage(mContext,"http://www.1316818.com/upload/" + images[2],((ArticleThreeImageViewHolder) holder).mImageList03);

            //消除多余的图片缓存
            picCollection.recycleCollection(mContext);
            picCollection.addImage("http://www.1316818.com/upload/" + images[0]);
            picCollection.addImage("http://www.1316818.com/upload/" + images[1]);
            picCollection.addImage("http://www.1316818.com/upload/" + images[2]);

            //这里硬代码
            ((ArticleThreeImageViewHolder) holder).mCategoryImgage.setImageResource(R.drawable.xh_05);
        }

        if(holder instanceof ArticleTwoImageViewHolder){
            ((ArticleTwoImageViewHolder) holder).mTitle.setText(artitle.getMTitle());
            ((ArticleTwoImageViewHolder) holder).mViews.setText("浏览:"+artitle.getMViews());
            ((ArticleTwoImageViewHolder) holder).mReplies.setText("回复:"+artitle.getMReplies());
            ((ArticleTwoImageViewHolder) holder).mPostDatetime.setText("日期:"+artitle.getMPostDatetime());
            //Picasso.with(mContext).load("http://www.1316818.com/upload/" + images[0]).into(((ArticleTwoImageViewHolder) holder).mImageList01);
            SingletonImageCollection.loadImage(mContext,"http://www.1316818.com/upload/" + images[0],((ArticleTwoImageViewHolder) holder).mImageList01);

            //消除多余的图片缓存
            picCollection.recycleCollection(mContext);
            picCollection.addImage("http://www.1316818.com/upload/" + images[0]);

            //这里硬代码
            ((ArticleTwoImageViewHolder) holder).mCategoryImgage.setImageResource(R.drawable.xh_05);
        }

        if(holder instanceof ArticleOneImageViewHolder){
            ((ArticleOneImageViewHolder) holder).mTitle.setText(artitle.getMTitle());
            ((ArticleOneImageViewHolder) holder).mViews.setText("浏览:"+artitle.getMViews());
            ((ArticleOneImageViewHolder) holder).mReplies.setText("回复:"+artitle.getMReplies());
            //Picasso.with(mContext).load("http://www.1316818.com/upload/" + images[0]).into(((ArticleOneImageViewHolder) holder).mImageList01);
            SingletonImageCollection.loadImage(mContext,"http://www.1316818.com/upload/" + images[0],((ArticleOneImageViewHolder) holder).mImageList01);

            //消除多余的图片缓存
            picCollection.recycleCollection(mContext);
            picCollection.addImage("http://www.1316818.com/upload/" + images[0]);

            //这里硬代码
            ((ArticleOneImageViewHolder) holder).mCategoryImgage.setImageResource(R.drawable.xh_05);
        }

        if(holder instanceof ArticleNoImageViewHolder){
            ((ArticleNoImageViewHolder) holder).mTitle.setText(artitle.getMTitle());
            ((ArticleNoImageViewHolder) holder).mViews.setText("浏览:"+artitle.getMViews());
            ((ArticleNoImageViewHolder) holder).mReplies.setText("回复:"+artitle.getMReplies());
            ((ArticleNoImageViewHolder) holder).mPostDatetime.setText(artitle.getMPostDatetime());

            //这里硬代码
            ((ArticleNoImageViewHolder) holder).mCategoryImgage.setImageResource(R.drawable.xh_05);
        }

        if(holder instanceof ArticleFooterImageViewHolder){}

        //添加点击事件
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Toast.makeText(mContext,artitle.getMTid(),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mContext,HomeArticleDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("tid",artitle.getMTid().toString());
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mListArticle.size();
    }

    /*
    这个方法根据不同的位置来决定加载不同的布局，可以实现同一个页面不通的布局
     */
    @Override
    public int getItemViewType(int position) {

        //表示头两条新闻部显示图片
        if(position<=1) return  TOP;

        //根据返回的图片数量决定调用哪个模板
        Article article = mListArticle.get(position); //temp/nopic.gif
        int intImageCount = article.getMImageList().length()-article.getMImageList().replace("|","").length()+1;

        if(intImageCount>3){
            intImageCount=1;
        }

        //if (position + a1 == getItemCount()){
        //    return FOOTER;
        //}else {
                switch (intImageCount){
                    case ONE_IMAGE:
                        if(article.getMImageList().contains("nopic.gif")){
                            return TOP;
                        }
                        return ONE_IMAGE;
                    case TWO_IMAGE:
                        return TWO_IMAGE;
                    case THREE_IMAGE:
                        return THREE_IMAGE;
                    default:
                        return TOP;
                }
            }
        //}

    /*
    定义不同的ItemView布局:三个图片
     */
    class ArticleThreeImageViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImageList01,mImageList02,mImageList03,mCategoryImgage;
        private TextView mTitle,mViews,mReplies,mPostDatetime;
        public ArticleThreeImageViewHolder(View itemView) {
            super(itemView);

            mImageList01 = (ImageView) itemView.findViewById(R.id.id_home_fragment_shaoya_mImageList01);
            mImageList02 = (ImageView) itemView.findViewById(R.id.id_home_fragment_shaoya_mImageList02);
            mImageList03 = (ImageView) itemView.findViewById(R.id.id_home_fragment_shaoya_mImageList03);
            mCategoryImgage = (ImageView) itemView.findViewById(R.id.id_home_fragment_shaoya_mCategoryImgage);

            mTitle = (TextView) itemView.findViewById(R.id.id_home_fragment_shaoya_mTitle);
            mViews = (TextView) itemView.findViewById(R.id.id_home_fragment_shaoya_mViews);
            mReplies = (TextView) itemView.findViewById(R.id.id_home_fragment_shaoya_mReplies);
            mPostDatetime = (TextView) itemView.findViewById(R.id.id_home_fragment_shaoya_mPostDatetime);
        }
    }

    /*
    定义不同的ItemView布局:二个图片
    */
    class ArticleTwoImageViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImageList01,mCategoryImgage;
        private TextView mTitle,mViews,mReplies,mPostDatetime;
        public ArticleTwoImageViewHolder(View itemView) {
            super(itemView);

            mImageList01 = (ImageView) itemView.findViewById(R.id.id_home_fragment_shaoya_mImageList01);
            mCategoryImgage = (ImageView) itemView.findViewById(R.id.id_home_fragment_shaoya_mCategoryImgage);

            mTitle = (TextView) itemView.findViewById(R.id.id_home_fragment_shaoya_mTitle);
            mViews = (TextView) itemView.findViewById(R.id.id_home_fragment_shaoya_mViews);
            mReplies = (TextView) itemView.findViewById(R.id.id_home_fragment_shaoya_mReplies);
            mPostDatetime = (TextView) itemView.findViewById(R.id.id_home_fragment_shaoya_mPostDatetime);
        }
    }

    /*
    定义不同的ItemView布局:一个图片
    */
    class ArticleOneImageViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImageList01,mCategoryImgage;
        private TextView mTitle,mViews,mReplies;
        public ArticleOneImageViewHolder(View itemView) {
            super(itemView);

            mImageList01 = (ImageView) itemView.findViewById(R.id.id_home_fragment_shaoya_mImageList01);
            mCategoryImgage = (ImageView) itemView.findViewById(R.id.id_home_fragment_shaoya_mCategoryImgage);

            mTitle = (TextView) itemView.findViewById(R.id.id_home_fragment_shaoya_mTitle);
            mViews = (TextView) itemView.findViewById(R.id.id_home_fragment_shaoya_mViews);
            mReplies = (TextView) itemView.findViewById(R.id.id_home_fragment_shaoya_mReplies);
        }
    }

    /*
    定义不同的ItemView布局:没有图片
    */
    class ArticleNoImageViewHolder extends RecyclerView.ViewHolder {
        private ImageView mCategoryImgage;
        private TextView mTitle,mViews,mReplies,mPostDatetime;
        public ArticleNoImageViewHolder(View itemView) {
            super(itemView);

            mCategoryImgage = (ImageView) itemView.findViewById(R.id.id_home_fragment_shaoya_mCategoryImgage);

            mTitle = (TextView) itemView.findViewById(R.id.id_home_fragment_shaoya_mTitle);
            mViews = (TextView) itemView.findViewById(R.id.id_home_fragment_shaoya_mViews);
            mReplies = (TextView) itemView.findViewById(R.id.id_home_fragment_shaoya_mReplies);
            mPostDatetime = (TextView) itemView.findViewById(R.id.id_home_fragment_shaoya_mPostDatetime);
        }
    }

    /*
    定义不同的ItemView布局:Footer
    */
    class ArticleFooterImageViewHolder extends RecyclerView.ViewHolder{
        public ArticleFooterImageViewHolder(View itemView) {
            super(itemView);
        }
    }

}
