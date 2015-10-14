package com.example.butterknife;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import butterknife.OnLongClick;
import butterknife.Unbinder;

public class SimpleActivity extends Activity {
  private static final ButterKnife.Action<View> ALPHA_FADE = new ButterKnife.Action<View>() {
    @Override public void apply(@NonNull View view, int index) {
      AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
      alphaAnimation.setFillBefore(true);
      alphaAnimation.setDuration(500);
      alphaAnimation.setStartOffset(index * 100);
      view.startAnimation(alphaAnimation);
    }
  };

  @Bind(resName="title") TextView title;
  @Bind(R.id.subtitle) TextView subtitle;
  @Bind(R.id.hello)
  Button hello;
  @Bind(R.id.list_of_things)
  ListView listOfThings;
  @Bind(R.id.footer) TextView footer;
  @Unbinder
  ButterKnife.Unbinder unbinder;

  @Bind(resName = { "title", "subtitle", "hello" })
  List<View> headerViews;

  private SimpleAdapter adapter;


  @OnClick(resName = "hello") void sayHello() {
    Toast.makeText(this, "Hello, views!", Toast.LENGTH_SHORT).show();
    ButterKnife.apply(headerViews, ALPHA_FADE);
  }

  @OnLongClick(resName = {"hello", "title"}) boolean sayGetOffMe() {
    Toast.makeText(this, "Let go of me!", Toast.LENGTH_SHORT).show();
    return true;
  }

  @OnItemClick(resName = "list_of_things") void onItemClick(int position) {
    Toast.makeText(this, "You clicked: " + adapter.getItem(position), Toast.LENGTH_SHORT).show();
  }
  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.simple_activity);
    ButterKnife.bind(this);

    // Contrived code to use the bound fields.
    title.setText("Butter Knife");
    ((TextView)headerViews.get(1)).setText("Butter Knife2");
    subtitle.setText("Field and method binding for Android views.");
    footer.setText("by Jake Wharton");
    hello.setText("Say Hello");

    adapter = new SimpleAdapter(this);
    listOfThings.setAdapter(adapter);
  }
}
