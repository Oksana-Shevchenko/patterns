package com.example.djcontrol;

public class Demo {
	public static void main(String[] args) {
		BeatModelInterface model = new BeatModel();
		ControllerInterface controller = new BeatController(model);

		HeartModel heartModel = new HeartModel();
		ControllerInterface heartController = new HeartController(heartModel);
	}
}
