public class OrderLine {

	private String COD_PROVINCIA;
	private String NOM_PROVINCIA;
	private String COD_MUNICIPIO;
	private String NOM_MUNICIPIO;
	private String TIPO;
	private String NOMBRE;
	private String DIRECCION;
	private String CP;
	private String TELEFONO;
	private String FAX;
	private String WEB;
	private String CATALOGO;
	private String EMAIL;
	private String CENTRAL;
	private String COD_CARACTER;
	private String DESC;
	private String CARACTER;
	private String DECRETO;
	
	
	public OrderLine(String COD_PROVINCIA,String NOM_PROVINCIA,String COD_MUNICIPIO,String NOM_MUNICIPIO,String TIPO,String NOMBRE,String DIRECCION,String CP,String TELEFONO,String FAX,String WEB,String CATALOGO,String EMAIL,String CENTRAL,String COD_CARACTER,String DESC,String CARACTER,String DECRETO) {
		this.COD_PROVINCIA = COD_PROVINCIA;
		this.NOM_PROVINCIA = NOM_PROVINCIA;
		this.COD_MUNICIPIO = COD_MUNICIPIO;
		this.NOM_MUNICIPIO = NOM_MUNICIPIO;
		this.TIPO = TIPO;
		this.NOMBRE = NOMBRE;
		this.DIRECCION = DIRECCION;
		this.CP = CP;
		this.TELEFONO = TELEFONO;
		this.FAX = FAX;
		this.WEB = WEB;
		this.CATALOGO = CARACTER;
		this.EMAIL = EMAIL;
		this.CENTRAL = CENTRAL;
		this.COD_CARACTER = COD_CARACTER;
		this.DESC = DESC;
		this.CARACTER = CARACTER;
		this.DECRETO = DECRETO;

		
		// Constructors, Getters, Setters and toString
	}
	
	public String getCOD_PROVINCIA() {
		return COD_PROVINCIA;
	}

	public void setCOD_PROVINCIA(String cOD_PROVINCIA) {
		COD_PROVINCIA = cOD_PROVINCIA;
	}

	public String getNOM_PROVINCIA() {
		return NOM_PROVINCIA;
	}

	public void setNOM_PROVINCIA(String nOM_PROVINCIA) {
		NOM_PROVINCIA = nOM_PROVINCIA;
	}

	public String getCOD_MUNICIPIO() {
		return COD_MUNICIPIO;
	}

	public void setCOD_MUNICIPIO(String cOD_MUNICIPIO) {
		COD_MUNICIPIO = cOD_MUNICIPIO;
	}

	public String getNOM_MUNICIPIO() {
		return NOM_MUNICIPIO;
	}

	public void setNOM_MUNICIPIO(String nOM_MUNICIPIO) {
		NOM_MUNICIPIO = nOM_MUNICIPIO;
	}

	public String getTIPO() {
		return TIPO;
	}

	public void setTIPO(String tIPO) {
		TIPO = tIPO;
	}

	public String getNOMBRE() {
		return NOMBRE;
	}

	public void setNOMBRE(String nOMBRE) {
		NOMBRE = nOMBRE;
	}

	public String getDIRECCION() {
		return DIRECCION;
	}

	public void setDIRECCION(String dIRECCION) {
		DIRECCION = dIRECCION;
	}

	public String getCP() {
		return CP;
	}

	public void setCP(String cP) {
		CP = cP;
	}

	public String getTELEFONO() {
		return TELEFONO;
	}

	public void setTELEFONO(String tELEFONO) {
		TELEFONO = tELEFONO;
	}

	public String getFAX() {
		return FAX;
	}

	public void setFAX(String fAX) {
		FAX = fAX;
	}

	public String getWEB() {
		return WEB;
	}

	public void setWEB(String wEB) {
		WEB = wEB;
	}

	public String getCATALOGO() {
		return CATALOGO;
	}

	public void setCATALOGO(String cATALOGO) {
		CATALOGO = cATALOGO;
	}

	public String getEMAIL() {
		return EMAIL;
	}

	public void setEMAIL(String eMAIL) {
		EMAIL = eMAIL;
	}

	public String getCENTRAL() {
		return CENTRAL;
	}

	public void setCENTRAL(String cENTRAL) {
		CENTRAL = cENTRAL;
	}

	public String getCOD_CARACTER() {
		return COD_CARACTER;
	}

	public void setCOD_CARACTER(String cOD_CARACTER) {
		COD_CARACTER = cOD_CARACTER;
	}

	public String getDESC() {
		return DESC;
	}

	public void setDESC(String dESC) {
		DESC = dESC;
	}

	public String getCARACTER() {
		return CARACTER;
	}

	public void setCARACTER(String cARACTER) {
		CARACTER = cARACTER;
	}

	public String getDECRETO() {
		return DECRETO;
	}

	public void setDECRETO(String dECRETO) {
		DECRETO = dECRETO;
	}
	
	

	public OrderLine()
	{}
}

