using UnityEngine;
using System.Collections;

public class sphereMove : MonoBehaviour {
	private Rigidbody rg;
	private float speed = 40.0f;
	// Use this for initialization
	void Start () {
		rg = this.GetComponent<Rigidbody> ();
	}
	
	// Update is called once per frame
	void Update () {
		if (transform.position.y < -10) {
			Destroy (this);
			Application.Quit ();
		}
	}
	void OnCollisionEnter(Collision coll){
		print ("碰撞");
		rg.AddForce(new Vector3(Random.Range(-0.2f,0.2f),1.0f,0)*speed);
	}
}
