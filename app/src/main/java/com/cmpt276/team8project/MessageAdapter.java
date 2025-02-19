package com.cmpt276.team8project;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

// MessageAdapter.java
public class MessageAdapter extends BaseAdapter {

  List<Message> messages = new ArrayList<>();
  Context context;

  // constructor
  public MessageAdapter(Context context) {
    this.context = context;
  }

  // add message to messages list
  public void add(Message message) {
    this.messages.add(message);
    notifyDataSetChanged(); // to render the list we need to notify
  }

  // getter functions
  @Override
  public int getCount() {
    return messages.size();
  }

  @Override
  public Object getItem(int i) {
    return messages.get(i);
  }

  @Override
  public long getItemId(int i) {
    return i;
  }

  // This is the backbone of the class, it handles the creation of single ListView row (chat bubble)
  @SuppressLint("InflateParams")
  @Override
  public View getView(int i, View convertView, ViewGroup viewGroup) {
    MessageViewHolder holder = new MessageViewHolder();
    LayoutInflater messageInflater = (LayoutInflater) context
        .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
    Message message = messages.get(i);

    if (message
        .isBelongsToCurrentUser()) { // this message was sent by us so let's create a basic chat bubble on the right

      convertView = messageInflater.inflate(R.layout.my_message, null);
      holder.messageBody = (TextView) convertView.findViewById(R.id.message_body);
      convertView.setTag(holder);
      holder.messageBody.setText(message.getText());
    } else { // this message was sent by someone else so let's create an advanced chat bubble on the left

      convertView = messageInflater.inflate(R.layout.their_message, null);
      holder.avatar = (View) convertView.findViewById(R.id.avatar);
      holder.name = (TextView) convertView.findViewById(R.id.name);
      holder.messageBody = (TextView) convertView.findViewById(R.id.message_body);
      convertView.setTag(holder);

      holder.name.setText(message.getMemberData().getName());
      holder.messageBody.setText(message.getText());
      GradientDrawable drawable = (GradientDrawable) holder.avatar.getBackground();
      drawable.setColor(Color.parseColor(message.getMemberData().getColor()));
    }
    return convertView;
  }
}

class MessageViewHolder {

  public View avatar;
  public TextView name;
  public TextView messageBody;
}

// Code above was modified from the following reference:
// Scaledrone, “Android Chat Tutorial: Building A Realtime Messaging App,”
//  Scaledrone Blog, 05-Feb-2019. [Online]. Available:
//  https://www.scaledrone.com/blog/android-chat-tutorial/. [Accessed: 09-Apr-2021].