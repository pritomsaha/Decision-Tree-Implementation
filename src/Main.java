import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;
public class Main {

	public static void main(String[] args) {
		/* Data will be numeric
		 * Data will be separated by ','
		 * class will be in the last
		 * class will be started from zero
		 */
		
		FileReader fileReader=new FileReader();
		ArrayList<ArrayList<Double>> data= fileReader.getRecords();
		Collections.sort(data,new Comparator<ArrayList<Double>>() {
			@Override
			public int compare(ArrayList<Double> a, ArrayList<Double> b) {
				return (int) (a.get(a.size()-1)-b.get(b.size()-1));
			}
		});
		int col_num=data.get(0).size();
		double number_of_class=data.get(data.size()-1).get(col_num-1)+1;
		Entropy.num_of_class_type=(int)number_of_class;
		double[] count=new double[(int) number_of_class];
		for (ArrayList<Double> d : data) {
			double index=d.get(col_num-1);
			count[(int)index]++;
		}
		ArrayList<ArrayList<Double>> temp=new ArrayList<ArrayList<Double>>();
		ArrayList<ArrayList<Double>> testData=new ArrayList<ArrayList<Double>>();
		Random random=new Random();
		double total_accuracy=0.0,partial_accuracy=0.0;
		for(int i=0;i<10;i++){
			temp.clear(); testData.clear(); temp.addAll(data);
			int l=0,r;
			for(int j=0;j<number_of_class;j++){
				int k=(int)Math.ceil(((count[j]*10)/100));r=(int)count[j];
				for(int n=0;n<k;n++){
					int removed_index=l+random.nextInt(r);
					testData.add(temp.get(removed_index));
					temp.remove(removed_index);	r--;				
				}
				l=l+(int)count[j]-k;
			}
			Tree tree=new Tree(temp);
			partial_accuracy=0.0;
			for (ArrayList<Double> arrayList : testData) {
				partial_accuracy+=tree.traverse(tree.getRoot(),arrayList);
			}
			partial_accuracy=(partial_accuracy*100.0)/(double)testData.size();
			total_accuracy+=partial_accuracy;
		}
		total_accuracy=total_accuracy/10.0;
		System.out.println("Accuracy :"+total_accuracy+"%");
	}
}
