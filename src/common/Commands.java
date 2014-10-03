package common;

public enum Commands {
	GET_LIST_MATCH("GetListMatch"),
	GET_EQUIPES_MATCH("GetEquipesMatch"),
	GET_CHRONO("GetChrono"),
	GET_POINTAGE("GetPointage"),
	GET_PENALITE("GetPenalite"),
	GET_BUT("GetBut"),
	SET_BET("SetBet"),
	EQUIPE_MATCH("EquipeMatch"),
	LIST_MATCH("ListMatch"),
	CHRONO("Chrono");
	
	
	private final String value;
	
	private Commands(final String value)
	{
		this.value = value;
	}
	public String toString()
	{
		return value;
	}
}
