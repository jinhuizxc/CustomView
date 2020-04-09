package com.zx.customview.step1.layoutgridlayoutanimation.listviewitem;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.zx.customview.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qijian on 16/3/12.
 *
 * 三、优化
 * 上面虽然解决了进入时添加动画的问题，但仔细的同学可以看出，在这个效果图中还存在几个问题，可能上面的效果图还看不清楚具体存在的问题
 1、如果上拉的时候，一下上拉了几个item，那些要显示的item会一起从底部出现
 2、在下拉的时候，上部出现的item也会应用上动画
 首先，解决第二个问题比较简单，只需要判断当前手指是上滑还上下滑就可以了，只有当手指向上滑的时候，才对底部出现的item添加上入场动画，其它时间不添加动画即可
 第一个问题其实也比较容易解决，我们可以通过listview.getChildCount()得到当前ListView中有多少个item。我们只给最后一个添加动画即可。其它的就直接显示就好了。
 1、上下滑动问题
 首先，我们首先解决上下滑动问题，这个问题其实比较好解决，只需要监听listview的OnScrollListener，根据它判断出当前ListView是上滑还是下滑就可以了。
 先看我们在onScrollListner中都有哪些参数：
 */
public class ListAdapter extends BaseAdapter {

    private List<Drawable> mDrawableList = new ArrayList<>();
    private int mLength = 0;
    private LayoutInflater mInflater;
    private Context mContext;
    private ListView mListView;
    private Animation animation;
    private int mFirstTop, mFirstPosition;
    private boolean isScrollDown;

    /**
     * 首先是构造函数：
     * 首先是传进来的几个参数，
     * List<Drawable> drawables是listview要循环显示的图片的Drawable对象列表，
     * length表示当前listview要显示多少行。
     * 可能有些同学会注意到，我们还把ListView对象给传进来了，
     * 然后在上面的代码中并没有用到，其中把listview封装进Adapter是一个好习惯，
     * 因为我们可能会在Adapter中监听listview的状态从而改变item的显示情况。
     * 我们这里目前还没有用到Listview然后是getItem函数：
     *
     * @param context
     * @param listView
     * @param drawables
     * @param length
     */
    public ListAdapter(Context context, ListView listView, List<Drawable> drawables, int length) {
        mDrawableList.addAll(drawables);
        mLength = length;
        mInflater = LayoutInflater.from(context);
        mContext = context;
        mListView = listView;
        animation = AnimationUtils.loadAnimation(mContext, R.anim.bottom_in_anim);
        // 设置setOnScrollListener
        mListView.setOnScrollListener(mOnScrollListener);
    }

    AbsListView.OnScrollListener mOnScrollListener = new AbsListView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {

        }

        /**
         * 有四个参数：

         第一个参数AbsListView view：是当前listview的对象
         第二个参数int firstVisibleItem：表示当前第一个可见的item在listview所有item中的索引，这里需要非常注意，firstVisibleItem与getChildAt(int position)中的参数position的意义不同，firstVisibleItem是指在整个ListView中的位置。而getChildAt(int position)中参数position传的是当前屏幕显示区域中item的索引，屏幕中第一个item的view可以通过getChildAt(0)得到。
         第三个参数int visibleItemCount：表示当前屏幕中可见的有几条item
         第四个参数int totalItemCount：表示当前listview总共有多少条item，得到的值与adapter.getCount()的值相同。

         在理解了上面四个参数以后，我们再来看看下移的情况；
         向下移动包括两种情况：
         第一：屏幕中第一个item或前几个item一起移出屏幕，在这种情况下，我们只需要判断firstVisibleItem是否比上次的值大即可。即第一个显示的item是不是已经向下移了
         第二：可能用户并没有一次性移一整条item，而是仅让当前item向上移了一点点。这里，由于当前可见的第一个item的位置仍然是firstVisibleItem，只是它的top值变了。



         * @param view
         * @param firstVisibleItem
         * @param visibleItemCount
         * @param totalItemCount
         */
        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            View firstChild = view.getChildAt(0);
            if (firstChild == null) return;
            int top = firstChild.getTop();
            /**
             * firstVisibleItem > mFirstPosition表示向下滑动一整个Item
             * mFirstTop > top表示在当前这个item中滑动
             */
            isScrollDown = firstVisibleItem > mFirstPosition || mFirstTop > top;
            mFirstTop = top;
            mFirstPosition = firstVisibleItem;
        }
    };

    @Override
    public int getCount() {
        return mLength;
    }

    /**
     * 大家可能会疑问：为什么要用position % mDrawableList.size()来得到当前图片在图片列表中的索引？
     * 因为我们是循环显示的图片的，比如我们总共有九张图片，
     * 那当前是第12个item时,显示的应当是第3张图了，position的值为12(因为Adapter的position是从0开始的)，所以12%9 = 3；这就对上了。
     * 理解不了的同学，多拿几个数来算算，比如当显示到第15张时，position是多少，对应的图片应该是哪一张呢？
     *
     * @param position
     * @return
     */
    @Override
    public Object getItem(int position) {
        return mDrawableList.get(position % mDrawableList.size());
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {

            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_layout_test, null);
            holder.mImageView = (ImageView) convertView.findViewById(R.id.img);
            holder.mTextView = (TextView) convertView.findViewById(R.id.text);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        //清除当前显示区域中所有item的动画
        for (int i = 0; i < mListView.getChildCount(); i++) {
            View view = mListView.getChildAt(i);
            view.clearAnimation();
        }
        //然后给当前item添加上动画
        if (isScrollDown) {
            convertView.startAnimation(animation);
        }
        convertView.setTag(holder);

        holder.mImageView.setImageDrawable(mDrawableList.get(position % mDrawableList.size()));
        holder.mTextView.setText(position + "");

        return convertView;
    }

    public class ViewHolder {
        public ImageView mImageView;
        public TextView mTextView;
    }
}
