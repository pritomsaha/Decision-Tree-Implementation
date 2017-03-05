import java.util.ArrayList;
public class Tree {
	private Node root;
	private Entropy entropy=new Entropy();
	
	public Tree(ArrayList<ArrayList<Double>> data){
		root=build_tree(root,data);
	}
	public Node build_tree(Node current,ArrayList<ArrayList<Double>> data){
		
		entropy.determine_node_value(data);
		if(entropy.isGainZero){
			current=new Node(entropy.decision);
			return current;
		}
		current=new Node(entropy.max_gain_col_num, data.get(entropy.max_gain_row_num).get(entropy.max_gain_col_num));
		ArrayList<ArrayList<Double>> leftData=new ArrayList<ArrayList<Double>>();
		ArrayList<ArrayList<Double>> rightData=new ArrayList<ArrayList<Double>>();
		for(ArrayList<Double> r:data){
			if(current.value>=r.get(current.type))leftData.add(r);
			else rightData.add(r);
		}
		current.left=build_tree(current.left,leftData);
		current.right= build_tree(current.right,rightData);
		return current;
	}
	
	public int traverse(Node current,ArrayList<Double> testData){
		if(current.left==null&&current.right==null){
			return (current.value==testData.get(testData.size()-1))?1:0;
		}
		if(current.value>=testData.get(current.type))return traverse(current.left, testData);
		else return traverse(current.right, testData);
	}
	public Node getRoot() {
		return root;
	}
}
