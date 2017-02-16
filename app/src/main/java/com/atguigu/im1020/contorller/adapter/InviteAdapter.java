package com.atguigu.im1020.contorller.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.atguigu.im1020.R;
import com.atguigu.im1020.modle.bean.InvitationInfo;
import com.atguigu.im1020.modle.bean.UserInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by 张永卫on 2017/2/16.
 */

public class InviteAdapter extends BaseAdapter {

    private Context context;
    private List<InvitationInfo> invitationInfos;

    public InviteAdapter(Context context,OnInviteItemClickListener onInviteItemClickListener) {
        this.context = context;
        invitationInfos = new ArrayList<>();
        this.onInviteItemClickListener = onInviteItemClickListener;
    }

    public void refresh(List<InvitationInfo> invitationInfos) {

        //校验 注意size的数量
        if (invitationInfos != null && invitationInfos.size() >= 0) {
            this.invitationInfos.clear();
            this.invitationInfos.addAll(invitationInfos);

            //刷新数据
            notifyDataSetChanged();
        }

    }

    @Override
    public int getCount() {
        return invitationInfos != null ? invitationInfos.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return invitationInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        InviteViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.adapter_invite_item, null);
            viewHolder = new InviteViewHolder(convertView);
            convertView.setTag(viewHolder);

        }else{
            viewHolder = (InviteViewHolder) convertView.getTag();
        }

        final InvitationInfo invitationInfo = invitationInfos.get(position);
        
        if(invitationInfo.getUserInfo()==null) {

            //群邀请

        }else{
            //联系人邀请
            UserInfo userInfo = invitationInfo.getUserInfo();
            viewHolder.tvInviteName.setText(userInfo.getHxid());
            viewHolder.btInviteAccept.setVisibility(View.GONE);
            viewHolder.btInviteReject.setVisibility(View.GONE);
            
            if(invitationInfo.getStatus()==InvitationInfo.InvitationStatus.NEW_INVITE) {

                //新邀请
                viewHolder.tvInviteReason.setText("申请加好友");
                viewHolder.btInviteAccept.setVisibility(View.VISIBLE);
                viewHolder.btInviteReject.setVisibility(View.VISIBLE);
                //设置监听
                viewHolder.btInviteAccept.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(onInviteItemClickListener!=null) {
                            onInviteItemClickListener.accpt(invitationInfo);
                        }
                    }
                });
                viewHolder.btInviteReject.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(onInviteItemClickListener!=null) {
                            onInviteItemClickListener.reject(invitationInfo);
                        }
                    }
                });

            }else if(invitationInfo.getStatus()==InvitationInfo.InvitationStatus.INVITE_ACCEPT_BY_PEER) {
                //邀请被接收
                viewHolder.tvInviteReason.setText("邀请被接收");

            }else if(invitationInfo.getStatus()== InvitationInfo.InvitationStatus.INVITE_ACCEPT) {

                //接收邀请
                viewHolder.tvInviteReason.setText("接收邀请");
            }

        }
        return convertView;
    }

     class InviteViewHolder {
        @InjectView(R.id.tv_invite_name)
        TextView tvInviteName;
        @InjectView(R.id.tv_invite_reason)
        TextView tvInviteReason;
        @InjectView(R.id.bt_invite_accept)
        Button btInviteAccept;
        @InjectView(R.id.bt_invite_reject)
        Button btInviteReject;

        InviteViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
    private OnInviteItemClickListener onInviteItemClickListener;
    public void  setOnInviteItemClickListener(OnInviteItemClickListener onInviteItemClickListener) {
        this.onInviteItemClickListener = onInviteItemClickListener;
    }

    public interface OnInviteItemClickListener{

        void accpt(InvitationInfo invitationInfo);
        void reject(InvitationInfo invitationInfo);
    }
}
