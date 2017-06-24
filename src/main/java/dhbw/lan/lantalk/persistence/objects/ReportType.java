package dhbw.lan.lantalk.persistence.objects;

public enum ReportType {
	Spam, Insult, Inappropriate;

	public static ReportType fromString(String value) {
		switch (value.toLowerCase()) {
		case "spam":
			return Spam;
		case "insult":
			return Insult;
		case "inappropriate":
			return Inappropriate;
		default:
			return null;
		}
	}
}
