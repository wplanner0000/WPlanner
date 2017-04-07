package com.example.lenovo.planner.UserHome;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.lenovo.planner.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;

import static android.R.attr.data;

public class todo extends Fragment {

    FloatingActionButton fab_plus,fab_predefined, fab_user_defined;
    Animation FabRClockwise,FabRanticlockwise;
    TextView mItemSelected;
    Button button;
    boolean isopen=false;
    String[] listItems;
    boolean[] checkedItems;
    ArrayList<Integer> mUserItems=new ArrayList<>();
    ArrayAdapter<String> adapter;
    ListView listView=null;
    TextView textView_Predefined,textView_Userdefined;
    final Context context=getActivity();
    ArrayList<String> item = new ArrayList<String>();
    List selection=new ArrayList();
    ActionMode actionMode;
    int count=0;
    ToggleButton toggleButton;
    LinkedHashSet<String> lhs = new LinkedHashSet<String>();
    public todo() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_todo, container, false);


        fab_plus=(FloatingActionButton) view.findViewById(R.id.fab_plus);
        fab_predefined=(FloatingActionButton) view.findViewById(R.id.fab_predefined);
        fab_user_defined=(FloatingActionButton) view.findViewById(R.id.fab_user_defined);
        // FabOpen= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_open);
        // FabClose= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_close);
        FabRClockwise= AnimationUtils.loadAnimation(getActivity(),R.anim.rotate_clockwise);
        FabRanticlockwise= AnimationUtils.loadAnimation(getActivity(),R.anim.rotate_anticlockwise);
        mItemSelected=(TextView) view.findViewById(R.id.tvItemSelected);
        listView=(ListView) view.findViewById(R.id.listview);
        mItemSelected=(TextView) view.findViewById(R.id.tvItemSelected);
        listItems=getResources().getStringArray((R.array.wedding_items));
        toggleButton=(ToggleButton) view.findViewById(R.id.toggle_button);
        checkedItems=new boolean[listItems.length];


        /*  CODE TO CHANGE THE BACKGROUND COLOR OF SELECTED LIST ITEMS   */


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                view.setSelected(true);
            }
        });



        /*  CODE FOR USER DEFINED TASK  */




        fab_user_defined.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (actionMode!=null) {
                    actionMode.finish();
                }
                final EditText taskEditText=new EditText(getActivity());
                AlertDialog dialog=new AlertDialog.Builder(getActivity())
                        .setTitle("Add new Task ")
                        .setMessage("What do you want to do next ?")
                        .setView(taskEditText)
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                //DATABASE

                                String task = String.valueOf(taskEditText.getText());
                                if (task.isEmpty()){
                                    taskEditText.setError("enter task");
                                    return;
                                }
                                else {

                                    item.add(task);
                                }

                                lhs.addAll(item);
                                item.clear();
                                item.addAll(lhs);

                                adapter = new ArrayAdapter(getActivity(), R.layout.row_layout_task, R.id.tvItemSelected, item);
                                listView.setAdapter(adapter);
                            }

                        })
                        .setNegativeButton("cancel",null)
                        .create();
                dialog.show();

            }
        });


        /*CODE TO DELETE ITEMS FROM LISTVIEW USING CONTEXTUAL MENU */


        listView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE_MODAL);
        listView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
                if (checked){
                    selection.add(item.get(position));


                }
                else {
                    selection.remove(item.get(position));

                }
                mode.setTitle(selection.size()+" item selected");



            }

            /*@Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                MenuInflater menuInflater = getMenuInflater();
                menuInflater.inflate(R.menu.my_menu, menu);
                actionMode=mode;

                return true;
            }
*/
            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                return true;

            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem mitem) {

                if (mitem.getItemId()==R.id.id_delete)
                {
                    for (Object Item : selection){
                        String ITEM=Item.toString();
                        item.remove(ITEM);


                    }

                    adapter.notifyDataSetChanged();
                    mode.finish();
                }



                return false;

            }


            @Override
            public void onDestroyActionMode(ActionMode mode){
                count=0;
                selection.clear();



            }
        });



        /*  CODE FOR PREDEFINED TASK ALERTDIALOG  */



        fab_predefined.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                final AlertDialog.Builder mBuilder=new AlertDialog.Builder(getActivity());
                mBuilder.setTitle("Items for wedding");
                mBuilder.setMultiChoiceItems(listItems,checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position, boolean isChecked)
                    {
                        if (isChecked) {
                            if (! mUserItems.contains(position)) {
                                mUserItems.add(position);
                            }
                        }
                        else{
                            int i=mUserItems.indexOf(position);
                            mUserItems.remove(i);
                        }

                    }

                });

                mBuilder.setCancelable(true);
                mBuilder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {

                        for (int i=0;i<mUserItems.size();i++)
                        {

                            item.add(listItems[mUserItems.get(i)]);
                            lhs.addAll(item);

                            item.clear();


                            item.addAll(lhs);


                        }

                        adapter=new ArrayAdapter(getActivity(),R.layout.row_layout_task,R.id.tvItemSelected,item);
                        listView.setAdapter(adapter);
                        // listView.setBackgroundColor(Color.GRAY);

                    }
                });
                mBuilder.setNegativeButton("dismiss", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                    }
                });
                mBuilder.setNeutralButton("clear all", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        for (int i=0;i<checkedItems.length;i++)
                        {
                            checkedItems[i]=false;
                            mUserItems.clear();
                            //mItemSelected.setText("");
                        }

                    }
                });
                AlertDialog mDialog=mBuilder.create();
                mDialog.show();




            }
        });



        /*  CODE FOR FLOATING ACTION BUTTON  */




        fab_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (isopen)
                {
                    // fab_predefined.startAnimation(FabClose);
                    // fab_user_defined.startAnimation(FabClose);
                    fab_plus.startAnimation(FabRanticlockwise);
                    //fab_predefined.setClickable(false);
                    //fab_user_defined.setClickable(false);
                    fab_predefined.setVisibility(View.INVISIBLE);
                    fab_user_defined.setVisibility(View.INVISIBLE);
                    isopen=!isopen;
                }
                else
                {
                    //  fab_user_defined.startAnimation(FabOpen);
                    //  fab_predefined.startAnimation(FabOpen);
                    fab_plus.startAnimation(FabRClockwise);
                    fab_user_defined.setClickable(true);
                    fab_predefined.setClickable(true);
                    fab_predefined.setVisibility(View.VISIBLE);
                    fab_user_defined.setVisibility(View.VISIBLE);
                    isopen=!isopen;
                }

            }

        });



        return view;
    }

}
