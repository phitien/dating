package dating.model;


public class PartnerCriteria {
	public Gender gender = Gender.MALE;
	public AgeRange ageRange;
	public IncomeRange incomeRange;

	public boolean match(Responder responder) {
		if (responder.isValid() && this.gender == responder.gender) {
			if (this.ageRange != null
					&& !this.ageRange.isInRange(responder.age)) {
				return false;
			}
			if (this.incomeRange != null
					&& !this.incomeRange.hasIntersection(responder.incomeRange)) {
				return false;
			}
			return true;
		}
		return false;
	}
}
