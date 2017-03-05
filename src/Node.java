public class Node {	
	public int type;
	public double value;
	public Node left,right;
	
	public Node(int type,double value) {
		this.type=type;
		this.value=value;
		this.left=this.right=null;
	}
	public Node(double value){
		this.value=value;
		this.left=this.right=null;
	}
	public Node(){
		this.left=this.right=null;
	}
}
