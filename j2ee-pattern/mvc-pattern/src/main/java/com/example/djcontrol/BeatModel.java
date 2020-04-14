package com.example.djcontrol;

import lombok.SneakyThrows;

import javax.sound.midi.*;
import java.util.ArrayList;
import java.util.List;

public class BeatModel implements BeatModelInterface, MetaEventListener {
	private Sequencer sequencer;
	private List<BeatObserver> beatObservers = new ArrayList<>();
	private List<BPMObserver> bpmObservers = new ArrayList<>();
	private int bpm = 90;
	private Sequence sequence;
	private Track track;

	@Override
	public void initialize() {
		setUpMidi();
		buildTrackAndStart();
	}

	@Override
	public void on() {
		sequencer.start();
		setBPM(90);
	}

	@Override
	public void off() {
		setBPM(0);
		sequencer.stop();
	}

	@Override
	public void setBPM(int bpm) {
		this.bpm = bpm;
		sequencer.setTempoInBPM(getBPM());
		notifyBPMObservers();
	}

	private void notifyBPMObservers() {
		bpmObservers.forEach(BPMObserver::updateBPM);
	}

	@Override
	public int getBPM() {
		return bpm;
	}

	public void beatEvent() {
		notifyBeatsObservers();
	}

	private void notifyBeatsObservers() {
		beatObservers.forEach(BeatObserver::updateBeat);
	}

	@Override
	public void registerObserver(BeatObserver o) {
		beatObservers.add(o);
	}

	@Override
	public void removeObserver(BeatObserver o) {
		int i = beatObservers.indexOf(o);
		if (i >= 0)
			beatObservers.remove(i);
	}

	@Override
	public void registerObserver(BPMObserver o) {
		bpmObservers.add(o);
	}

	@Override
	public void removeObserver(BPMObserver o) {
		int i = bpmObservers.indexOf(o);
		if (i >= 0)
			bpmObservers.remove(i);
	}

	@Override
	public void meta(MetaMessage message) {
		if (message.getType() == 47) {
			beatEvent();
			sequencer.start();
			setBPM(getBPM());
		}
	}

	@SneakyThrows
	public void setUpMidi() {
		sequencer = MidiSystem.getSequencer();
		sequencer.open();
		sequencer.addMetaEventListener(this);
		sequence = new Sequence(Sequence.PPQ, 4);
		track = sequence.createTrack();
		sequencer.setTempoInBPM(getBPM());
	}

	@SneakyThrows
	public void buildTrackAndStart() {
		int[] trackList = {35, 0, 46, 0};
		sequence.deleteTrack(null);
		track = sequence.createTrack();
		makeTracks(trackList);
		track.add(makeEvent(192, 9, 1, 0, 4));
		sequencer.setSequence(sequence);

	}

	private void makeTracks(int[] list) {
//		for (int i = 0; i < list.length; i++) {
//			int key = list[i];
//
//			if (key != 0) {
//				track.add(makeEvent(144, 9, key, 100, i));
//				track.add(makeEvent(128, 9, key, 100, i + 1));
//			}
//		}
		for (int i = 0; i < list.length; i++) {
			int key = list[i];
			if (key != 0) {
				track.add(makeEvent(144, 9, key, 100, i));
			}
			if (key != 0) {
				track.add(makeEvent(144, 9, key, 100, i));
				MidiEvent midiEvent =
						makeEvent(128, 9, key, 100, i + 1);
				track.add(midiEvent);
				track.add(makeMetaEvent(midiEvent, i + 2));
			}
		}
	}


	private MidiEvent makeMetaEvent(MidiEvent midiEvent, int tick) {
		MidiEvent event = null;

		try {
			MetaMessage message = new MetaMessage(47,
					midiEvent.getMessage().getMessage(),
					midiEvent.getMessage().getLength());
			event = new MidiEvent(message, tick);
		} catch (InvalidMidiDataException e) {
			e.printStackTrace();
		}

		return event;
	}

	@SneakyThrows
	private MidiEvent makeEvent(int comd, int chan, int one, int two, int tick) {
		ShortMessage a = new ShortMessage();
		a.setMessage(comd, chan, one, two);
		return new MidiEvent(a, tick);
	}
}