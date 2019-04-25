using UnityEngine;
using System.Collections;

public class planeMove : MonoBehaviour {
	public float speed = 5.0f;
	// Use this for initialization
	void Start () {
	
	}
	
	// Update is called once per frame
	void Update () {
		float h = Input.GetAxis ("Horizontal");
		transform.Translate (Vector3.right * h * speed * Time.deltaTime);
	}
}
