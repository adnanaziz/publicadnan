package net.homeip.jtjang.MileageRun;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class MileageRunActivity extends Activity {
	public static final String KEY_DEPARTCODE = "DepartCode";
	public static final String KEY_YEARMONTH = "YearMonth";
	private String departCode;
	private String yearMonth;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		final AutoCompleteTextView worldCodesText = (AutoCompleteTextView) findViewById(R.id.ac_worldcodes);
		ArrayAdapter<String> worldCodesAdapter = new ArrayAdapter<String>(this, R.layout.world_codes, WORLD_CODES);
		worldCodesText.setAdapter(worldCodesAdapter);

		final Spinner usCodes = (Spinner) findViewById(R.id.uscodes);
		ArrayAdapter<CharSequence> usCodesAdapter = ArrayAdapter.createFromResource(
				this, R.array.uscodes_array, android.R.layout.simple_spinner_item);
		usCodesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		usCodes.setAdapter(usCodesAdapter);
		usCodes.setOnItemSelectedListener(new OnItemSelectedListener()  {
			public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
				AutoCompleteTextView worldCodesText = (AutoCompleteTextView) findViewById(R.id.ac_worldcodes);
				worldCodesText.setText(parent.getItemAtPosition(pos).toString());
			}

			public void onNothingSelected(AdapterView<?> parent) {
				// Do nothing.
			}
		});

		final EditText monthText = (EditText) findViewById(R.id.month);

		Button goButton = (Button) findViewById(R.id.go);
		goButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				departCode = worldCodesText.getText().toString();
				yearMonth = monthText.getText().toString();

				if (departCode.length()!=3 || yearMonth.length()!=6 ||
						!Character.isDigit(yearMonth.charAt(0)) ||
						!Character.isDigit(yearMonth.charAt(1)) ||
						!Character.isDigit(yearMonth.charAt(2)) ||
						!Character.isDigit(yearMonth.charAt(3)) ||
						!Character.isDigit(yearMonth.charAt(4)) ||
						!Character.isDigit(yearMonth.charAt(5)) ||
						Integer.parseInt(yearMonth.substring(0, 4))<2011 ||
						Integer.parseInt(yearMonth.substring(4))>12 ||
						Integer.parseInt(yearMonth.substring(4))<1 ) {
					Toast.makeText(v.getContext(), "Wrong format!", Toast.LENGTH_SHORT).show();
				} else {
					Intent showIntent = null;

					showIntent = new Intent(v.getContext(), ShowMileageActivity.class);
					showIntent.putExtra(KEY_DEPARTCODE, departCode);
					showIntent.putExtra(KEY_YEARMONTH, yearMonth);

					startActivity(showIntent);
				}
			}
		});
	}

	
	
	
	private static final String[] WORLD_CODES = new String [] {
		"AAC","AAE","AAL","AAM","AAN","AAQ","AAR","AAT","AAX","ABC","ABE","ABI",
		"ABJ","ABK","ABL","ABM","ABQ","ABR","ABS","ABT","ABV","ABX","ABY","ABZ",
		"ACA","ACC","ACE","ACH","ACI","ACK","ACT","ACV","ACY","ADA","ADB","ADD",
		"ADE","ADJ","ADK","ADL","ADQ","ADZ","AED","AEP","AER","AES","AET","AEX",
		"AEY","AFA","AGA","AGB","AGC","AGF","AGH","AGN","AGP","AGR","AGS","AGT",
		"AGU","AHB","AHN","AHO","AHU","AIA","AIN","AIY","AJA","AJF","AJI","AJN",
		"AJR","AJU","AKF","AKJ","AKL","AKN","AKU","AKV","ALA","ALB","ALC","ALE",
		"ALF","ALG","ALL","ALM","ALO","ALP","ALS","ALW","ALY","AMA","AMD","AMH",
		"AMI","AMM","AMQ","AMS","ANB","ANC","ANF","ANG","ANI","ANR","ANU","ANV",
		"AOG","AOI","AOJ","AOK","AOO","AOR","AOU","APF","APL","APN","APW","AQG",
		"AQI","AQJ","AQP","ARH","ARI","ARM","ARN","ART","ASB","ASD","ASE","ASF",
		"ASJ","ASM","ASO","ASP","ASR","ASU","ASW","ATC","ATH","ATL","ATM","ATQ",
		"ATW","ATY","ATZ","AUA","AUC","AUG","AUH","AUS","AUX","AVK","AVL","AVN",
		"AVP","AVV","AWZ","AXA","AXD","AXM","AXP","AXT","AYQ","AYT","AZA","AZD",
		"AZN","AZO","AZS","BAH","BAK","BAL","BAQ","BAU","BAV","BAX","BAY","BBA",
		"BBI","BBK","BBN","BBU","BCD","BCI","BCN","BCQ","BDA","BDB","BDJ","BDL",
		"BDO","BDQ","BDR","BDS","BDU","BEB","BED","BEG","BEH","BEI","BEK","BEL",
		"BEN","BEO","BER","BES","BET","BEW","BEY","BFD","BFF","BFI","BFL","BFN",
		"BFS","BGA","BGF","BGI","BGK","BGM","BGO","BGR","BGY","BHB","BHD","BHE",
		"BHH","BHI","BHK","BHM","BHO","BHQ","BHS","BHU","BHV","BHX","BHY","BIA",
		"BIK","BIL","BIM","BIO","BIQ","BIS","BJI","BJL","BJM","BJR","BJX","BKI",
		"BKK","BKL","BKM","BKO","BKQ","BKW","BKX","BLA","BLE","BLF","BLG","BLI",
		"BLK","BLL","BLQ","BLR","BLT","BLZ","BMA","BME","BMG","BMI","BMP","BMV",
		"BNA","BND","BNE","BNJ","BNK","BNN","BNS","BNX","BOB","BOD","BOG","BOI",
		"BOJ","BOM","BON","BOO","BOS","BPN","BPS","BPT","BPX","BQK","BQN","BQU",
		"BRC","BRD","BRE","BRI","BRL","BRM","BRN","BRO","BRQ","BRR","BRS","BRT",
		"BRU","BRW","BSB","BSK","BSL","BTH","BTI","BTM","BTR","BTS","BTU","BTV",
		"BUD","BUF","BUQ","BUR","BUX","BUZ","BVB","BVC","BVE","BVG","BVI","BWA",
		"BWD","BWI","BWN","BWT","BXN","BXU","BYN","BYU","BZE","BZG","BZN","BZO",
		"BZR","BZV","CAC","CAE","CAG","CAI","CAJ","CAK","CAL","CAN","CAS","CAY",
		"CBB","CBG","CBL","CBO","CBR","CCF","CCJ","CCP","CCS","CCU","CDB","CDC",
		"CDG","CDH","CDR","CDV","CDW","CEB","CEC","CED","CEI","CEK","CEN","CER",
		"CEZ","CFA","CFE","CFR","CFS","CFU","CGA","CGB","CGD","CGH","CGI","CGK",
		"CGN","CGO","CGP","CGQ","CGR","CGX","CGY","CHA","CHC","CHI","CHO","CHQ",
		"CHS","CIA","CIC","CID","CIH","CIU","CIW","CIX","CJA","CJB","CJC","CJJ",
		"CJL","CJS","CJT","CJU","CKB","CKG","CKS","CKY","CLD","CLE","CLJ","CLL",
		"CLM","CLO","CLQ","CLT","CLY","CMB","CME","CMF","CMG","CMH","CMI","CMN",
		"CMX","CND","CNF","CNJ","CNM","CNQ","CNS","CNX","CNY","CNY","COD","COK",
		"COO","COQ","COR","COS","COU","CPC","CPC","CPD","CPE","CPH","CPO","CPQ",
		"CPR","CPT","CPV","CPX","CRD","CRG","CRI","CRK","CRP","CRU","CRV","CRW",
		"CSG","CSX","CTA","CTG","CTL","CTM","CTS","CTU","CUC","CUE","CUL","CUM",
		"CUN","CUP","CUR","CUU","CUZ","CVG","CVJ","CVM","CVN","CWA","CWB","CWL",
		"CWT","CXH","CXI","CXJ","CYB","CYF","CYI","CYR","CYS","CZE","CZL","CZM",
		"CZS","CZX","DAB","DAC","DAD","DAL","DAM","DAN","DAR","DAY","DBM","DBO",
		"DBQ","DBV","DCA","DCF","DDC","DDG","DEA","DEB","DEC","DEL","DEM","DEN",
		"DET","DFW","DGA","DGO","DGT","DHA","DHM","DHM","DHN","DIB","DIG","DIJ",
		"DIK","DIL","DIN","DIR","DIU","DIY","DJE","DJJ","DKR","DLA","DLC","DLG",
		"DLH","DLI","DLM","DLU","DLZ","DME","DMM","DNA","DND","DNF","DNH","DNK",
		"DNM","DNV","DNZ","DOH","DOK","DOL","DOM","DPA","DPL","DPO","DPS","DRO",
		"DRS","DRT","DRW","DSA","DSE","DSI","DSK","DSM","DTM","DTW","DUB","DUD",
		"DUJ","DUQ","DUR","DUS","DUT","DVL","DVO","DWD","DXB","DYG","EAM","EAR",
		"EAS","EAT","EAU","EBA","EBB","EBJ","ECH","EDI","EDR","EEN","EFD","EFL",
		"EGC","EGE","EGO","EHM","EIN","EIS","EJA","EJH","EKO","ELD","ELH","ELM",
		"ELP","ELQ","ELS","ELY","EMA","EMD","ENA","ENS","ENV","EOH","EPR","EQS",
		"ERF","ERI","ERS","ERZ","ESB","ESC","ESD","ESR","ETH","ETZ","EUG","EUN",
		"EVE","EVN","EVV","EWB","EWN","EWR","EXT","EYW","EZE","EZF","FAE","FAI",
		"FAO","FAR","FAT","FAY","FCA","FCO","FDE","FDF","FDH","FEN","FEZ","FFM",
		"FHU","FIH","FKB","FKL","FLG","FLL","FLN","FLO","FLR","FMA","FMN","FMO",
		"FNC","FNI","FNL","FNT","FOC","FOD","FOE","FOR","FPO","FRA","FRD","FRM",
		"FRO","FRS","FRU","FSD","FSM","FSP","FTE","FUE","FUG","FUK","FUN","FWA",
		"FYV","GAJ","GAL","GAU","GBD","GBE","GBG","GCC","GCC","GCI","GCK","GCM",
		"GCN","GCY","GDC","GDL","GDN","GDT","GDV","GDX","GEA","GEG","GEO","GET",
		"GFF","GFK","GGG","GGT","GGW","GHB","GHT","GIB","GIG","GIL","GIZ","GJT",
		"GKA","GLA","GLD","GLF","GLH","GLS","GLT","GLV","GMU","GNB","GND","GNV",
		"GOA","GOH","GOI","GOJ","GON","GOT","GOV","GPI","GPS","GPT","GRB","GRE",
		"GRI","GRJ","GRO","GRQ","GRR","GRU","GRX","GRZ","GSO","GSP","GST","GTF",
		"GTO","GTR","GUA","GUB","GUC","GUM","GUP","GUR","GVA","GVT","GWD","GWT",
		"GWY","GXQ","GYA","GYD","GYE","GYM","GYN","GYY","HAC","HAD","HAG","HAH",
		"HAJ","HAK","HAM","HAN","HAS","HAU","HAV","HBA","HBE","HBT","HDB","HDF",
		"HDN","HDS","HDY","HEA","HEH","HEL","HER","HET","HFA","HFE","HFT","HGA",
		"HGH","HGN","HGR","HGU","HHH","HHN","HIB","HID","HII","HIJ","HIL","HIN",
		"HIR","HIS","HJR","HJT","HKD","HKG","HKK","HKN","HKT","HKY","HLA","HLD",
		"HLN","HLP","HLZ","HMA","HMO","HNA","HNH","HNL","HNM","HNS","HOB","HOF",
		"HOM","HON","HOQ","HOR","HOT","HOU","HPA","HPB","HPH","HPN","HPV","HRB",
		"HRE","HRG","HRK","HRL","HRO","HSG","HSI","HSM","HSN","HSV","HTI","HTN",
		"HTS","HUF","HUH","HUI","HUM","HUN","HUQ","HUX","HUY","HVB","HVD","HVG",
		"HVN","HVR","HWN","HYA","HYD","HYG","HYN","HYS","IAD","IAH","IAS","IBZ",
		"ICN","ICN","ICT","IDA","IDR","IEG","IEV","IFN","IFO","IFP","IGA","IGG",
		"IGM","IGR","IGU","IJK","IKO","IKT","ILE","ILF","ILG","ILI","ILM","ILO",
		"ILQ","ILY","IMF","IMP","IMT","INC","IND","INL","INN","INT","INU","INV",
		"IOA","IOM","IOS","IPC","IPH","IPI","IPT","IQM","IQQ","IQT","IRJ","IRK",
		"ISA","ISB","ISC","ISG","ISH","ISN","ISO","ISP","IST","ITB","ITH","ITM",
		"ITO","IUE","IVC","IVL","IWD","IWJ","IXA","IXB","IXC","IXD","IXE","IXG",
		"IXH","IXI","IXJ","IXK","IXL","IXM","IXN","IXP","IXQ","IXR","IXS","IXT",
		"IXU","IXV","IXW","IXY","IXZ","IYK","IZO","JAC","JAG","JAI","JAL","JAN",
		"JAT","JAV","JAX","JBR","JCA","JDH","JDO","JDP","JED","JER","JFK","JGA",
		"JHB","JHE","JHG","JHM","JHW","JIB","JIJ","JIK","JIL","JIM","JJN","JKG",
		"JKH","JLN","JMC","JMK","JMM","JMO","JMS","JNB","JNS","JNU","JNX","JOE",
		"JOG","JOI","JON","JOS","JPA","JRA","JRH","JRO","JSH","JSI","JST","JSY",
		"JTR","JTY","JUJ","JUL","JYV","JZH","KAB","KAE","KAG","KAJ","KAL","KAN",
		"KAO","KAT","KBL","KBP","KBR","KBV","KCA","KCG","KCH","KCL","KCQ","KCZ",
		"KDH","KDU","KEF","KEH","KEJ","KEL","KEM","KEP","KER","KGC","KGD","KGI",
		"KGK","KGL","KGS","KHG","KHH","KHI","KHN","KHR","KHV","KID","KIF","KIJ",
		"KIM","KIN","KIR","KIT","KIV","KIV","KIX","KJA","KKC","KKE","KKH","KKJ",
		"KKN","KLH","KLL","KLO","KLR","KLU","KLV","KLW","KMA","KMG","KMI","KMJ",
		"KMO","KMQ","KNH","KNS","KNW","KNX","KOA","KOG","KOI","KOJ","KOK","KOW",
		"KPN","KPO","KPS","KPV","KQA","KRF","KRK","KRL","KRN","KRP","KRR","KRS",
		"KRT","KSA","KSC","KSD","KSF","KSH","KSJ","KSO","KSQ","KSU","KSY","KTA",
		"KTM","KTN","KTP","KTR","KTT","KTW","KUA","KUD","KUF","KUH","KUL","KUN",
		"KUO","KUS","KUV","KVA","KVC","KVL","KWA","KWE","KWI","KWJ","KWL","KWN",
		"KYA","KZI","KZN","KZS","LAD","LAE","LAF","LAI","LAN","LAO","LAP","LAQ",
		"LAR","LAS","LAW","LAX","LBA","LBB","LBE","LBF","LBG","LBL","LBP","LBS",
		"LBU","LBV","LCA","LCE","LCG","LCH","LCJ","LCY","LDB","LDE","LDH","LDU",
		"LDY","LEA","LEB","LEC","LED","LEH","LEI","LEJ","LEN","LET","LEX","LFT",
		"LFW","LGA","LGB","LGG","LGI","LGK","LGL","LGP","LGW","LHE","LHR","LHW",
		"LIF","LIG","LIH","LIL","LIM","LIN","LIR","LIS","LIT","LJG","LJU","LKE",
		"LKE","LKH","LKL","LKN","LKO","LLA","LLI","LLW","LLY","LMM","LMN","LMP",
		"LMQ","LMT","LNJ","LNK","LNS","LNV","LNY","LNZ","LOS","LOV","LPA","LPB",
		"LPI","LPL","LPP","LPQ","LPS","LPT","LRD","LRE","LRH","LRM","LRM","LRS",
		"LRT","LRU","LSC","LSE","LSI","LSP","LSQ","LSS","LST","LSY","LTD","LTI",
		"LTN","LTO","LUA","LUD","LUG","LUK","LUM","LUN","LUP","LUQ","LUX","LVI",
		"LVT","LWB","LWK","LWO","LWS","LWT","LWY","LXA","LXG","LXR","LYA","LYB",
		"LYC","LYG","LYH","LYP","LYR","LYS","LZC","LZH","LZO","MAA","MAB","MAD",
		"MAF","MAG","MAH","MAJ","MAM","MAN","MAO","MAR","MAY","MAZ","MBA","MBD",
		"MBE","MBJ","MBL","MBS","MBX","MCE","MCG","MCI","MCK","MCM","MCN","MCO",
		"MCP","MCQ","MCT","MCW","MCY","MCZ","MDC","MDE","MDG","MDH","MDL","MDQ",
		"MDT","MDW","MDZ","MED","MED","MEH","MEI","MEL","MEM","MER","MES","MEU",
		"MEX","MEY","MFE","MFM","MFN","MFR","MGA","MGB","MGF","MGH","MGL","MGM",
		"MGQ","MGW","MHD","MHG","MHH","MHK","MHQ","MHT","MHU","MIA","MID","MIE",
		"MIG","MIM","MIR","MJD","MJF","MJT","MJV","MKC","MKE","MKG","MKK","MKK",
		"MKL","MKM","MKS","MKW","MKY","MLA","MLB","MLE","MLG","MLH","MLI","MLM",
		"MLN","MLO","MLS","MLT","MLU","MLW","MLX","MMB","MME","MMH","MMJ","MMK",
		"MMO","MMU","MMX","MMY","MNF","MNI","MNL","MNM","MOB","MOC","MOD","MOL",
		"MOT","MOW","MOZ","MPA","MPB","MPK","MPL","MPM","MPN","MQL","MQM","MQN",
		"MQP","MQS","MQT","MQX","MRA","MRD","MRK","MRS","MRU","MRV","MRY","MRZ",
		"MSE","MSJ","MSL","MSN","MSO","MSP","MSQ","MSR","MSS","MST","MSU","MSY",
		"MTF","MTH","MTJ","MTS","MTT","MTY","MUB","MUC","MUE","MUN","MUR","MUX",
		"MVD","MVN","MVY","MWA","MWH","MWZ","MXL","MXP","MXS","MXV","MXW","MXZ",
		"MYA","MYE","MYG","MYJ","MYR","MYU","MYY","MZG","MZL","MZT","MZV","NAA",
		"NAG","NAH","NAK","NAN","NAP","NAS","NAT","NAY","NBO","NCA","NCE","NCL",
		"NCU","NCY","NDG","NDJ","NDR","NEC","NER","NEU","NEV","NFO","NGB","NGO",
		"NGS","NHA","NIM","NIX","NJC","NKC","NKG","NLA","NLD","NLG","NLK","NLP",
		"NMA","NME","NNG","NNM","NNY","NOC","NOH","NOU","NPE","NPL","NQN","NQY",
		"NRA","NRK","NRN","NRT","NSB","NSI","NSK","NSN","NST","NTE","NTG","NTL",
		"NTQ","NTT","NTY","NUE","NUX","NVT","NWA","NWI","NYU","OAG","OAJ","OAK",
		"OAX","OBO","OCJ","ODE","ODN","ODS","ODW","ODY","OER","OFK","OGG","OGS",
		"OHD","OIM","OIT","OKA","OKC","OKD","OKJ","OLB","OLF","OLM","OLP","OMA",
		"OME","OMR","OMS","ONG","ONJ","ONT","OOK","OOL","OOM","OPF","OPO","ORB",
		"ORD","ORF","ORH","ORK","ORL","ORN","ORY","OSA","OSD","OSH","OSL","OSR",
		"OSZ","OTH","OTM","OTP","OTZ","OUA","OUD","OUI","OUL","OVB","OVD","OVD",
		"OWB","OWD","OXB","OXR","OZH","OZZ","PAC","PAD","PAH","PAM","PAO","PAP",
		"PAR","PAS","PAT","PAZ","PBC","PBD","PBI","PBM","PBO","PCA","PCL","PCT",
		"PDG","PDL","PDP","PDS","PDT","PDX","PEE","PEG","PEI","PEK","PEM","PEN",
		"PER","PES","PEW","PFB","PFN","PFO","PGA","PGF","PGV","PGX","PHC","PHE",
		"PHF","PHL","PHO","PHS","PHW","PHX","PIA","PIB","PID","PIE","PIH","PIK",
		"PIP","PIR","PIS","PIT","PIU","PIW","PJG","PKB","PKC","PKE","PKR","PKU",
		"PKZ","PLB","PLH","PLM","PLN","PLO","PLQ","PLS","PLS","PLU","PLW","PLZ",
		"PMC","PMD","PMF","PMI","PML","PMO","PMR","PMV","PMW","PNA","PNC","PNH",
		"PNI","PNK","PNL","PNQ","PNR","PNS","PNZ","POA","POG","POM","POP","POR",
		"POS","POU","POZ","PPG","PPP","PPS","PPT","PQC","PQI","PQQ","PRC","PRG",
		"PRI","PRJ","PRN","PSA","PSC","PSE","PSG","PSI","PSM","PSO","PSP","PSR",
		"PSS","PSZ","PTF","PTG","PTH","PTJ","PTP","PTU","PTY","PUB","PUF","PUJ",
		"PUQ","PUS","PUW","PUY","PUZ","PVC","PVD","PVG","PVH","PVK","PVR","PVU",
		"PWK","PWM","PWT","PXM","PXO","PXU","PZB","PZE","PZH","PZO","QBF","QBL",
		"QDU","QEC","QEJ","QGG","QGH","QIE","QKB","QKL","QKO","QMQ","QRO","QSY",
		"QUB","QXB","QZL","QZT","RAB","RAE","RAH","RAI","RAJ","RAK","RAO","RAP",
		"RAR","RAS","RBA","RBR","RCB","RCE","RDD","RDG","RDM","RDU","RDZ","REC",
		"REG","REK","REL","REP","RES","REU","REX","RFD","RFD","RFP","RGA","RGL",
		"RGN","RHI","RHO","RIC","RIO","RIS","RIW","RIX","RJL","RKD","RKS","RKT",
		"RKV","RLG","RMA","RMF","RMI","RNB","RNN","RNO","RNS","ROA","ROB","ROC",
		"ROK","ROM","ROP","ROR","ROS","ROT","ROV","ROW","RPN","RPR","RRG","RRO",
		"RSA","RSD","RST","RSU","RSW","RTB","RTM","RUH","RUI","RUN","RUT","RVN",
		"RWI","RXS","RYK","RZE","SAB","SAF","SAH","SAL","SAN","SAP","SAQ","SAT",
		"SAV","SAY","SBA","SBH","SBN","SBP","SBW","SBY","SBZ","SCC","SCE","SCK",
		"SCL","SCM","SCN","SCQ","SCU","SCX","SDE","SDF","SDJ","SDK","SDL","SDN",
		"SDP","SDQ","SDR","SDU","SDV","SDX","SDY","SEA","SEB","SEL","SEZ","SFA",
		"SFB","SFG","SFJ","SFL","SFN","SFO","SFQ","SFT","SGC","SGD","SGF","SGN",
		"SGO","SGU","SGY","SHA","SHB","SHC","SHC","SHD","SHE","SHJ","SHM","SHO",
		"SHP","SHR","SHV","SHW","SID","SIG","SIN","SIP","SIR","SIT","SJC","SJD",
		"SJI","SJJ","SJK","SJO","SJP","SJT","SJU","SJW","SKB","SKD","SKE","SKG",
		"SKN","SKP","SKS","SKU","SKZ","SLA","SLC","SLD","SLK","SLL","SLM","SLN",
		"SLP","SLU","SLW","SLZ","SMF","SMI","SML","SMM","SMR","SMS","SMX","SNA",
		"SNB","SNE","SNN","SNO","SNP","SNU","SOF","SOG","SOM","SOP","SOU","SOW",
		"SOY","SPB","SPC","SPD","SPI","SPN","SPR","SPS","SPU","SPW","SQH","SQL",
		"SQO","SRE","SRG","SRQ","SRX","SSA","SSB","SSG","SSH","SSJ","SSQ","STC",
		"STD","STI","STL","STM","STN","STP","STR","STS","STT","STX","SUB","SUE",
		"SUF","SUJ","SUL","SUN","SUV","SUX","SVC","SVD","SVG","SVJ","SVL","SVO",
		"SVQ","SVU","SVX","SVZ","SWA","SWF","SWP","SWQ","SXB","SXF","SXL","SXM",
		"SXR","SYD","SYM","SYO","SYR","SYX","SYY","SYZ","SZB","SZF","SZG","SZT",
		"SZX","SZZ","TAB","TAC","TAE","TAG","TAI","TAK","TAM","TAO","TAP","TAS",
		"TAT","TAV","TBB","TBN","TBP","TBS","TBT","TBU","TBZ","TCB","TCG","TCI",
		"TCL","TCQ","TDD","TEB","TED","TEN","TER","TEX","TEZ","TFF","TFN","TFS",
		"TGD","TGG","TGM","TGU","TGZ","THE","THF","THK","THN","THR","THU","TIA",
		"TIE","TIF","TIJ","TIM","TIP","TIQ","TIS","TIU","TIV","TIZ","TJA","TJM",
		"TKE","TKK","TKN","TKQ","TKS","TKU","TLC","TLH","TLL","TLN","TLS","TLV",
		"TMG","TMJ","TMP","TMS","TMT","TMW","TNA","TNG","TNK","TNN","TNR","TOB",
		"TOG","TOL","TOS","TOY","TPA","TPE","TPL","TPP","TPQ","TPR","TPS","TRC",
		"TRD","TRE","TRF","TRG","TRI","TRK","TRN","TRO","TRS","TRU","TRV","TRZ",
		"TSA","TSE","TSF","TSJ","TSM","TSN","TSR","TSS","TST","TSV","TSZ","TTB",
		"TTE","TTJ","TTN","TTT","TUC","TUF","TUG","TUI","TUK","TUL","TUN","TUO",
		"TUP","TUR","TUS","TUU","TVC","TVF","TVL","TVU","TWA","TWB","TWF","TXG",
		"TXK","TXL","TXN","TYN","TYR","TYS","TZA","TZN","TZX","UAK","UAQ","UBA",
		"UBJ","UBP","UCA","UDI","UDJ","UDR","UEL","UEO","UET","UFA","UGA","UGB",
		"UGC","UIB","UIH","UIN","UIO","UIP","ULD","ULG","ULN","ULO","ULY","ULZ",
		"UME","UMR","UNA","UNI","UNK","UNR","UNT","UPG","UPN","URC","URO","URT",
		"URY","USH","USM","USN","UTH","UTN","UTT","UUD","UUN","UUS","UVF","VAA",
		"VAI","VAK","VAN","VAR","VAS","VAV","VBS","VBY","VCE","VCP","VCT","VDA",
		"VDC","VDM","VDS","VDZ","VER","VEY","VFA","VGO","VGT","VHM","VHY","VIE",
		"VII","VIJ","VIL","VIS","VIT","VIX","VKG","VKO","VLC","VLD","VLG","VLI",
		"VLL","VLN","VNA","VNO","VNS","VOG","VPS","VQS","VRA","VRB","VRK","VRN",
		"VSA","VSG","VST","VTE","VTZ","VUP","VVI","VVO","VXO","WAE","WAG","WAT",
		"WAW","WBU","WDG","WDH","WEH","WEI","WGA","WGE","WHK","WIC","WIL","WIN",
		"WJU","WKA","WKJ","WKK","WLG","WMH","WNA","WNP","WNZ","WRE","WRG","WRI",
		"WRL","WRO","WSN","WST","WSX","WSZ","WTL","WUH","WUS","WVB","WWK","WWT",
		"WXN","WYA","WYS","XAP","XAW","XAZ","XBR","XCI","XCM","XDL","XDM","XDU",
		"XEE","XEH","XEJ","XEK","XEL","XEM","XEY","XFD","XFE","XFG","XFL","XFM",
		"XFN","XFO","XFQ","XFS","XFV","XGJ","XGK","XGR","XGY","XHM","XHS","XIA",
		"XIB","XID","XIL","XIM","XIO","XIP","XIY","XJL","XJQ","XKH","XLV","XLZ",
		"XMN","XNA","XNN","XOK","XPB","XPN","XPX","XQP","XQU","XRP","XRY","XSC",
		"XTY","XUZ","XVV","XWY","XZB","XZC","YAI","YAK","YAM","YAP","YAT","YAY",
		"YAY","YAZ","YBA","YBB","YBC","YBG","YBL","YBP","YBR","YBW","YBX","YCA",
		"YCB","YCC","YCD","YCG","YCM","YCM","YCO","YCY","YDF","YDI","YDN","YDQ",
		"YDS","YEC","YEG","YEK","YEL","YEV","YEY","YFA","YFB","YFC","YFO","YFS",
		"YGB","YGG","YGJ","YGK","YGL","YGN","YGO","YGP","YGR","YGT","YGW","YHB",
		"YHD","YHH","YHI","YHK","YHM","YHN","YHR","YHU","YHY","YHZ","YIB","YIF",
		"YIH","YIK","YIN","YIO","YIP","YIW","YJA","YJT","YKA","YKF","YKG","YKL",
		"YKM","YKQ","YKU","YKZ","YLC","YLD","YLQ","YLW","YMM","YMO","YMP","YMQ",
		"YMS","YMT","YMX","YNA","YNB","YND","YNG","YNJ","YNT","YNY","YNZ","YOJ",
		"YOO","YOP","YOW","YPA","YPE","YPF","YPH","YPJ","YPR","YPT","YPW","YPX",
		"YPZ","YQB","YQC","YQD","YQG","YQH","YQI","YQK","YQL","YQM","YQQ","YQR",
		"YQT","YQU","YQX","YQY","YQZ","YRB","YRI","YRJ","YRL","YRR","YRT","YSB",
		"YSF","YSH","YSJ","YSK","YSL","YSM","YSN","YSP","YSR","YTA","YTD","YTE",
		"YTG","YTH","YTQ","YTS","YTZ","YUD","YUL","YUM","YUX","YUY","YVA","YVB",
		"YVC","YVM","YVO","YVP","YVR","YVZ","YWB","YWG","YWH","YWK","YWL","YWR",
		"YWS","YXC","YXD","YXE","YXH","YXJ","YXL","YXP","YXS","YXT","YXU","YXX",
		"YXX","YXY","YXZ","YYB","YYC","YYD","YYE","YYF","YYG","YYH","YYJ","YYQ",
		"YYR","YYT","YYU","YYY","YYZ","YZF","YZG","YZP","YZR","YZT","YZV","ZAD",
		"ZAG","ZAH","ZAL","ZAM","ZAT","ZAZ","ZBF","ZBV","ZCL","ZCO","ZDJ","ZGC",
		"ZGI","ZHA","ZHO","ZIH","ZJJ","ZKE","ZLO","ZNA","ZNE","ZNZ","ZOS","ZQN",
		"ZQW","ZRF","ZRH","ZSA","ZTH","ZUH","ZVK","ZYL"
	};

}