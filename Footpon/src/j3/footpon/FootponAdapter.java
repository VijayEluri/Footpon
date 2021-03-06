package j3.footpon;

import j3.footpon.model.Footpon;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

public class FootponAdapter extends ArrayAdapter<Footpon> implements Filterable {
	
	private ArrayList<Footpon> footpons;
	private ArrayList<Footpon> orig_footpons;
	private ListFilter mFilter;
	
	public FootponAdapter(Context context,int textViewResourceId,
			ArrayList<Footpon> footpons){
		super(context,textViewResourceId,footpons);
		this.footpons = footpons;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v;
        if (convertView == null) {
        	LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.footpon_listitem, null);
        }
        else
        	v = convertView;
        
        Footpon f = footpons.get(position);
        if (f != null) {
            TextView storeName = (TextView) v.findViewById(R.id.store_name);
            TextView description = (TextView) v.findViewById(R.id.realDescription);
            TextView steps = (TextView) v.findViewById(R.id.steps_required);
            if (storeName != null) {
             	storeName.setText(f.getStoreName());                            
            }
            if (description != null) {
               	description.setText(f.getRealDescription());
            }
            if (steps != null) {
               	steps.setText(String.valueOf(f.getStepsRequired()));
            }
            
    		ImageView newLogo = (ImageView) v.findViewById(R.id.ItemImage);
    		
    		if(f.getStoreName().equals("McDonald's"))
    		{	
    			newLogo.setImageResource(R.drawable.mcdonald);
    		}
    		
    		else if(f.getStoreName().equals("Nintendo World Store"))
    		{	
    			newLogo.setImageResource(R.drawable.nintendo);
    		}
    		
    		else if(f.getStoreName().equals("Wendy's"))
    		{	
    			newLogo.setImageResource(R.drawable.wendys);
    		}
    	
    		else if(f.getStoreName().equals("Burger King"))
    		{	
    			newLogo.setImageResource(R.drawable.burgerking);
    		}
    		
    		else
    		{
    			newLogo.setImageResource(R.drawable.mark);
    		}
        }
        return v;
	}
	
	public void notifyDataSetChanged() {
		super.notifyDataSetChanged();
	}
	
	public final int getCount() 
	{
		return footpons.size();
	}
	
	public Filter getFilter() {
        if (mFilter == null) {
            mFilter = new ListFilter();
        }
        return mFilter;
    }
	
	private class ListFilter extends Filter {
	    @Override
	    protected FilterResults performFiltering(CharSequence prefix) {
	        FilterResults results = new FilterResults();

	        if (orig_footpons == null) {
	        	orig_footpons = new ArrayList<Footpon>(footpons);
            }
	        
	        if (prefix == null || prefix.length() == 0) {
                results.values = orig_footpons;
                results.count = orig_footpons.size();
                return results;
	        } 
			else {
	            String prefixString = prefix.toString().toLowerCase();

	            final ArrayList<Footpon> fpList = orig_footpons;
	            final int count = fpList.size();
	            final ArrayList<Footpon> newFpList = new ArrayList<Footpon>(count);

	            for (int i = 0; i < count; i++) {
	                final Footpon fp = fpList.get(i);
	                final String valueText = fp.getStoreName().toLowerCase();
	                
	                // First match against the whole, non-splitted value
	                if (valueText.startsWith(prefixString)) {
	                	newFpList.add(fp);
	                } 
	                else {
	                    final String[] words = valueText.split(" ");
	                    final int wordCount = words.length;

	                    for (int k = 0; k < wordCount; k++) {
	                        if (words[k].startsWith(prefixString)) {
	                        	newFpList.add(fp);
	                            break;
	                        }
	                    }
	                }
	            }

	            results.values = newFpList;
	            results.count = newFpList.size();
	        }
	        Log.e("log_tag", "dd: ");
	        return results;
	    }

		@Override
		@SuppressWarnings("unchecked")
	    protected void publishResults(CharSequence constraint, FilterResults results) {
	    	footpons = (ArrayList<Footpon>) results.values;
	    	if (results.count > 0) {
	        	notifyDataSetChanged();
	        	Log.e("log_tag", "huan: ");
	        } else {
	            notifyDataSetInvalidated();
	        }
	    }
	}
}
