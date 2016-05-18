package app.season.sample;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import app.season.backgroundview.BackgroundView;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private BackgroundView mBackgroundView;
    private FloatingActionButton mFloatingActionButton;

    private Adapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler);
        mBackgroundView = (BackgroundView) findViewById(R.id.background);
        mFloatingActionButton = (FloatingActionButton) findViewById(R.id.fab);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new Adapter();
        mRecyclerView.setAdapter(mAdapter);


        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String string = "New Mission";
                mAdapter.addData(string);
                mBackgroundView.setVisibility(View.GONE);
                mRecyclerView.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBackgroundView.stopAnimation();
    }

    private class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

        private List<String> mData;

        public Adapter() {
            super();
            mData = new ArrayList<>();
        }

        public void setData(List<String> data) {
            mData.clear();
            mData.addAll(data);
            notifyDataSetChanged();
        }

        public void addData(String string) {
            mData.add(string);
            notifyDataSetChanged();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            ViewHolder viewHolder = (ViewHolder) holder;
            viewHolder.mButton.setText(mData.get(position));

            viewHolder.mButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mData.remove(holder.getAdapterPosition());
                    notifyDataSetChanged();

                    if (mData.size() == 0) {
                        mBackgroundView.setVisibility(View.VISIBLE);
                        mRecyclerView.setVisibility(View.GONE);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            private Button mButton;

            public ViewHolder(View itemView) {
                super(itemView);
                mButton = (Button) itemView.findViewById(R.id.item_name);
            }
        }
    }
}
