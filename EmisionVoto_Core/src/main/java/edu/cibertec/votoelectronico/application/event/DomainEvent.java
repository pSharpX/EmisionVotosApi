package edu.cibertec.votoelectronico.application.event;

public class DomainEvent {
	private final String contents;

	public DomainEvent(String contents) {
		this.contents = contents;
	}

	public String getContents() {
		return contents;
	}

	@Override
	public String toString() {
		return "DomainEvent [contents=" + contents + "]";
	}

}
