import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Entropy {
	public static int num_of_class_type;
	private ArrayList<ArrayList<Double>> data;
	private int num_of_row,num_of_column;
	public boolean isGainZero;
	public int max_gain_row_num,max_gain_col_num;
	public double decision;
	
	public void determine_node_value(ArrayList<ArrayList<Double>> data){
		this.data=data;
		isGainZero=false;
		double parent_entropy=calculate_par_entropy();
		if(parent_entropy==0){
			isGainZero=true;
			decision=data.get(0).get(num_of_column-1);
			return;
		}
		double maxen=0.0;
		for(int i=0;i<num_of_column-1;i++){
			Map<Double, Integer> isDuplicate=new HashMap<Double, Integer>();
			for(int j=0;j<num_of_row;j++){
				if(isDuplicate.containsKey(data.get(j).get(i)))continue;
				else isDuplicate.put(data.get(j).get(i), 1);
				double avg_entropy=calculate_child_entropy(i, data.get(j).get(i));
				if(maxen<(parent_entropy-avg_entropy)){
					maxen=(parent_entropy-avg_entropy);
					this.max_gain_row_num=j;this.max_gain_col_num=i;
				}
			}
		}
	}
	public double calculate_par_entropy(){
		double entropy=0.0;
		Map<Double,Integer> mp=new HashMap<Double,Integer>();
		num_of_row=data.size();num_of_column=data.get(0).size();	
		for(int i=0;i<num_of_row;i++){
			int cnt=(mp.get(data.get(i).get(num_of_column-1))==null)?0:mp.get(data.get(i).get(num_of_column-1));
			mp.put(data.get(i).get(num_of_column-1), cnt+1);
		}
		for (Double key: mp.keySet()) {
			double probability = (double)mp.get(key)/(double)num_of_row;
			entropy+=-probability*(Math.log(probability)/Math.log(2));
		}
		return entropy;
	}
	public double calculate_child_entropy(int index,double value){
		double left_entropy=0.0,right_entropy=0.0,n=0.0;
		double[] left=new double[num_of_class_type];
		double[] right=new double[num_of_class_type];
		for(int i=0;i<num_of_class_type;i++){
			left[i]=0.0;
			right[i]=0.0;
		}
		for(int i=0;i<num_of_row;i++){
			double c=data.get(i).get(num_of_column-1);
			if(data.get(i).get(index)<=value)left[(int)c]++;
			else{
				n++;right[(int)c]++;
			}
		}
		double m=num_of_row-n;
		for(double i:left){
			double probability=(double)i/(double)m;
			if(probability>0){
				left_entropy+=-1*probability*(Math.log(probability)/Math.log(2));
			}
		}
		for(double i:right){
			double probability=(double)i/(double)n;
			if(probability>0)right_entropy+=-probability*(Math.log(probability)/Math.log(2));
		}
		return ((m/num_of_row)*left_entropy+(n/num_of_row)*right_entropy);
	}
}
