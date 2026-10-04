/***********************************************************************
* Copyright by Michael Loesler, https://software.applied-geodesy.org   *
*                                                                      *
* This program is free software; you can redistribute it and/or modify *
* it under the terms of the GNU General Public License as published by *
* the Free Software Foundation; either version 3 of the License, or    *
* at your option any later version.                                    *
*                                                                      *
* This program is distributed in the hope that it will be useful,      *
* but WITHOUT ANY WARRANTY; without even the implied warranty of       *
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the        *
* GNU General Public License for more details.                         *
*                                                                      *
* You should have received a copy of the GNU General Public License    *
* along with this program; if not, see <http://www.gnu.org/licenses/>  *
* or write to the                                                      *
* Free Software Foundation, Inc.,                                      *
* 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.            *
*                                                                      *
***********************************************************************/

package org.applied_geodesy.instrument.totalstaion.leica;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.applied_geodesy.instrument.totalstaion.leica.data.AUT_CHANGE_FACE;
import org.applied_geodesy.instrument.totalstaion.leica.data.AUT_FINE_ADJUST;
import org.applied_geodesy.instrument.totalstaion.leica.data.AUT_POSTOL;
import org.applied_geodesy.instrument.totalstaion.leica.data.AUT_SEARCH_AREA;
import org.applied_geodesy.instrument.totalstaion.leica.data.AUT_SEARCH_SPIRAL;
import org.applied_geodesy.instrument.totalstaion.leica.data.AUT_TIMEOUT;
import org.applied_geodesy.instrument.totalstaion.leica.data.BAP_PRISM_TYPE_AND_NAME;
import org.applied_geodesy.instrument.totalstaion.leica.data.CAM_2D_COORD_TYPE;
import org.applied_geodesy.instrument.totalstaion.leica.data.CAM_3D_COORD_TYPE;
import org.applied_geodesy.instrument.totalstaion.leica.data.CAM_OVC_EXTER_CALIB_TYPE;
import org.applied_geodesy.instrument.totalstaion.leica.data.CAM_OVC_INTER_CALIB_TYPE;
import org.applied_geodesy.instrument.totalstaion.leica.data.CAM_ROTATION_TYPE;
import org.applied_geodesy.instrument.totalstaion.leica.data.BAP_PRISMDEF;
import org.applied_geodesy.instrument.totalstaion.leica.data.CSV_POWER;
import org.applied_geodesy.instrument.totalstaion.leica.data.CSV_SOFTWARE_VERSION;
import org.applied_geodesy.instrument.totalstaion.leica.data.DATE_TYPE;
import org.applied_geodesy.instrument.totalstaion.leica.data.DATIME;
import org.applied_geodesy.instrument.totalstaion.leica.data.FTR_BLOCK;
import org.applied_geodesy.instrument.totalstaion.leica.data.FTR_BLOCK_LARGE;
import org.applied_geodesy.instrument.totalstaion.leica.data.FTR_DIRINFO;
import org.applied_geodesy.instrument.totalstaion.leica.data.FTR_MODDATE;
import org.applied_geodesy.instrument.totalstaion.leica.data.FTR_MODTIME;
import org.applied_geodesy.instrument.totalstaion.leica.data.IMG_TCC_CONFIG;
import org.applied_geodesy.instrument.totalstaion.leica.data.MOT_COM_PAIR;
import org.applied_geodesy.instrument.totalstaion.leica.data.SUP_POWER_MANAGEMENT_CONFIG;
import org.applied_geodesy.instrument.totalstaion.leica.data.TIME_TYPE;
import org.applied_geodesy.instrument.totalstaion.leica.data.TMC_ANGLE;
import org.applied_geodesy.instrument.totalstaion.leica.data.TMC_ANG_SWITCH;
import org.applied_geodesy.instrument.totalstaion.leica.data.TMC_ATMOS_TEMPERATURE;
import org.applied_geodesy.instrument.totalstaion.leica.data.TMC_COORDINATE;
import org.applied_geodesy.instrument.totalstaion.leica.data.TMC_EDM_SIGNAL;
import org.applied_geodesy.instrument.totalstaion.leica.data.TMC_FULL_MEAS;
import org.applied_geodesy.instrument.totalstaion.leica.data.TMC_HZ_V_ANG_DIST;
import org.applied_geodesy.instrument.totalstaion.leica.data.TMC_INCLINE;
import org.applied_geodesy.instrument.totalstaion.leica.data.TMC_REFRACTION;
import org.applied_geodesy.instrument.totalstaion.leica.data.TMC_SLOPE_DIST_CORR;
import org.applied_geodesy.instrument.totalstaion.leica.data.TMC_STATION;
import org.applied_geodesy.instrument.totalstaion.leica.data.TPS_DEVICE;
import org.applied_geodesy.instrument.totalstaion.leica.es.AUT_ADJ_MODE;
import org.applied_geodesy.instrument.totalstaion.leica.es.AUT_ATR_MODE;
import org.applied_geodesy.instrument.totalstaion.leica.es.AUT_POS_MODE;
import org.applied_geodesy.instrument.totalstaion.leica.es.AUT_ROTATION_DIRECTION;
import org.applied_geodesy.instrument.totalstaion.leica.es.BAP_ATRSETTING;
import org.applied_geodesy.instrument.totalstaion.leica.es.BAP_MEASURE_PRG;
import org.applied_geodesy.instrument.totalstaion.leica.es.BAP_PRISM_TYPE;
import org.applied_geodesy.instrument.totalstaion.leica.es.BAP_REFL_TYPE;
import org.applied_geodesy.instrument.totalstaion.leica.es.BAP_TARGET_TYPE;
import org.applied_geodesy.instrument.totalstaion.leica.es.BAP_USER_MEASPRG;
import org.applied_geodesy.instrument.totalstaion.leica.es.BOOLE;
import org.applied_geodesy.instrument.totalstaion.leica.es.CAM_COMPRESSION_TYPE;
import org.applied_geodesy.instrument.totalstaion.leica.es.CAM_ID_TYPE;
import org.applied_geodesy.instrument.totalstaion.leica.es.CAM_JPEG_COMPR_QUALITY_TYPE;
import org.applied_geodesy.instrument.totalstaion.leica.es.CAM_RESOLUTION_TYPE;
import org.applied_geodesy.instrument.totalstaion.leica.es.CAM_WHITE_BALANCE_TYPE;
import org.applied_geodesy.instrument.totalstaion.leica.es.CAM_ZOOM_FACTOR_TYPE;
import org.applied_geodesy.instrument.totalstaion.leica.es.COM_TPS_STARTUP_MODE;
import org.applied_geodesy.instrument.totalstaion.leica.es.COM_TPS_STOP_MODE;
import org.applied_geodesy.instrument.totalstaion.leica.es.CSV_BATTERY;
import org.applied_geodesy.instrument.totalstaion.leica.es.CSV_POWER_PATH;
import org.applied_geodesy.instrument.totalstaion.leica.es.EDM_EGLINTENSITY_TYPE;
import org.applied_geodesy.instrument.totalstaion.leica.es.EDM_MEASUREMENT_TYPE;
import org.applied_geodesy.instrument.totalstaion.leica.es.EDM_MODE;
import org.applied_geodesy.instrument.totalstaion.leica.es.FTR_DEVICETYPE;
import org.applied_geodesy.instrument.totalstaion.leica.es.FTR_FILETYPE;
import org.applied_geodesy.instrument.totalstaion.leica.es.GRC;
import org.applied_geodesy.instrument.totalstaion.leica.es.IMG_MEM_TYPE;
import org.applied_geodesy.instrument.totalstaion.leica.es.MOT_LOCK_STATUS;
import org.applied_geodesy.instrument.totalstaion.leica.es.MOT_MODE;
import org.applied_geodesy.instrument.totalstaion.leica.es.MOT_STOP_MODE;
import org.applied_geodesy.instrument.totalstaion.leica.es.ON_OFF_TYPE;
import org.applied_geodesy.instrument.totalstaion.leica.es.RPC;
import org.applied_geodesy.instrument.totalstaion.leica.es.SUP_AUTO_POWER;
import org.applied_geodesy.instrument.totalstaion.leica.es.TMC_FACE;
import org.applied_geodesy.instrument.totalstaion.leica.es.TMC_INCLINE_PRG;
import org.applied_geodesy.instrument.totalstaion.leica.es.TMC_MEASURE_PRG;
import org.applied_geodesy.instrument.totalstaion.leica.es.TPS_DEVICE_CLASS;
import org.applied_geodesy.instrument.totalstaion.leica.es.TPS_DEVICE_TYPE;
import org.applied_geodesy.instrument.totalstaion.leica.es.TPS_REFLESS_CLASS;
import org.applied_geodesy.io.rxtx.ReceiveDataType;
import org.applied_geodesy.io.rxtx.ReceiverExchangeable;
import org.applied_geodesy.io.rxtx.RxTx;
import org.applied_geodesy.io.rxtx.RxTxReturnable;

public class JGeoCOM implements RxTxReturnable, ReceiverExchangeable {

	private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

	private boolean isBusy = false, isDone = false;
	private GCDataPacket packet = null;
	private RxTx connRxTx;

	private final static String REQUEST_START     = new String("\n%%R1Q,");
	private final static String REQUEST_END       = new String("\r\n");
	private final static String REQUEST_SEPERATOR = new String(":");
	private final static String REQUEST_FORMAT    = REQUEST_START + "%d" + REQUEST_SEPERATOR + "%s" + REQUEST_END;
	
	private StringBuffer responseMessage = new StringBuffer();
	
	public JGeoCOM(RxTx connRxTx) {
		this.connRxTx = connRxTx;
		this.connRxTx.setReceiveDataType(ReceiveDataType.BYTE_ARRAY);
	}
	
	@Override
	public void receive(byte[] bytesRX) throws IOException {
		this.responseMessage.append(new String(bytesRX));
		
		int beginIndex = this.responseMessage.lastIndexOf("%R1P");
		int endIndex   = this.responseMessage.lastIndexOf("\n");
		
		if (this.packet != null && beginIndex != -1 && endIndex != -1 && beginIndex < endIndex) {
			//System.out.println(this.getClass().getSimpleName()+"  "+packet.getRPC()+"   "+this.responseMessage.substring(this.responseMessage.lastIndexOf("%R1P"), this.responseMessage.lastIndexOf("\n"))+"\n-------------------" );
			this.packet = this.exploidResponse(this.packet, this.responseMessage.substring(beginIndex, endIndex));
			this.responseMessage.setLength(0); // clearing data
			
			this.pcs.firePropertyChange("OnAnswer", this.packet.getRPC(), this.packet.getOnAnswerArgument());
			this.pcs.firePropertyChange("OnCommandAnswer", this.packet.getRPC(), this.packet.getGRC());

			if (packet.getGRC() != GRC.GRC_OK)
				pcs.firePropertyChange("OnErrorAnswer", this.packet.getRPC(), this.packet.getGRC());
							
			synchronized(this.packet){
				this.isDone = true;
				this.packet.notifyAll();
			}
		}
	}

	@Override
	public void receive(int intRX) throws IOException {
		throw new IOException("Error, unsupported method call. Use receive(int intRX) for data transfer.");
	}
	
	private GCDataPacket exploidResponse(GCDataPacket packet, String response) {
		String regexp = new String("^%R1P,(\\d+),(\\d+):(\\d+),?(.+)?");

		Pattern pattern = Pattern.compile(regexp);
		Matcher matcher = pattern.matcher(response);
		
		if (matcher != null && matcher.find() && matcher.groupCount() >= 2) {
			try {
				int comRC    = Integer.parseInt(matcher.group(1));
				//int transID  = Integer.parseInt(matcher.group(2));
				int rpcRC    = Integer.parseInt(matcher.group(3));   	

				GRC rc = GRC.getEnumByValue(Math.max(comRC, rpcRC));
				if (rc == null)
					rc = GRC.GRC_UNDEFINED;
				packet.setGRC(rc);
				
				String reqParamArr[] = new String[0];
				// Prufe, ob eine Nachricht uebermittelt wurde
				if (matcher.groupCount() >= 4 && matcher.group(4) != null) {
					reqParamArr = matcher.group(4).trim().split(",");
				}

				RPC rpc = packet.getRPC();
				switch(rpc) {
				
				case COM_GetDoublePrecision:
					if (reqParamArr.length >= 1) {
						packet.setOnAnswerArgument(
								Integer.valueOf(reqParamArr[0])
						);
					}
					break;
					
				case TMC_GetCoordinate:
					if (reqParamArr.length >= 8) {
						packet.setOnAnswerArgument(
								new TMC_COORDINATE(
										Double.parseDouble(reqParamArr[0]),
										Double.parseDouble(reqParamArr[1]),
										Double.parseDouble(reqParamArr[2]),
										Long.parseLong(reqParamArr[3]),
										Double.parseDouble(reqParamArr[4]),
										Double.parseDouble(reqParamArr[5]),
										Double.parseDouble(reqParamArr[6]),
										Long.parseLong(reqParamArr[7])
								)
						);
					}
					break;

				case TMC_GetAngle1:
					if (reqParamArr.length >= 9) {
						packet.setOnAnswerArgument(
								new TMC_ANGLE(
										Double.parseDouble(reqParamArr[0]),
										Double.parseDouble(reqParamArr[1]),
										Double.parseDouble(reqParamArr[2]),
										Long.parseLong(reqParamArr[3]),
										
										new TMC_INCLINE(
												Double.parseDouble(reqParamArr[4]),
												Double.parseDouble(reqParamArr[5]),
												Double.parseDouble(reqParamArr[6]),
												Long.parseLong(reqParamArr[7])
										),
										
										TMC_FACE.getEnumByValue(Integer.parseInt(reqParamArr[8]))
								)
						);
					}
					break;
					
				case TMC_GetAngle5:
					if (reqParamArr.length >= 2) {
						packet.setOnAnswerArgument(
								new TMC_HZ_V_ANG_DIST(
										Double.parseDouble(reqParamArr[0]),
										Double.parseDouble(reqParamArr[1])
								)
						);
					}
					break;
					
				case TMC_QuickDist:
					if (reqParamArr.length >= 3) {
						packet.setOnAnswerArgument(
								new TMC_HZ_V_ANG_DIST(
										Double.parseDouble(reqParamArr[0]),
										Double.parseDouble(reqParamArr[1]),
										Double.parseDouble(reqParamArr[2])
								)
						);
					}
					break;
					
				case TMC_GetFullMeas:
					if (reqParamArr.length >= 8) {
						packet.setOnAnswerArgument(
								new TMC_FULL_MEAS(
										Double.parseDouble(reqParamArr[0]),
										Double.parseDouble(reqParamArr[1]),
										Double.parseDouble(reqParamArr[2]),
										Double.parseDouble(reqParamArr[3]),
										Double.parseDouble(reqParamArr[4]),
										Double.parseDouble(reqParamArr[5]),
										Double.parseDouble(reqParamArr[6]),
										Double.parseDouble(reqParamArr[7])
								)
						);
					}
					break;
					
				case TMC_GetHeight:
					if (reqParamArr.length >= 1) {
						packet.setOnAnswerArgument(
								Double.valueOf(reqParamArr[0])
						);
					}
					break;
					
				case TMC_GetAtmCorr:
					if (reqParamArr.length >= 4) {
						packet.setOnAnswerArgument(
								new TMC_ATMOS_TEMPERATURE(
										Double.parseDouble(reqParamArr[0]),
										Double.parseDouble(reqParamArr[1]),
										Double.parseDouble(reqParamArr[2]),
										Double.parseDouble(reqParamArr[3])
								)
						);
					}
					break;
					
				case TMC_CalcATMCorr:
					if (reqParamArr.length >= 1) {
						packet.setOnAnswerArgument(
								Double.valueOf(reqParamArr[0])
						);
					}
					break;
					
				case TMC_GetPrismCorr:
					if (reqParamArr.length >= 1) {
						packet.setOnAnswerArgument(
								Double.valueOf(reqParamArr[0])
						);
					}
					break;
					
				case TMC_GetRefractiveCorr:
					if (reqParamArr.length >= 3) {
						packet.setOnAnswerArgument(
								new TMC_REFRACTION(
										ON_OFF_TYPE.getEnumByValue(Integer.parseInt(reqParamArr[0])),
										Double.parseDouble(reqParamArr[1]),
										Double.parseDouble(reqParamArr[2])
								)
						);
					}
					break;
					
				case TMC_GetRefractiveMethod:
					if (reqParamArr.length >= 1) {
						packet.setOnAnswerArgument(
								Integer.valueOf(Integer.parseInt(reqParamArr[0]) != 2 ? 1 : 2)
						);
					}
					break;
					
				case TMC_GetStation:
					if (reqParamArr.length >= 4) {
						packet.setOnAnswerArgument(
								new TMC_STATION(
										Double.parseDouble(reqParamArr[0]),
										Double.parseDouble(reqParamArr[1]),
										Double.parseDouble(reqParamArr[2]),
										Double.parseDouble(reqParamArr[3])
								)
						);
					}
					break;
					
				case TMC_GetSimpleMea:
					if (reqParamArr.length >= 3) {
						packet.setOnAnswerArgument(
								new TMC_HZ_V_ANG_DIST(
										Double.parseDouble(reqParamArr[0]),
										Double.parseDouble(reqParamArr[1]),
										Double.parseDouble(reqParamArr[2])
										)
								);
					}
					break;
					
				case TMC_GetAtmPpm:
					if (reqParamArr.length >= 1) {
						packet.setOnAnswerArgument(
								Double.valueOf(reqParamArr[0])
						);
					}
					break;
					
				case TMC_GetGeoPpm:
					if (reqParamArr.length >= 1) {
						packet.setOnAnswerArgument(
								Double.valueOf(reqParamArr[0])
						);
					}
					break;
					
				case TMC_GetFace:
					if (reqParamArr.length >= 1) {
						packet.setOnAnswerArgument(
								TMC_FACE.getEnumByValue(Integer.parseInt(reqParamArr[0]))
						);
					}
					break;
					
				case TMC_GetSignal:
					if (reqParamArr.length >= 2) {
						packet.setOnAnswerArgument(
								new TMC_EDM_SIGNAL(
										Double.parseDouble(reqParamArr[0]),
										Long.parseLong(reqParamArr[1])
								)
						);
					}
					break;
					
				case TMC_GetAngSwitch:
					if (reqParamArr.length >= 4) {
						packet.setOnAnswerArgument(
								new TMC_ANG_SWITCH(
										ON_OFF_TYPE.getEnumByValue(Integer.parseInt(reqParamArr[0])),
										ON_OFF_TYPE.getEnumByValue(Integer.parseInt(reqParamArr[1])),
										ON_OFF_TYPE.getEnumByValue(Integer.parseInt(reqParamArr[2])),
										ON_OFF_TYPE.getEnumByValue(Integer.parseInt(reqParamArr[3]))
								)
						);
					}
					break;
					
				case TMC_GetInclineSwitch:
					if (reqParamArr.length >= 1) {
						packet.setOnAnswerArgument(
								ON_OFF_TYPE.getEnumByValue(Integer.parseInt(reqParamArr[0]))
						);
					}
					break;
					
				case TMC_GetEdmMode:
					if (reqParamArr.length >= 1) {
						packet.setOnAnswerArgument(
								EDM_MODE.getEnumByValue(Integer.parseInt(reqParamArr[0]))
						);
					}
					break;
					
				case TMC_GetSimpleCoord:
					if (reqParamArr.length >= 3) {
						packet.setOnAnswerArgument(
								new TMC_COORDINATE(
										Double.parseDouble(reqParamArr[0]),
										Double.parseDouble(reqParamArr[1]),
										Double.parseDouble(reqParamArr[2])
								)
						);
					}
					break;
					
				case TMC_IfDataAzeCorrError:
					if (reqParamArr.length >= 1) {
						packet.setOnAnswerArgument(
								BOOLE.getEnumByValue(Integer.parseInt(reqParamArr[0]))
						);
					}
					break;
					
				case TMC_IfDataIncCorrError:
					if (reqParamArr.length >= 1) {
						packet.setOnAnswerArgument(
								BOOLE.getEnumByValue(Integer.parseInt(reqParamArr[0]))
						);
					}
					break;
					
				case TMC_GetSlopeDistCorr:
					if (reqParamArr.length >= 2) {
						packet.setOnAnswerArgument(
								new TMC_SLOPE_DIST_CORR (
										Double.parseDouble(reqParamArr[0]),
										Double.parseDouble(reqParamArr[1])
								)
						);
					}
					break;
					
				case AUS_GetUserAtrState:
					if (reqParamArr.length >= 1) {
						packet.setOnAnswerArgument(
								ON_OFF_TYPE.getEnumByValue(Integer.parseInt(reqParamArr[0]))
						);
					}
					break;
					
				case AUS_GetUserLockState:
					if (reqParamArr.length >= 1) {
						packet.setOnAnswerArgument(
								ON_OFF_TYPE.getEnumByValue(Integer.parseInt(reqParamArr[0]))
						);
					}
					break;
					
				case AUT_ReadTol:
					if (reqParamArr.length >= 2) {
						packet.setOnAnswerArgument(
								new AUT_POSTOL(
										Double.parseDouble(reqParamArr[0]),
										Double.parseDouble(reqParamArr[1])	
								)
						);
					}
					break;
				
				case AUT_ReadTimeout:
					if (reqParamArr.length >= 2) {
						packet.setOnAnswerArgument(
								new AUT_TIMEOUT (
										Double.parseDouble(reqParamArr[0]),
										Double.parseDouble(reqParamArr[1])
								)
						);
					}
					break;
					
				case AUT_GetFineAdjustMode:
					if (reqParamArr.length >= 1) {
						packet.setOnAnswerArgument(
								AUT_ADJ_MODE.getEnumByValue(Integer.parseInt(reqParamArr[0]))
						);
					}
					break;
					
				case AUT_GetSearchArea:
					if (reqParamArr.length >= 5) {
						packet.setOnAnswerArgument(
								new AUT_SEARCH_AREA(
										Double.parseDouble(reqParamArr[0]),
										Double.parseDouble(reqParamArr[1]),
										Double.parseDouble(reqParamArr[2]),
										Double.parseDouble(reqParamArr[3]),
										BOOLE.getEnumByValue(Integer.parseInt(reqParamArr[4]))
								)
						);
					}
					break;
					
				case AUT_GetUserSpiral:
					if (reqParamArr.length >= 1) {
						packet.setOnAnswerArgument(
								new AUT_SEARCH_SPIRAL(
										Double.parseDouble(reqParamArr[0]),
										Double.parseDouble(reqParamArr[1])
								)
						);
					}
					break;
					
				case EDM_GetEglIntensity:
					if (reqParamArr.length >= 1) {
						packet.setOnAnswerArgument(
								EDM_EGLINTENSITY_TYPE.getEnumByValue(Integer.parseInt(reqParamArr[0]))
						);
					}
					break;
					
				case EDM_IsContMeasActive:
					if (reqParamArr.length >= 1) {
						packet.setOnAnswerArgument(
								BOOLE.getEnumByValue(Integer.parseInt(reqParamArr[0]))
						);
					}
					break;
					
				case MOT_ReadLockStatus:
					if (reqParamArr.length >= 1) {
						packet.setOnAnswerArgument(
								MOT_LOCK_STATUS.getEnumByValue(Integer.parseInt(reqParamArr[0]))
						);
					}
					break;
					
				case CSV_GetInstrumentNo:
					if (reqParamArr.length >= 1) {
						packet.setOnAnswerArgument(
								Long.parseLong(reqParamArr[0])
						);
					}
					break;
					
				case CSV_GetInstrumentName:
					if (reqParamArr.length >= 1) {
						packet.setOnAnswerArgument(
								(reqParamArr[0].replaceAll("^\"(.+)\"$", "$1")).replaceAll("\\\\", "")
						);
					}
					break;
					
				case CSV_GetDeviceConfig:
					if (reqParamArr.length >= 2) {
						packet.setOnAnswerArgument(
								new TPS_DEVICE(
										TPS_DEVICE_CLASS.getEnumByValue( Integer.parseInt(reqParamArr[0]) ),
										TPS_DEVICE_TYPE.getEnumByValue( Integer.parseInt(reqParamArr[1]) )
										)
								);
					}
					break;
					
				case CSV_GetReflectorlessClass:
					if (reqParamArr.length >= 1) {
						packet.setOnAnswerArgument(
								TPS_REFLESS_CLASS.getEnumByValue(Integer.parseInt(reqParamArr[0]))
						);
					}
					break;
					
				case CSV_GetDateTime:
					if (reqParamArr.length >= 6) {
						packet.setOnAnswerArgument(
								new DATIME(
										new DATE_TYPE(
												Integer.parseInt(reqParamArr[0].replaceAll("'", "")),
												Integer.parseInt(reqParamArr[1].replaceAll("'", ""), 16),
												Integer.parseInt(reqParamArr[2].replaceAll("'", ""), 16)
										),
										new TIME_TYPE(
												Integer.parseInt(reqParamArr[3].replaceAll("'", ""), 16),
												Integer.parseInt(reqParamArr[4].replaceAll("'", ""), 16),
												Integer.parseInt(reqParamArr[5].replaceAll("'", ""), 16)
										)
								)
						);
					}
					break;
					
				case CSV_GetSWVersion:
					if (reqParamArr.length >= 3) {
						packet.setOnAnswerArgument(
								new CSV_SOFTWARE_VERSION(
										Integer.parseInt(reqParamArr[0]),
										Integer.parseInt(reqParamArr[1]),
										Integer.parseInt(reqParamArr[2])
								)
						);
					}
					break;
					
				case CSV_CheckPower:
					if (reqParamArr.length >= 3) {
						packet.setOnAnswerArgument(
								new CSV_POWER(
										Integer.parseInt(reqParamArr[0]),
										CSV_POWER_PATH.getEnumByValue(Integer.parseInt(reqParamArr[1])),
										CSV_POWER_PATH.getEnumByValue(Integer.parseInt(reqParamArr[2]))
								)
						);
					}
					break;
					
				case CSV_GetIntTemp:
					if (reqParamArr.length >= 1) {
						packet.setOnAnswerArgument(
								Double.valueOf(reqParamArr[0])
						);
					}
					break;
					
				case CSV_GetDateTimeCentiSec:
					// %R1P,0,0:RC,Year,Month,Day,Hour,Minute,Second,CentiSecond[all short]
					if (reqParamArr.length >= 7) {
						packet.setOnAnswerArgument(
								new DATIME(
										new DATE_TYPE(
												Integer.parseInt(reqParamArr[0]),
												Integer.parseInt(reqParamArr[1]),
												Integer.parseInt(reqParamArr[2])
										),
										new TIME_TYPE(
												Integer.parseInt(reqParamArr[3]),
												Integer.parseInt(reqParamArr[4]),
												Integer.parseInt(reqParamArr[5]),
												Integer.parseInt(reqParamArr[6])
										)
								)
						);
					}
					break;
					
				case CSV_GetCharging:
					if (reqParamArr.length >= 1) {
						packet.setOnAnswerArgument(
								ON_OFF_TYPE.getEnumByValue(Integer.parseInt(reqParamArr[0]))
						);
					}
					break;
					
				case CSV_GetLaserlotIntens:
					if (reqParamArr.length >= 1) {
						packet.setOnAnswerArgument(
								Integer.parseInt(reqParamArr[0])
						);
					}
					break;
					
				case CSV_GetLaserlotStatus:
					if (reqParamArr.length >= 1) {
						packet.setOnAnswerArgument(
								ON_OFF_TYPE.getEnumByValue(Integer.parseInt(reqParamArr[0]))
						);
					}
					break;
					
				case CSV_GetPreferredPowerSource:
					if (reqParamArr.length >= 1) {
						packet.setOnAnswerArgument(
								CSV_BATTERY.getEnumByValue(Integer.parseInt(reqParamArr[0]))
						);
					}
					break;
					
				case CSV_GetStartUpMessageMode:
					if (reqParamArr.length >= 1) {
						packet.setOnAnswerArgument(
								ON_OFF_TYPE.getEnumByValue(Integer.parseInt(reqParamArr[0]))
						);
					}
					break;
					
				case CSV_GetVoltage:
					if (reqParamArr.length >= 1) {
						packet.setOnAnswerArgument(
								Integer.parseInt(reqParamArr[0])
						);
					}
					break;
					
				case SUP_GetConfig:
					if (reqParamArr.length >= 3) {
						packet.setOnAnswerArgument(
								new SUP_POWER_MANAGEMENT_CONFIG(
										ON_OFF_TYPE.getEnumByValue(Integer.parseInt(reqParamArr[0])),
										SUP_AUTO_POWER.getEnumByValue(Integer.parseInt(reqParamArr[1])),
										Long.parseLong(reqParamArr[2])
								)
						);
					}
					break;
					
				case BAP_GetTargetType:
					if (reqParamArr.length >= 1) {
						packet.setOnAnswerArgument(
								BAP_TARGET_TYPE.getEnumByValue(Integer.parseInt(reqParamArr[0]))
						);
					}
					break;
					
				case BAP_GetPrismType:
					if (reqParamArr.length >= 1) {
						packet.setOnAnswerArgument(
								BAP_PRISM_TYPE.getEnumByValue(Integer.parseInt(reqParamArr[0]))
						);
					}
					break;
					
				case BAP_GetPrismType2:
					if (reqParamArr.length >= 2) {
						packet.setOnAnswerArgument(
								new BAP_PRISM_TYPE_AND_NAME(
										BAP_PRISM_TYPE.getEnumByValue(Integer.parseInt(reqParamArr[0])),
										reqParamArr[1].replaceAll("^\"(.+)\"$", "$1")
								)
						);
					}
					break;
					
				case BAP_GetPrismDef:	
					if (reqParamArr.length >= 3) {
						packet.setOnAnswerArgument(
								new BAP_PRISMDEF(
										reqParamArr[0].replaceAll("^\"(.+)\"$", "$1"),
										Double.parseDouble(reqParamArr[1]),
										BAP_REFL_TYPE.getEnumByValue(Integer.parseInt(reqParamArr[2]))
								)
						);
					}
					break;

				case BAP_GetUserPrismDef:
					if (reqParamArr.length >= 4) {
						packet.setOnAnswerArgument(
								new BAP_PRISMDEF(
										reqParamArr[0].replaceAll("^\"(.+)\"$", "$1"),
										Double.parseDouble(reqParamArr[1]),
										BAP_REFL_TYPE.getEnumByValue(Integer.parseInt(reqParamArr[2])),
										reqParamArr[3].replaceAll("^\"(.+)\"$", "$1")
								)
						);
					}
					break;
					
				case BAP_GetMeasPrg:
					if (reqParamArr.length >= 1) {
						packet.setOnAnswerArgument(
								BAP_USER_MEASPRG.getEnumByValue(Integer.parseInt(reqParamArr[0]))
						);
					}
					break;
					
				case BAP_MeasDistanceAngle:
					if (reqParamArr.length >= 3) {
						packet.setOnAnswerArgument(
								new TMC_HZ_V_ANG_DIST(
										Double.parseDouble(reqParamArr[0]),
										Double.parseDouble(reqParamArr[1]),
										Double.parseDouble(reqParamArr[2])
								)
						);
					}
					break;
				
				case BAP_GetATRSetting:
					if (reqParamArr.length >= 1) {
						packet.setOnAnswerArgument(
								BAP_ATRSETTING.getEnumByValue(Integer.parseInt(reqParamArr[0]))
						);
					}
					break;
				
				case BAP_GetRedATRFov:
					if (reqParamArr.length >= 1) {
						packet.setOnAnswerArgument(
								ON_OFF_TYPE.getEnumByValue(Integer.parseInt(reqParamArr[0]))
						);
					}
					break;
					
				case BAP_GetATRPrecise:
					if (reqParamArr.length >= 1) {
						packet.setOnAnswerArgument(
								ON_OFF_TYPE.getEnumByValue(Integer.parseInt(reqParamArr[0]))
						);
					}
					break;
					
				case CAM_GetZoomFactor:
					if (reqParamArr.length >= 1) {
						packet.setOnAnswerArgument(
								CAM_ZOOM_FACTOR_TYPE.getEnumByValue(Integer.parseInt(reqParamArr[0]))
						);
					}
					break;
					
				case CAM_GetCamPos:
					if (reqParamArr.length >= 3) {
						packet.setOnAnswerArgument(
								new CAM_3D_COORD_TYPE(
										Double.parseDouble(reqParamArr[0]),
										Double.parseDouble(reqParamArr[1]),
										Double.parseDouble(reqParamArr[2])
								)
						);
					}
					break;
					
				case CAM_GetCamViewingDir:
					if (reqParamArr.length >= 3) {
						packet.setOnAnswerArgument(
								new CAM_3D_COORD_TYPE(
										Double.parseDouble(reqParamArr[0]),
										Double.parseDouble(reqParamArr[1]),
										Double.parseDouble(reqParamArr[2])
								)
						);
					}
					break;
					
				case CAM_GetCameraFoV:
					if (reqParamArr.length >= 2) {
						packet.setOnAnswerArgument(
								new CAM_2D_COORD_TYPE(
										Double.parseDouble(reqParamArr[0]),
										Double.parseDouble(reqParamArr[1])
								)
						);
					}
					break;
					
				case CAM_OVC_GetActCameraCentre:
					if (reqParamArr.length >= 2) {
						packet.setOnAnswerArgument(
								new CAM_2D_COORD_TYPE(
										Double.parseDouble(reqParamArr[0]),
										Double.parseDouble(reqParamArr[1])
								)
						);
					}
					break;
					
				case CAM_GetCameraPowerSwitch:
					if (reqParamArr.length >= 1) {
						packet.setOnAnswerArgument(
								ON_OFF_TYPE.getEnumByValue(Integer.parseInt(reqParamArr[0]))
						);
					}
					break;
					
				case CAM_AF_GetMotorPosition:
					if (reqParamArr.length >= 1) {
						packet.setOnAnswerArgument(
								Long.valueOf(reqParamArr[0])
						);
					}
					break;
					
				case CAM_AF_GetChipWindowSize:
					if (reqParamArr.length >= 2) {
						packet.setOnAnswerArgument(
								new CAM_2D_COORD_TYPE(
										Double.parseDouble(reqParamArr[0]),
										Double.parseDouble(reqParamArr[1])
								)
						);
					}
					break;
					
				case CAM_OAC_GetCrossHairPos:
					if (reqParamArr.length >= 2) {
						packet.setOnAnswerArgument(
								new CAM_2D_COORD_TYPE(
										Double.parseDouble(reqParamArr[0]),
										Double.parseDouble(reqParamArr[1])
								)
						);
					}
					break;
					
				case CAM_OVC_ReadInterOrient:
					if (reqParamArr.length >= 4) {
						packet.setOnAnswerArgument(
								new CAM_OVC_INTER_CALIB_TYPE (
										new CAM_2D_COORD_TYPE(
												Double.parseDouble(reqParamArr[0]),
												Double.parseDouble(reqParamArr[1])
										),
										Double.parseDouble(reqParamArr[2]),
										Double.parseDouble(reqParamArr[3])
								)
						);
					}
					break;
					
				case CAM_OVC_ReadExterOrient:
					if (reqParamArr.length >= 6) {
						packet.setOnAnswerArgument(
								new CAM_OVC_EXTER_CALIB_TYPE (
										new CAM_3D_COORD_TYPE(
												Double.parseDouble(reqParamArr[0]),
												Double.parseDouble(reqParamArr[1]),
												Double.parseDouble(reqParamArr[2])
										),
										new CAM_ROTATION_TYPE(
												Double.parseDouble(reqParamArr[3]),
												Double.parseDouble(reqParamArr[4]),
												Double.parseDouble(reqParamArr[5])
										)
								)
						);
					}
					break;
					
				case IMG_GetTccConfig:
					if (reqParamArr.length >= 4) {
						packet.setOnAnswerArgument(
								new IMG_TCC_CONFIG ( 
										Long.parseLong(reqParamArr[0]),
										Long.parseLong(reqParamArr[1]),
										Long.parseLong(reqParamArr[2]),
										new String(reqParamArr[3])
								)
						);
					}
					break;
					
				case IMG_TakeTccImage:
					if (reqParamArr.length >= 1) {
						packet.setOnAnswerArgument(
								Integer.parseInt(reqParamArr[0])
						);
					}
					break;
					
				case KDM_GetLcdPower:
					if (reqParamArr.length >= 1) {
						packet.setOnAnswerArgument(
								BOOLE.getEnumByValue(Integer.parseInt(reqParamArr[0]))
						);
					}
					break;
					
				case FTR_List:
					if (reqParamArr.length >= 10) {
						packet.setOnAnswerArgument(
								new FTR_DIRINFO(
										BOOLE.getEnumByValue(Integer.parseInt(reqParamArr[0])),
										reqParamArr[1],
										Long.parseLong(reqParamArr[2]),
										new FTR_MODTIME(
												Integer.parseInt(reqParamArr[3]),
												Integer.parseInt(reqParamArr[4]),
												Integer.parseInt(reqParamArr[5]),
												Integer.parseInt(reqParamArr[6])
										),
										new FTR_MODDATE(
												Integer.parseInt(reqParamArr[7]),
												Integer.parseInt(reqParamArr[8]),
												Integer.parseInt(reqParamArr[9])
										)
								)
						);
					}
					break;
					
				case TR_SetupDownload:
				case FTR_SetupDownloadLarge:
					if (reqParamArr.length >= 1) {
						packet.setOnAnswerArgument(
								Integer.parseInt(reqParamArr[0])
						);
					}
					break;
				
				case FTR_Download:
					if (reqParamArr.length >= 1) {
						//byte data[] = (reqParamArr[0]).getBytes();
						byte[] data = hexStringToByteArray(reqParamArr[0]);
						packet.setOnAnswerArgument(
								new FTR_BLOCK(
										data,
										data.length
								)
						);
					}
					break;

				case FTR_DownloadXL:
					if (reqParamArr.length >= 1) {
						//byte data[] = (reqParamArr[0]).getBytes();
						byte[] data = hexStringToByteArray(reqParamArr[0]);
						
						packet.setOnAnswerArgument(
								new FTR_BLOCK_LARGE(
										data,
										data.length
								)
						);
					}
					break;
				case FTR_Delete:
				case FTR_DeleteDir:
					if (reqParamArr.length >= 1) {
						packet.setOnAnswerArgument(
								Integer.parseInt(reqParamArr[0])
						);
					}
					break;
					
				default:
					// No Parameters
					break;
					
				} // switch
			} 
			catch(Exception e) {
				e.printStackTrace();
				System.out.println(packet);
				System.err.println(this.getClass().getSimpleName() + " Fehler beim Decodieren der Nachricht "+response+"! "+e);
			}
		}
		else 
			System.err.println(this.getClass().getSimpleName() + " Fehler beim Decodieren der Nachricht "+response+"!");

		return packet;
	}


	private boolean send(GCDataPacket packet) {
		RPC rpc      = packet.getRPC();
		String param = packet.getArgument(); 
		// %R1Q,2108:WaitTime[long],Mode[long]
		String request = String.format(Locale.ENGLISH, REQUEST_FORMAT, rpc.getValue(), param == null ? "" : param);

		try {
			this.connRxTx.transmit( request.getBytes() );
		} 
		catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public GRC doCommand(GCDataPacket packet) {
		try {
			if (this.isBusy) {
				packet.setGRC(GRC.GRC_SYSBUSY);
				this.pcs.firePropertyChange("OnErrorAnswer", packet.getRPC(), packet.getGRC());
				return GRC.GRC_SYSBUSY;
			}

			this.isDone = false;
			this.isBusy = true;
			this.packet = packet;

			if (!this.send(packet)) 
				return GRC.GRC_COM_CANT_SEND;

			synchronized( this.packet ) {
				try {
					long timeOut = this.packet.getTimeout();
					if (!this.isDone) {
						if (timeOut > 0)
							this.packet.wait(timeOut);
						else
							this.packet.wait();	
					}
					// notify called or timeout reached
					if (!this.isDone) {
						this.packet.setGRC(GRC.GRC_TIME_OUT);
						this.pcs.firePropertyChange("OnErrorAnswer", packet.getRPC(), packet.getGRC());
					}
					this.isDone = true;
					this.packet = null;
				}
				catch ( Exception e ) {
					if (this.packet != null) {
						this.packet.setGRC(GRC.GRC_TIME_OUT);
						this.pcs.firePropertyChange("OnErrorAnswer", packet.getRPC(), packet.getGRC());
					}
					return GRC.GRC_TIME_OUT;
				}
			}
			return packet.getGRC();

		} finally {
			this.packet = null;
			this.isBusy = false;
			this.isDone = true;
		}
	}
	
	/**************************************************************************************************/
	
	/************************************/
	/*                                  */
	/*    COMMUNICATION SETTINGS COM    */
	/*                                  */
	/************************************/

	/**
	 * precision of the server side will be returned.
	 * Number of digits to the right of the decimal point.
	 * @return %R1P,0,0:RC,nDigits[short]
	 *
	 */
	public GCDataPacket COM_GetDoublePrecision() {
		GCDataPacket packet = new GCDataPacket(
				RPC.COM_GetDoublePrecision
		);

		this.doCommand(packet);
		return packet;
	}

	/**
	 * sets the precision - number of digits to the right of the decimal - when double floating-point values
	 * are transmitted. The TPS' system software always calculates with highest possible precision. The default
	 * precision is fifteen digits. However, if this precision is not needed then transmission of double data (ASCII
	 * transmission) can be speeded up by choosing a lower precision. Especially when many double values are
	 * transmitted this may enhance the operational speed.
	 * <em>Notice that trailing Zeros will not be sent by the server and values may be rounded. E.g. if
	 * precision is set to 3 and the exact value is 1.99975 the resulting value will be 2.0</em>
	 *
	 * @param nDigits  0 > nDigits < 15
	 * @return %R1P,0,0:RC
	 *
	 */
	public GCDataPacket COM_SetDoublePrecision(int nDigits) {
		GCDataPacket packet = new GCDataPacket(
				RPC.COM_SetDoublePrecision,
				String.valueOf(nDigits)
		);
		
		if (nDigits < 0 || nDigits > 15)
			packet.setGRC(GRC.GRC_IVPARAM);
		else
			this.doCommand(packet);
		return packet;
	}
	
	/**
	 * This function switches on the TPS1200 instrument.
	 *
	 * @param COM_TPS_STARTUP_MODE
	 * @return %R1P,0,0:RC
	 *
	 */
	public GCDataPacket COM_SwitchOnTPS(COM_TPS_STARTUP_MODE eOnMode) {
		GCDataPacket packet = new GCDataPacket(
				RPC.COM_SwitchOnTPS,
				String.valueOf(eOnMode.getValue())
		);
		this.doCommand(packet);
		return packet;
	}

	/**
	 * This function switches off the TPS1200 instrument or put it into sleep mode.
	 *
	 * @param COM_TPS_STOP_MODE
	 * @return %R1P,0,0:RC
	 *
	 */
	public GCDataPacket COM_SwitchOffTPS(COM_TPS_STOP_MODE eOffMode) {
		GCDataPacket packet = new GCDataPacket(
				RPC.COM_SwitchOffTPS,
				String.valueOf(eOffMode.getValue())
		);
		this.doCommand(packet);
		return packet;
	}
	
	/****************************************************/
	/*                                                  */
	/*                  Camera - CAM                    */
	/*                                                  */
	/****************************************************/

	/********************************** CAMERA FUNCTIONS *************************************/

	/**
	 * This command sets the Zoom factor for the camera.
	 * @param CamID CAM_ID_TYPE
	 * @param ZoomFactor CAM_ZOOM_FACTOR_TYPE
	 * @return %R1P,0,0:RC
	 */
	public GCDataPacket CAM_SetZoomFactor (CAM_ID_TYPE CamID, CAM_ZOOM_FACTOR_TYPE ZoomFactor) {
		GCDataPacket packet = new GCDataPacket(
				RPC.CAM_SetZoomFactor,
				CamID.getValue() + "," + ZoomFactor.getValue()
		);
		this.doCommand(packet);
		return packet;
	}
	
	/**
	 * This command gets the current Zoom factor of the camera.
	 * @param CamID Camera ID
	 * @return %R1P,0,0:RC,rZoomFactor
	 */
	public GCDataPacket CAM_GetZoomFactor (CAM_ID_TYPE CamID) {
		GCDataPacket packet = new GCDataPacket(
				RPC.CAM_GetZoomFactor,
				String.valueOf(CamID.getValue())
		);
		this.doCommand(packet);
		return packet;
	}
	
	/**
	 * This command reads the position of the OVC with respect to station coordinates 
	 * (in Cartesian coordinate system). The station coordinates can be read with 
	 * function TMC_GetStation.
	 * If the instrument is turned to Hz angle 0 deg and V angle 90 deg the function CAM_GetCamPos would return the
	 * typical camera shift values x = Easting = 0.016 m, y = Northing = 0.061 m and z = Height = 0.056 m.
	 * 
	 * @param CamID Camera ID
	 * @return %R1P,0,0:RC,dX,dY,dZ
	 */
	public GCDataPacket CAM_GetCamPos (CAM_ID_TYPE CamID) {
		GCDataPacket packet = new GCDataPacket(
				RPC.CAM_GetCamPos,
				String.valueOf(CamID.getValue())
		);
		this.doCommand(packet);
		return packet;
	}
	
	/**
	 * This command calculates the viewing direction of the OVC with respect to its coordinates. The provided
	 * direction is a 3-D vector pointing in the direction of the optical axis of the OVC at the given slope distance. 
	 * The camera shift (see CAM_GetCamPos) is automatically considered. The slope distance can be read with function
	 * TMC_GetSimpleMea.
	 * 
	 * @param CamID Camera ID
	 * @param dSlopeDistance Slope distance in [m]
	 * @return %R1P,0,0:RC,dCamDirE,dCamDirN,dCamDirH
	 */
	public GCDataPacket CAM_GetCamViewingDir (CAM_ID_TYPE CamID, double dSlopeDistance) {
		GCDataPacket packet = new GCDataPacket(
				RPC.CAM_GetCamViewingDir,
				String.valueOf(CamID.getValue()) + "," + String.valueOf(dSlopeDistance)
		);
		this.doCommand(packet);
		return packet;
	}
	
	/**
	 * This command calculates the field of view of the OVC for different zoom factors.
	 * @param CamID  Camera ID
	 * @param eZoomFactor Current zoom factor
	 * @return %R1P,0,0:RC,rFoVHz,rFoVV
	 */
	public GCDataPacket CAM_GetCameraFoV (CAM_ID_TYPE CamID, CAM_ZOOM_FACTOR_TYPE eZoomFactor) {
		GCDataPacket packet = new GCDataPacket(
				RPC.CAM_GetCameraFoV,
				String.valueOf(CamID.getValue()) + "," + String.valueOf(eZoomFactor.getValue())
		);
		this.doCommand(packet);
		return packet;
	}
	
	/**
	 * This command sets the image name and number for the next image that is saved in the 
	 * following format: szNamelNumber
	 * @param CamID   Camera ID
	 * @param szName  image name
	 * @param lNumber image number
	 * @return %R1P,0,0:RC
	 */
	public GCDataPacket CAM_SetActualImageName (CAM_ID_TYPE CamID, String szName, long lNumber) {
		GCDataPacket packet = new GCDataPacket(
				RPC.CAM_SetActualImageName,
				String.valueOf(CamID.getValue()) + ",\"" + szName + "\"" + String.valueOf(lNumber)
		);
		this.doCommand(packet);
		return packet;
	}
	
	/**
	 * This command captures an image and saves it with the name defined by CAM_SetActualImageName.
	 * @param CamID Camera ID
	 * @return %R1P,0,0:RC
	 */
	public GCDataPacket CAM_TakeImage (CAM_ID_TYPE CamID) {
		GCDataPacket packet = new GCDataPacket(
				RPC.CAM_TakeImage,
				String.valueOf(CamID.getValue())
		);
		this.doCommand(packet);
		return packet;
	}
	
	/**
	 * This command calculates the position of the crosshairs of the optical sighting axis 
	 * in OVC image at the distance defined by CAM_OVC_SetActDistance and the pixel resolution 
	 * defined by CAM_SetCameraProperties.
	 * @return %R1P,0,0:RC,rdXCentre,rdYCentre
	 */
	public GCDataPacket CAM_OVC_GetActCameraCentre () {
		GCDataPacket packet = new GCDataPacket(
				RPC.CAM_OVC_GetActCameraCentre
		);
		this.doCommand(packet);
		return packet;
	}
	
	/**
	 * This command sets the distance to the current target. This has effect on the current 
	 * camera centre (see CAM_OVC_GetActCameraCentre).
	 * @param dDist slope distance to the current target in m
	 * @param bFace1 theodolite face
	 * @return %R1P,0,0:RC
	 */
	public GCDataPacket CAM_OVC_SetActDistance (double dDist, BOOLE bFace1) {
		GCDataPacket packet = new GCDataPacket(
				RPC.CAM_OVC_SetActDistance,
				String.valueOf(dDist) + "," + String.valueOf(bFace1.getValue())
		);
		this.doCommand(packet);
		return packet;
	}
	
	/**
	 * This command sets the white balance mode of the camera.
	 * @param CamID Camera ID
	 * @param eWhiteBalanceMode White balance mode (Auto = 0, indoor = 1, outdoor = 2)
	 * @return %R1P,0,0:RC
	 */
	public GCDataPacket CAM_SetWhiteBalanceMode (CAM_ID_TYPE CamID, CAM_WHITE_BALANCE_TYPE eWhiteBalanceMode) {
		GCDataPacket packet = new GCDataPacket(
				RPC.CAM_SetWhiteBalanceMode,
				String.valueOf(CamID.getValue()) + "," + String.valueOf(eWhiteBalanceMode.getValue())
		);
		this.doCommand(packet);
		return packet;
	}
	
	/**
	 * This command returns if the camera is ready for use.
	 * @param CamID Camera ID
	 * @return %R1P,0,0:RC
	 */
	public GCDataPacket CAM_IsCameraReady (CAM_ID_TYPE CamID) {
		GCDataPacket packet = new GCDataPacket(
				RPC.CAM_IsCameraReady,
				String.valueOf(CamID.getValue())
		);
		this.doCommand(packet);
		return packet;
	}
	
	/**
	 * This command sets the image resolution and compression for the next image that is captured.
	 * @param CamID Camera ID
	 * @param CamResolution    Resolution
	 * @param CamCompression   Compression
	 * @param JpegComprQuality JPEG-Quality
	 * @return %R1P,0,0:RC
	 */
	public GCDataPacket CAM_SetCameraProperties (CAM_ID_TYPE CamID, CAM_RESOLUTION_TYPE CamResolution, CAM_COMPRESSION_TYPE CamCompression, CAM_JPEG_COMPR_QUALITY_TYPE JpegComprQuality) {
		GCDataPacket packet = new GCDataPacket(
				RPC.CAM_SetCameraProperties,
				String.valueOf(CamID.getValue()) + "," + String.valueOf(CamResolution.getValue()) +
				"," + String.valueOf(CamCompression.getValue()) + "," + String.valueOf(JpegComprQuality.getValue())
		);
		this.doCommand(packet);
		return packet;
	}
	
	/**
	 * This command requests the camera power switch state (on/off).
	 * @param CamID Camera ID
	 * @return %R1P,0,0:RC,reSwitch
	 */
	public GCDataPacket CAM_GetCameraPowerSwitch (CAM_ID_TYPE CamID) {
		GCDataPacket packet = new GCDataPacket(
				RPC.CAM_GetCameraPowerSwitch,
				String.valueOf(CamID.getValue())
		);
		this.doCommand(packet);
		return packet;
	}
	
	/**
	 * This command sets the camera power switch state (on/off).
	 * @param CamID Camera ID
	 * @param eSwitch Power state (on/off)
	 * @return %R1P,0,0:RC
	 */
	public GCDataPacket CAM_SetCameraPowerSwitch (CAM_ID_TYPE CamID, ON_OFF_TYPE eSwitch) {
		GCDataPacket packet = new GCDataPacket(
				RPC.CAM_SetCameraPowerSwitch,
				String.valueOf(CamID.getValue()) + "," + String.valueOf(eSwitch.getValue())
		);
		this.doCommand(packet);
		return packet;
	}
	
	/**
	 * This command waits for the specified time for the camera to become ready. After for example standby the
	 * camera requires around half a minute to become usable again. This command waits for the camera and returns
	 * GRC_OK if it is ready. If the specified time expires before the camera is usable, timeout is returned.
	 * 
	 * @param CamID Camera ID
	 * @param ulTimeout Timeout in ms
	 * @return %R1P,0,0:RC
	 */
	public GCDataPacket CAM_WaitForCameraReady (CAM_ID_TYPE CamID, long ulTimeout) {
		GCDataPacket packet = new GCDataPacket(
				RPC.CAM_WaitForCameraReady,
				String.valueOf(CamID.getValue()) + "," + String.valueOf(ulTimeout)
		);
		this.doCommand(packet);
		return packet;
	}
	
	/**
	 * This command set the Autofocus motor to the entered position.
	 * @param lMotorPosition Motor position for the AF.
	 * @return %R1P,0,0:RC
	 */
	public GCDataPacket CAM_AF_SetMotorPosition (long lMotorPosition) {
		GCDataPacket packet = new GCDataPacket(
				RPC.CAM_AF_SetMotorPosition,
				String.valueOf(lMotorPosition)
		);
		this.doCommand(packet);
		return packet;
	}
	
	/**
	 * This command get the actual motor position for the Autofocus.
	 * @return %R1P,0,0:RC,lMotorPosition
	 */
	public GCDataPacket CAM_AF_GetMotorPosition () {
		GCDataPacket packet = new GCDataPacket(
				RPC.CAM_AF_GetMotorPosition
		);
		this.doCommand(packet);
		return packet;
	}
	
	/**
	 * This command start and stop the continuous autofocus.
	 * Note: The distance measurement mode depends on the currently selected EDM mode.
	 * The distance is measured either by IR or by RL tracking.
	 * @param bStart 
	 * @return %R1P,0,0:RC
	 */
	public GCDataPacket CAM_AF_ContinuousAutofocus (BOOLE bStart) {
		GCDataPacket packet = new GCDataPacket(
				RPC.CAM_AF_ContinuousAutofocus,
				String.valueOf(bStart.getValue())
		);
		this.doCommand(packet);
		return packet;
	}
	
	/**
	 * This command sets the Focus motor to entered distance.
	 * @param dDistance Position to focus.
	 * @return %R1P,0,0:RC
	 */
	public GCDataPacket CAM_AF_PositFocusMotorToDist (double dDistance) {
		GCDataPacket packet = new GCDataPacket(
				RPC.CAM_AF_PositFocusMotorToDist,
				String.valueOf(dDistance)
		);
		this.doCommand(packet);
		return packet;
	}
	
	/**
	 * This command sets the Focus motor to infinity
	 * @return %R1P,0,0:RC
	 */
	public GCDataPacket CAM_AF_PositFocusMotorToInfinity() {
		GCDataPacket packet = new GCDataPacket(
				RPC.CAM_AF_PositFocusMotorToInfinity
		);
		this.doCommand(packet);
		return packet;
	}
	
	/**
	 * This command Autofocus to current target.
	 * @return %R1P,0,0:RC
	 */
	public GCDataPacket CAM_AF_SingleShotAutofocus() {
		GCDataPacket packet = new GCDataPacket(
				RPC.CAM_AF_SingleShotAutofocus
		);
		this.doCommand(packet);
		return packet;
	}
	
	/**
	 * With this commands the focus is done by contrast around current Target
	 * @param nSteps Steps for focus.
	 * @return %R1P,0,0:RC
	 */
	public GCDataPacket CAM_AF_FocusContrastArroundCurrent (short nSteps) {
		GCDataPacket packet = new GCDataPacket(
				RPC.CAM_AF_FocusContrastArroundCurrent,
				String.valueOf(nSteps)
		);
		this.doCommand(packet);
		return packet;
	}
	
	/**
	 * This command returns the Chip window size.
	 * @param CamID Camera ID
	 * @return %R1P,0,0:RC,rChipWindowSize
	 */
	public GCDataPacket CAM_AF_GetChipWindowSize (CAM_ID_TYPE CamID) {
		GCDataPacket packet = new GCDataPacket(
				RPC.CAM_AF_GetChipWindowSize,
				String.valueOf(CamID.getValue())
		);
		this.doCommand(packet);
		return packet;
	}
	
	/**
	 * This command returns the cross Hair position for the actual camera resolution.
	 * @return %R1P,0,0:RC,roCrossHairPos
	 */
	public GCDataPacket CAM_OAC_GetCrossHairPos() {
		GCDataPacket packet = new GCDataPacket(
				RPC.CAM_OAC_GetCrossHairPos
		);
		this.doCommand(packet);
		return packet;
	}
	
	/**
	 * This command returns the interior orientation for the OVC camera for the actual camera position.
	 * @param bReadCalData
	 * @return %R1P,0,0:RC,rInterOrient
	 */
	public GCDataPacket CAM_OVC_ReadInterOrient(BOOLE bReadCalData) {
		GCDataPacket packet = new GCDataPacket(
				RPC.CAM_OVC_ReadInterOrient,
				String.valueOf(bReadCalData.getValue())
		);
		this.doCommand(packet);
		return packet;
	}
	
	/**
	 * This command returns the exterior orientation for the OVC camera for the actual camera position.
	 * 3D camera location with respect to the intersection of Hz and V axes [m]
 	 * Hz, V rotation of camera and image rotation [rad]
	 * @param bReadCalData
	 * @return %R1P,0,0:RC,rExterOrient
	 */
	public GCDataPacket CAM_OVC_ReadExterOrient(BOOLE bReadCalData) {
		GCDataPacket packet = new GCDataPacket(
				RPC.CAM_OVC_ReadExterOrient,
				String.valueOf(bReadCalData.getValue())
		);
		this.doCommand(packet);
		return packet;
	}
	
	/**
	 * This command starts a remote video which can be watched with VLC player. The VLC player can be
	 * downloaded at http://www.videolan.org/vlc/. To watch the video following address needs to be opened under
	 * Media -> Networkstream: rtsp://192.168.254.3/TSCam
	 * Parameter 2 changed from nQuality (Valid qualities: 2(highest) to 31(lowest)) in firmware version 5.50 nBitrate
	 * (Valid bitrates [kbps] Range between 100 kbps and 6144 kbps). It is recommended to check the firmware
	 * version with the CSV_GetSWVersion command to set the correct parameters.
	 * 
	 * @param CamID Camera ID
	 * @param nFrameRate [Hz] 3 Hz, 5 Hz or 10 Hz allowed
	 * @param nBitRate [kbps] Range between 100 kbps and 6144 kbps allowed
	 * @return %R1P,0,0:RC
	 */
	public GCDataPacket CAM_StartRemoteVideo (CAM_ID_TYPE CamID, short nFrameRate, short nBitRate) {
		GCDataPacket packet = new GCDataPacket(
				RPC.CAM_StartRemoteVideo,
				String.valueOf(CamID.getValue()) + "," + String.valueOf(nFrameRate) + "," + String.valueOf(nBitRate)
		);
		this.doCommand(packet);
		return packet;
	}
	
	/**
	 * This command stops a started remote video.
	 * @return %R1P,0,0:RC
	 */
	public GCDataPacket CAM_StopRemoteVideo () {
		GCDataPacket packet = new GCDataPacket(
				RPC.CAM_StopRemoteVideo
		);
		this.doCommand(packet);
		return packet;
	}
	
	/****************************************************/
	/*                                                  */
	/*              Image Processing - IMG              */
	/*                                                  */
	/****************************************************/
	
	/*********************************** IMAGE PROCESSING **************************************/
	
	/**
	 * Reading the actual image configuration.
	 * 
	 * @param eMemType Memory device type
	 * @return %R1P,0,0:RC,ulImageNumber[long],ulQuality[long],ulSubFunctNumber[long],szFileNamePrefix[string]
	 */
	public GCDataPacket IMG_GetTccConfig(IMG_MEM_TYPE eMemType) {
		GCDataPacket packet = new GCDataPacket(
				RPC.IMG_GetTccConfig,
				String.valueOf(eMemType.getValue())
		);
		this.doCommand(packet);
		return packet;
	}
	
	/**
	 * Setting the actual image configuration
	 * @param eMemType Memory device type
	 * @param Parameters Image configuration
	 * @return %R1P,0,0:RC
	 */
	public GCDataPacket IMG_SetTccConfig(IMG_MEM_TYPE eMemType, IMG_TCC_CONFIG Parameters) {
		GCDataPacket packet = new GCDataPacket(
				RPC.IMG_SetTccConfig,
				String.valueOf(eMemType.getValue()) + "," +
						String.valueOf(Parameters.ulImageNumber) + "," +
						String.valueOf(Parameters.ulQuality) + "," +
						String.valueOf(Parameters.ulSubFunctNumber) + "," +
						"\""+String.valueOf(Parameters.szFileNamePrefix)+"\""
		);
		this.doCommand(packet);
		return packet;
	}
	
	/**
	 * Capture a telescopic image (timeout is set to 60.000 ms)
	 * 
	 * @param eMemType Memory device type
	 * @return %R1P,0,0:RC,runImageNumber
	 */
	public GCDataPacket IMG_TakeTccImage(IMG_MEM_TYPE eMemType) {
		return IMG_TakeTccImage(eMemType, 60000L);
	}
	
	/**
	 * Capture a telescopic image
	 * 
	 * @param eMemType Memory device type
	 * @param timeOut Timeout (default 60.000 ms)
	 * @return %R1P,0,0:RC,runImageNumber
	 */
	public GCDataPacket IMG_TakeTccImage(IMG_MEM_TYPE eMemType, long timeOut) {
		GCDataPacket packet = new GCDataPacket(
				RPC.IMG_TakeTccImage,
				String.valueOf(eMemType.getValue()),
				timeOut
		);
		this.doCommand(packet);
		return packet;
	}
	
	/**
	 * Set the exposure time for images
	 * @param unExposureTime
	 * 
	 * @return %R1P,0,0:RC
	 */
	public GCDataPacket IMG_SetTccExposureTime(int unExposureTime) {
		GCDataPacket packet = new GCDataPacket(
				RPC.IMG_SetTccExposureTime,
				String.valueOf(Math.abs(unExposureTime))
		);
		this.doCommand(packet);
		return packet;
	}
	
	/****************************************************/
	/*                                                  */
	/*    Theodolite Measurement and Calculation TMC    */
	/*                                                  */
	/****************************************************/	

	/********************************* MEASUREMENT FUNCTIONS ***********************************/

	/**
	 * Getting the coordinates of a measured point
	 * queries an angle measurement and, in dependence of the selected Mode, an inclination
	 * measurement and calculates the coordinates of the measured point with an already measured distance. A
	 * distance measurement has to be started in advance. The <code>WaitTime</code> is a delay to wait for the distance
	 * measurement to finish. Single and tracking measurements are supported. Information about a missing distance
	 * measurement and other information about the quality of the result is returned in the returncode.
	 * @param  WaitTime
	 * @param  TMC_INCLINE_MODE
	 * @return %R1P,0,0:RC,E[double],N[double],H[double],CoordTime[long],
	 *         E-Cont[double],N-Cont[double],H-Cont[double],CoordContTime[long]
	 *
	 */
	public GCDataPacket TMC_GetCoordinate(long WaitTime, TMC_INCLINE_PRG mode) {
		GCDataPacket packet = new GCDataPacket(
				RPC.TMC_GetCoordinate,
				WaitTime + "," + mode.getValue()
		);
		this.doCommand(packet);
		return packet;
	}

	/**
	 * Returning an angle and distance measurement
	 * returns the angles and distance measurement data. This command does not issue a new distance
	 * measurement. A distance measurement has to be started in advance. If a distance measurement is valid the
	 * function ignores <code>WaitTime</code> and returns the results. If no valid distance measurement is available and the
	 * distance measurement unit is not activated (by <code>TMC_DoMeasure</code> before the <code>TMC_GetSimpleMea</code> call) the angle
	 * measurement result is returned after the waittime. Information about distance measurement is returned in the
	 * return code.
	 * @param  WaitTime
	 * @param  TMC_INCLINE_MODE
	 * @return %R1P,0,0:RC,Hz[double],V[double],SlopeDistance[double]
	 *
	 */
	public GCDataPacket TMC_GetSimpleMea(long WaitTime, TMC_INCLINE_PRG mode) {
		GCDataPacket packet = new GCDataPacket(
				RPC.TMC_GetSimpleMea,
				WaitTime + "," + mode.getValue(),
				2*WaitTime
		);

		this.doCommand(packet);
		return packet;
	}

	/**
	 * Returns complete angle measurement
	 * carries out an angle measurement and, in dependence of configuration, inclination measurement
	 * and returns the results. As shown the result is very comprehensive. For simple angle measurements use
	 * <code>TMC_GetAngle5</code> or <code>TMC_GetSimpleMea</code> instead.
	 * Information about measurement is returned in the return code.
	 * @param TMC_INCLINE_MODE
	 * @return %R1P,0,0:RC,Hz[double],V[double],AngleAccuracy[double],
	 *         AngleTime[long],CrossIncline[double],LengthIncline[double],
	 *         AccuracyIncline[double],InclineTime[long],FaceDef[long]
	 *
	 */
	public GCDataPacket TMC_GetAngle1(TMC_INCLINE_PRG mode) {
		GCDataPacket packet = new GCDataPacket(
				RPC.TMC_GetAngle1,
				String.valueOf(mode.getValue())
		);

		this.doCommand(packet);
		return packet;
	}

	/**
	 * Returns simple angle measurement
	 * carries out an angle measurement and returns the results. In contrast to the function
	 * <code>TMC_GetAngle1</code> this function returns only the values of the angle. For simple angle measurements use
	 * <code>TMC_GetSimpleMea</code> instead.
	 * Information about measurement is returned in the return code.
	 * @param TMC_INCLINE_MODE
	 * @return %R1P,0,0:RC,Hz[double],V[double]
	 *
	 */
	public GCDataPacket TMC_GetAngle5(TMC_INCLINE_PRG mode) {
		GCDataPacket packet = new GCDataPacket(
				RPC.TMC_GetAngle5,
				String.valueOf(mode.getValue())
		);

		this.doCommand(packet);
		return packet;
	}

	/**
	 * Returns slope-distance and hz-,v-angle
	 * starts an EDMTracking measurement and waits until a distance is measured. Then it returns the
	 * angle and the slope-distance, but no coordinates. If no distance can be measured, it returns the
	 * angle values (hz,v) and the corresponding return-code.
	 * In order to abort the current measuring program use the function <code>TMC_DoMeasure</code>.
	 * @return %R1P,0,0:RC,dHz[double],dV[double],dSlopeDistance[double]
	 *
	 **/
	public GCDataPacket TMC_QuickDist(){
		GCDataPacket packet = new GCDataPacket(
				RPC.TMC_QuickDist
		);

		this.doCommand(packet);
		return packet;
	}

	/**
	 * This function returns angle, inclination and distance measurement data including accuracy and distance
	 * measurement time [ms]. This command does not issue a new distance measurement. A distance measurement has to be
	 * started in advance. If a distance measurement is valid the function ignores WaitTime and returns the results.
	 * If no valid distance measurement is available and the distance measurement unit is not
	 * activated (by <code>TMC_DoMeasure</code> before the <code>TMC_GetFullMeas</code> call) the angle measurement result is returned
	 * after the waiting time. Information about distance measurement is returned in the return code.
	 *
	 * @param WaitTime
	 * @param TMC_INCLINE_PRG
	 * @return %R1P,0,0:RC,Hz[double],V[double],AccAngle[double],C[double],L[double],AccIncl[double],SlopeDist[double],DistTime[double]
	 */
	public GCDataPacket TMC_GetFullMeas (long WaitTime, TMC_INCLINE_PRG mode) {
		GCDataPacket packet = new GCDataPacket(
				RPC.TMC_GetFullMeas,
				WaitTime + "," + mode.getValue(),
				2*WaitTime
		);

		this.doCommand(packet);
		return packet;
	}	
	
	/********************************* MEASUREMENT CONTROL FUNCTIONS ***********************************/

	/**
	 * Carries out a distance measurement
	 * carries out a distance measurement according to the TMC measurement mode like single distance,
	 * tracking,... . Please note that this command does not output any values (distances). In order to get the values you
	 * have to use other measurement functions such as <code>TMC_GetCoordinate</code>, <code>TMC_GetSimpleMea</code> or
	 * <code>TMC_GetAngle</code>.
	 * The result of the distance measurement is kept in the instrument and is valid to the next <code>TMC_DoMeasure</code>
	 * command where a new distance is requested or the distance is clear by the measurement program <code>TMC_CLEAR</code>.
	 * @param TMC_MEASURE_MODE
	 * @param TMC_INCLINE_MODE
	 * @return %R1P,0,0:RC
	 *
	 */
	public GCDataPacket TMC_DoMeasure(TMC_MEASURE_PRG command, TMC_INCLINE_PRG mode) {
		GCDataPacket packet = new GCDataPacket(
				RPC.TMC_DoMeasure,
				command.getValue() + "," + mode.getValue()
		);
		this.doCommand(packet);
		return packet;
	}

	/**
	 * Input slope distance and height offset
	 * Used to input manually measured slope distance and height offset for a following measurement.
	 * Additionally an inclination measurement and an angle measurement are carried out to determine the coordinates
	 * of target. The V-angle is corrected to PI/2 or 3/2*PI in dependence of the instrument's face because of the manual
	 * input.
	 * <strong>After this command the previous measured distance is cleared.</strong>
	 * @param  SlopeDistance
	 * @param  HeightOffset
	 * @param  TMC_INCLINE_MODE
	 * @return %R1P,0,0:RC
	 *
	 */
	public GCDataPacket TMC_SetHandDist (double slopeDistance, double hgtOffset, TMC_INCLINE_PRG mode) {
		GCDataPacket packet = new GCDataPacket(
				RPC.TMC_SetHandDist,
				slopeDistance + "," + hgtOffset + "," + mode.getValue()
		);
		this.doCommand(packet);
		return packet;
	}

	/********************************* DATA SETUP FUNCTIONS ***********************************/

	/**
	 * Returns the current reflector height
	 * @return %R1P,0,0:RC,Height[double]
	 *
	 */
	public GCDataPacket TMC_GetHeight() {
		GCDataPacket packet = new GCDataPacket(
				RPC.TMC_GetHeight
		);
		this.doCommand(packet);
		return packet;
	}

	/**
	 * sets a new reflector height
	 * @param height
	 * @return %R1P,0,0:RC
	 *
	 */
	public GCDataPacket TMC_SetHeight(double height) {
		GCDataPacket packet = new GCDataPacket(
				RPC.TMC_SetHeight,
				String.valueOf(height)
		);
		this.doCommand(packet);
		return packet;
	}
	
	/**
	 * getting the atmospheric correction parameters
	 * @return %R1P,0,0:RC,Lambda[double],Pressure[double],DryTemperature[double],WetTemperature[double]
	 *
	 */
	public GCDataPacket TMC_GetAtmCorr() {
		GCDataPacket packet = new GCDataPacket(
				RPC.TMC_GetAtmCorr
		);
		this.doCommand(packet);
		return packet;
	}
	
	/**
	 * determining the atmospheric correction parameters
	 * @param TMC_ATMOS_TEMPERATURE
	 * @return %R1P,0,0:RC,PPM[double]
	 * @deprecated Replace with TMC_SetAtmCorr should only be used for older instruments such as the TCA2003
	 *
	 */
	public GCDataPacket TMC_CalcATMCorr(TMC_ATMOS_TEMPERATURE atmTemperature) {
		return this.TMC_CalcATMCorr(atmTemperature.dLambda, atmTemperature.dPressure, atmTemperature.dDryTemperature, atmTemperature.dWetTemperature);
	}
	
	/**
	 * determining the atmospheric correction parameters
	 * %R1Q,2027:Wellenlänge, Luftdruck, Trockentemp, Feuchttemp 
	 * @param lambda
	 * @param pressure
	 * @param dryTemperature
	 * @param wetTemperature
	 * @return %R1P,0,0:RC,PPM[double]
	 * @deprecated Replace with TMC_SetAtmCorr should only be used for older instruments such as the TCA2003
	 *
	 */
	public GCDataPacket TMC_CalcATMCorr(double lambda, double pressure, double dryTemperature, double wetTemperature) {
		GCDataPacket packet = new GCDataPacket(
				RPC.TMC_CalcATMCorr,
				lambda+","+pressure+","+dryTemperature+","+wetTemperature
		);
		this.doCommand(packet);
		return packet;
	}
	
	/**
	 * setting the atmospheric correction parameters
	 * @param TMC_ATMOS_TEMPERATURE
	 * @return %R1P,0,0:RC
	 *
	 */
	public GCDataPacket TMC_SetAtmCorr(TMC_ATMOS_TEMPERATURE atmTemperature) {
		return this.TMC_SetAtmCorr(atmTemperature.dLambda, atmTemperature.dPressure, atmTemperature.dDryTemperature, atmTemperature.dWetTemperature);
	}

	/**
	 * setting the atmospheric correction parameters
	 * @param lambda
	 * @param pressure
	 * @param dryTemperature
	 * @param wetTemperature
	 * @return %R1P,0,0:RC
	 *
	 */
	public GCDataPacket TMC_SetAtmCorr(double lambda, double pressure, double dryTemperature, double wetTemperature) {
		GCDataPacket packet = new GCDataPacket(
				RPC.TMC_SetAtmCorr,
				lambda+","+pressure+","+dryTemperature+","+wetTemperature
		);
		this.doCommand(packet);
		return packet;
	}
	
	/**
	 * orientating the instrument in hz-direction
	 * used to orientate the instrument in Hz direction. It is a combination of an angle measurement to
	 * get the Hz offset and afterwards setting the angle Hz offset in order to orientates onto a target. Before the new
	 * orientation can be set an existing distance must be
	 * cleared (use <code>TMC_DoMeasure</code> with the <code>TMC_MEASURE_MODE = TMC_CLEAR</code>).
	 * @param HzOrientation [RAD]
	 * @return %R1P,0,0:RC
	 *
	 */
	public GCDataPacket TMC_SetOrientation(double HzOrientation) {
		GCDataPacket packet = new GCDataPacket(
				RPC.TMC_SetOrientation,
				String.valueOf(HzOrientation)
		);
		this.doCommand(packet);
		return packet;
	}

	/**
	 * getting the prism constant in [m]
	 * @return %R1P,0,0:RC,PrismCorr[double]
	 *
	 */
	public GCDataPacket TMC_GetPrismCorr() {
		GCDataPacket packet = new GCDataPacket(
				RPC.TMC_GetPrismCorr
		);
		this.doCommand(packet);
		return packet;
	}

	/**
	 * setting a new prism constant in [m]
	 * @param PrismCorr [m]
	 * @return %R1P,0,0:RC
	 * @deprecated Methode enfaellt ab GeoCOM-Version 1.5+
	 */
	public GCDataPacket TMC_SetPrismCorr(double PrismCorr) {
		GCDataPacket packet = new GCDataPacket(
				RPC.TMC_SetPrismCorr,
				String.valueOf(PrismCorr)
		);
		this.doCommand(packet);
		return packet;
	}

	/**
	 * getting the refraction coefficient
	 * @return %R1P,0,0:RC,RefOn[boolean],EarthRadius[double], RefractiveScale[double]
	 *
	 */
	public GCDataPacket TMC_GetRefractiveCorr() {
		GCDataPacket packet = new GCDataPacket(
				RPC.TMC_GetRefractiveCorr
		);
		this.doCommand(packet);
		return packet;
	}
	
	/**
	 * setting the refraction coefficient
	 * @param refractive
	 * @return %R1P,0,0:RC
	 *
	 */
	public GCDataPacket TMC_SetRefractiveCorr(TMC_REFRACTION refractive) {
		return this.TMC_SetRefractiveCorr(refractive.eRefOn, refractive.dEarthRadius, refractive.dRefractiveScale);
	}

	/**
	 * setting the refraction coefficient
	 * @param RefOn (0 or 1) int as boolean
	 * @param EarthRadius
	 * @param RefractiveScale
	 * @return %R1P,0,0:RC
	 *
	 */
	public GCDataPacket TMC_SetRefractiveCorr(ON_OFF_TYPE RefOn, double EarthRadius, double RefractiveScale) {
		GCDataPacket packet = new GCDataPacket(
				RPC.TMC_SetRefractiveCorr,
				RefOn.getValue()+","+EarthRadius+","+RefractiveScale
		);
		this.doCommand(packet);
		return packet;
	}

	/**
	 * getting the refraction model
	 * used to get the current refraction model. Note that changing the refraction method is not
	 * indicated on the instrument's interface.
	 * @return %R1P,0,0:RC,Method[unsigned short]
	 *         Method = 1 means method 1 (for the rest of the world)
	 *         Method = 2 means method 2 (for Australia)
	 *
	 */
	public GCDataPacket TMC_GetRefractiveMethod() {
		GCDataPacket packet = new GCDataPacket(
				RPC.TMC_GetRefractiveMethod
		);
		this.doCommand(packet);
		return packet;
	}

	/**
	 * setting the refraction model
	 * @param  Method
	 *         Method = 1 means method 1 (for the rest of the world)
	 *         Method = 2 means method 2 (for Australia)
	 * @return %R1P,0,0:RC
	 *
	 */

	public GCDataPacket TMC_SetRefractiveMethod(int Method) {
		Method = Method != 2 ? 1 : 2;
		GCDataPacket packet = new GCDataPacket(
				RPC.TMC_SetRefractiveMethod,
				String.valueOf(Method)
		);
		this.doCommand(packet);
		return packet;
	}

	/**
	 * getting the station coordinates of the instrument
	 * @return %R1P,0,0:RC,E0[double],N0[double],H0[double],Hi[double]
	 *
	 */
	public GCDataPacket TMC_GetStation() {
		GCDataPacket packet = new GCDataPacket(
				RPC.TMC_GetStation
		);
		this.doCommand(packet);
		return packet;
	}
	
	/**
	 * setting the station coordinates of the instrument
	 * @param  TMC_STATION
	 * @return %R1P,0,0:RC
	 *
	 */
	public GCDataPacket TMC_SetStation(TMC_STATION station) {
		return this.TMC_SetStation(station.dE0, station.dN0, station.dH0, station.dHi);
	}

	/**
	 * setting the station coordinates of the instrument
	 * @param  E0
	 * @param  N0
	 * @param  H0
	 * @param  Hi
	 * @return %R1P,0,0:RC
	 *
	 */
	public GCDataPacket TMC_SetStation(double E0, double N0, double H0, double Hi) {
		GCDataPacket packet = new GCDataPacket(
				RPC.TMC_SetStation,
				E0+","+N0+","+H0+","+Hi
		);
		this.doCommand(packet);
		return packet;
	}

	/**
	 * getting the atmospheric [ppm] correction factor
	 * @return %R1P,0,0:RC,dPpmA[double]
	 *
	 */
	public GCDataPacket TMC_GetAtmPpm() {
		GCDataPacket packet = new GCDataPacket(
				RPC.TMC_GetAtmPpm
		);
		this.doCommand(packet);
		return packet;
	}

	/**
	 * setting the atmospheric ppm correction factor
	 * @param  dPpmA [ppm]
	 * @return %R1P,0,0:RC
	 *
	 */
	public GCDataPacket TMC_SetAtmPpm(double dPpmA) {
		GCDataPacket packet = new GCDataPacket(
				RPC.TMC_SetAtmPpm,
				String.valueOf(dPpmA)
		);
		this.doCommand(packet);
		return packet;
	}
	
	/**
	 * setting the distance ppm correction factor
	 * @param  dPpmD [ppm]
	 * @return %R1P,0,0:RC
	 * @deprecated Replace with TMC_SetAtmPpm should only be used for older instruments such as the TCA2003
	 */
	public GCDataPacket TMC_SetDistPPm(double dPpmD) {
		GCDataPacket packet = new GCDataPacket(
				RPC.TMC_SetDistPPm,
				String.valueOf(dPpmD)
		);
		this.doCommand(packet);
		return packet;
	}

	/**
	 * getting the geometric ppm correction factor
	 * @return %R1P,0,0:RC,unGeomUseAutomatic[unsigned short],dScaleFactorCentralMeridian[double],
	 *         dOffsetCentralMeridian[double],dHeightReductionPPM[double],dIndividualPPM[double]
	 *
	 */
	public GCDataPacket TMC_GetGeoPpm() {
		GCDataPacket packet = new GCDataPacket(
				RPC.TMC_GetGeoPpm
		);
		this.doCommand(packet);
		return packet;
	}

	/**
	 * setting the geometric ppm correction factor
	 * @param  unGeomUseAutomatic[int]
	 * @param  dScaleFactorCentralMeridian[double]
	 * @param  dOffsetCentralMeridian[double]
	 * @param  dHeightReductionPPM[double]
	 * @param  dIndividualPPM[double]
	 * @return %R1P,0,0:RC
	 *
	 */
	public GCDataPacket TMC_SetGeoPpm(int unGeomUseAutomatic, double dScaleFactorCentralMeridian, double dOffsetCentralMeridian, double dHeightReductionPPM, double dIndividualPPM) {
		GCDataPacket packet = new GCDataPacket(
				RPC.TMC_SetGeoPpm,
				unGeomUseAutomatic+","+dScaleFactorCentralMeridian+","+dOffsetCentralMeridian+","+dHeightReductionPPM+","+dIndividualPPM
		);
		this.doCommand(packet);
		return packet;
	}
	
	  
	/********************************* INFORMATION FUNCTIONS ***********************************/

	/**
	 * getting the face information of the current telescope position (1. Lage / 2. Lage)
	 * returns the face information of the current telescope position. The face information is only valid, if
	 * the instrument is in an active measurement state (that means a measurement function was called before the
	 * <code>TMC_GetFace</code> call, see example). Note that the instrument automatically turns into an
	 * inactive measurement state after a predefined timeout.
	 * @return %R1P,0,0:RC,Face[long]
	 *
	 */
	public GCDataPacket TMC_GetFace() {
		GCDataPacket packet = new GCDataPacket(
				RPC.TMC_GetFace
		);
		this.doCommand(packet);
		return packet;
	}

	/**
	 * returns information about the intensity of the EDM signal. The function can only perform a
	 * measurement if the signal measurement program is activated. Start the signal measurement program
	 * with <code>TMC_DoMeasure</code> where <code>TMC_MEASURE_MODE = TMC_SIGNAL</code>. After the measurement
	 * the EDM must be switched off (use <code>TMC_DoMeasure</code> where <code>TMC_MEASURE_MODE = TMC_CLEAR</code>).
	 * While measuring there is no angle measurement data available.
	 * @return %R1P,0,0:RC,SignalIntensity[double],Time[long]
	 *
	 */
	public GCDataPacket TMC_GetSignal() {
		GCDataPacket packet = new GCDataPacket(
				RPC.TMC_GetSignal
		);
		this.doCommand(packet);
		return packet;
	}
	
	/******************************** CONFIGURATION FUNCTIONS ************************************/

	/**
	 * returns the angular corrections status.
	 * @return %R1P,0,0:RC,InclineCorr[long],StandAxisCorr[long],
	 *         CollimationCorr[long],TiltAxisCorr[long]
	 *
	 */
	public GCDataPacket TMC_GetAngSwitch() {
		GCDataPacket packet = new GCDataPacket(
				RPC.TMC_GetAngSwitch
		);
		this.doCommand(packet);
		return packet;
	}
	
	/**
	 * can enable/disable follow angle measurement correction.
	 * <tt>incline</tt>: The incline will be considered in the angle measurement if enabled.
	 * <tt>stand axis</tt>: The stand axis will be considered in the angle measurement if enabled.
	 * <tt>collimation:</tt> The collimation will be considered in the angle measurement if enabled
	 * <tt>tilt axis:</tt> The tilt axis will be considered in the angle measurement if enabled.
	 * @param TMC_ANG_SWITCH
	 * @return %R1P,0,0:RC
	 *
	 */
	public GCDataPacket TMC_SetAngSwitch(TMC_ANG_SWITCH SwCorr) {
		return this.TMC_SetAngSwitch(SwCorr.eInclineCorr,SwCorr.eStandAxisCorr,SwCorr.eCollimationCorr,SwCorr.eTiltAxisCorr);
	}

	/**
	 * can enable/disable follow angle measurement correction.
	 * <tt>incline</tt>: The incline will be considered in the angle measurement if enabled.
	 * <tt>stand axis</tt>: The stand axis will be considered in the angle measurement if enabled.
	 * <tt>collimation:</tt> The collimation will be considered in the angle measurement if enabled
	 * <tt>tilt axis:</tt> The tilt axis will be considered in the angle measurement if enabled.
	 * @param InclineCorr
	 * @param StandAxisCorr
	 * @param CollimationCorr
	 * @param TiltAxisCorr
	 * @return %R1P,0,0:RC
	 *
	 */
	public GCDataPacket TMC_SetAngSwitch(ON_OFF_TYPE InclineCorr, ON_OFF_TYPE StandAxisCorr, ON_OFF_TYPE CollimationCorr, ON_OFF_TYPE TiltAxisCorr) {
		GCDataPacket packet = new GCDataPacket(
				RPC.TMC_SetAngSwitch,
				InclineCorr.getValue()+","+StandAxisCorr.getValue()+","+CollimationCorr.getValue()+","+TiltAxisCorr.getValue()
		);
		this.doCommand(packet);
		return packet;
	}

	/**
	 * returns the current dual axis compensator status
	 * 1 ... ON
	 * 2 ... OFF
	 * @return %R1P,0,0:RC,SwCorr[long]
	 *
	 */
	public GCDataPacket TMC_GetInclineSwitch() {
		GCDataPacket packet = new GCDataPacket(
				RPC.TMC_GetInclineSwitch
		);
		this.doCommand(packet);
		return packet;
	}

	/**
	 * switches the dual axis compensator on or off.
	 * @param SwCorr (0 = off or 1 = on)
	 * @return %R1P,0,0:RC
	 *
	 */
	public GCDataPacket TMC_SetInclineSwitch(ON_OFF_TYPE SwCorr) {
		GCDataPacket packet = new GCDataPacket(
				RPC.TMC_SetInclineSwitch,
				String.valueOf(SwCorr.getValue())
		);
		this.doCommand(packet);
		return packet;
	}

	/**
	 * returns the EDM measurement mode.
	 * @return %R1P,0,0:RC,EDM_MODE
	 * @see TMC_EDM_MODE
	 *
	 */
	public GCDataPacket TMC_GetEdmMode() {
		GCDataPacket packet = new GCDataPacket(
				RPC.TMC_GetEdmMode
		);
		this.doCommand(packet);
		return packet;
	}

	/**
	 * sets the current measurement mode. The measure function <code>TMC_DoMeasure(TMC_DEF_DIST)</code>
	 * uses this configuration.
	 * @param EDM_MODE
	 * @return %R1P,0,0:RC
	 * @see TMC_EDM_MODE
	 *
	 */
	public GCDataPacket TMC_SetEdmMode(EDM_MODE mode) {
		GCDataPacket packet = new GCDataPacket(
				RPC.TMC_SetEdmMode,
				String.valueOf(mode.getValue())
		);
		this.doCommand(packet);
		return packet;
	}

	/**
	 * gets the cartesian coordinates if a valid distance exists. The parameter <code>WaitTime</code> defined the
	 * max wait time in order to get a valid distance. If after the wait time a valid distance does not exist, the function
	 * initialises the parameter for the coordinates <code>(E,N,H)</code> with 0 and returns an error. For the coordinate calculate
	 * will require incline results. With the parameter <code>TMC_INCLINE_MODE</code> you have the possibility to either measure an inclination,
	 * use the pre-determined plane to calculate an inclination, or use the automatic mode wherein the system decides
	 * which method is appropriate
	 * @param  WaitTime
	 * @param  TMC_INCLINE_MODE
	 * @return %R1P,0,0:RC,dCoordE[double], dCoordN[double], dCoordH[double]
	 * @see TMC_INCLINE_MODE
	 *
	 */
	public GCDataPacket TMC_GetSimpleCoord(long WaitTime, TMC_INCLINE_PRG mode) {
		GCDataPacket packet = new GCDataPacket(
				RPC.TMC_GetSimpleCoord,
				WaitTime + "," + mode.getValue()
		);
		this.doCommand(packet);
		return packet;
	}

	/**
	 * returns the status of the ATR correction of the last measurement. If you get a return code
	 * <code>GRC_TMC_ANGLE_NOT_FULL_CORR</code> or <code>GRC_TMC_NO_FULL_CORRECTION</code> from a measurement
	 * function, this function indicates whether the returned data is missing a deviation correction of the ATR or not.
	 * @return %R1P,0,0:RC,bAtrCorrectionError[long]
	 * @see RETRUN_CODE
	 *
	 */
	public GCDataPacket TMC_IfDataAzeCorrError() {
		GCDataPacket packet = new GCDataPacket(
				RPC.TMC_IfDataAzeCorrError
		);
		this.doCommand(packet);
		return packet;
	}

	/**
	 * returns the status of the inclination correction of the last measurement. If you get a return code
	 * <code>GRC_TMC_ANGLE_NOT_FULL_CORR</code> or <code>GRC_TMC_NO_FULL_CORRECTION</code> from a measurement
	 * function, this function indicates whether the returned data is missing an inclination correction or not. Error
	 * information can only occur if the incline sensor is active.
	 * @return %R1P,0,0:RC,bIncCorrectionError[long]
	 * @see RETRUN_CODE
	 *
	 */
	public GCDataPacket TMC_IfDataIncCorrError() {
		GCDataPacket packet = new GCDataPacket(
				RPC.TMC_IfDataIncCorrError
		);
		this.doCommand(packet);
		return packet;
	}

	/**
	 * retrieves the total ppm value (atmospheric+geometric ppm) plus the current prism constant.
	 * @return %R1P,0,0: RC,dPpmCorr[double], dPrismCorr[double]
	 * @see RETRUN_CODE
	 *
	 */
	public GCDataPacket TMC_GetSlopeDistCorr() {
		GCDataPacket packet = new GCDataPacket(
				RPC.TMC_GetSlopeDistCorr
		);
		this.doCommand(packet);
		return packet;
	}
	
	/***********************/
	/*                     */
	/*    ALT USER - AUS   */
	/*                     */
	/***********************/

	/**
	 * Get the current status of the ATR mode on TCA instruments. This command does
	 * not indicate whether the ATR has currently acquired a prism.
	 * @return %R1P,0,0:RC,OnOff[long]
	 *
	 */
	public GCDataPacket AUS_GetUserAtrState() {
		GCDataPacket packet = new GCDataPacket(
				RPC.AUS_GetUserAtrState
		);
		this.doCommand(packet);
		return packet;
	}

	/**
	 * Activate respectively deactivate the ATR mode.
	 * Activate ATR mode:
	 * The ATR mode is activated and the LOCK mode (if sets) will be reset automatically also.
	 * Deactivate ATR mode:
	 * The ATR mode is deactivated and the LOCK mode keep unchanged.
	 * This command is valid for TCA instruments only.
	 * @param OnOff   0=off, 1=on
	 * @return %R1P,0,0:RC
	 *
	 */
	public GCDataPacket AUS_SetUserAtrState(ON_OFF_TYPE OnOff) {
		GCDataPacket packet = new GCDataPacket(
				RPC.AUS_SetUserAtrState,
				String.valueOf(OnOff.getValue())
		);
		this.doCommand(packet);
		return packet;
	}

	/**
	 * gets the current LOCK switch. This command is valid for instruments equipped with ATR only
	 * and does not indicate whether the ATR has a prism in lock or not.
	 * With the function <code>MOT_ReadLockStatus</code> you can find out whether a target is locked or not.
	 * This command is valid for instruments with ATR only.
	 * @return %R1P,0,0:RC, OnOff[long]
	 *
	 */
	public GCDataPacket AUS_GetUserLockState() {
		GCDataPacket packet = new GCDataPacket(
				RPC.AUS_GetUserLockState
		);
		this.doCommand(packet);
		return packet;
	}

	/**
	 * Set the lock status.
	 * Status ON:
	 * The target tracking functionality is available but not activated. In order to activate target tracking, see the
	 * function <code>AUT_LockIn</code>. The ATR mode will be set automatically.
	 * Status OFF:
	 * A running target tracking will be aborted and the manual driving wheel is activated. The ATR mode will be not
	 * reset automatically respectively keep unchanged.
	 * This command is valid for TCA instruments only.
	 * @param OnOff  0=off, 1=on
	 * @return %R1P,0,0:RC
	 *
	 */
	public GCDataPacket AUS_SetUserLockState(ON_OFF_TYPE OnOff) {
		GCDataPacket packet = new GCDataPacket(
				RPC.AUS_SetUserLockState,
				String.valueOf(OnOff.getValue())
		);
		this.doCommand(packet);
		return packet;
	}
	
	/**************************/
	/*                        */
	/*    AUTOMATION - AUT    */
	/*                        */
	/**************************/


	/**
	 * reads the current setting for the positioning tolerances of the Hz- and V- instrument axis.
	 * @return %R1P,0,0:RC,Tolerance Hz[double],Tolerance V[double]
	 *
	 */
	public GCDataPacket AUT_ReadTol() {
		GCDataPacket packet = new GCDataPacket(
				RPC.AUT_ReadTol
		);
		this.doCommand(packet);
		return packet;
	}

	/**
	 * Set the positioning tolerances
	 * stops every movement and sets new values for the positioning tolerances of the Hz- and V instrument
	 * axes. This command is valid for motorized instruments only.
	 * The tolerances must be in the range of 1[cc] ( =1.57079 E-06[rad] ) to 100[cc] ( =1.57079 E-04[rad] ).
	 * @param ToleranceHz [RAD]
	 * @param ToleranceV  [RAD]
	 * @return %R1P,0,0:RC
	 *
	 */
	public GCDataPacket AUT_SetTol(AUT_POSTOL posTol) {
		GCDataPacket packet = new GCDataPacket(
				RPC.AUT_SetTol,
				posTol.dToleranceHz+","+posTol.dToleranceV
		);
		this.doCommand(packet);
		return packet;
	}

	/**
	 * reads the current setting for the positioning time out (maximum time to perform positioning).
	 * @return %R1P,0,0:RC, TimeoutHz[double], TimeoutV[double]
	 *
	 */
	public GCDataPacket AUT_ReadTimeout() {
		GCDataPacket packet = new GCDataPacket(
				RPC.AUT_ReadTimeout
		);
		this.doCommand(packet);
		return packet;
	}

	/**
	 * set the positioning timeout (set maximum time to perform a positioning).
	 * The timeout is reset on 10[sec] after each power on
	 * The values for the positioning timeout in Hz and V direction [sec]. Valid values are
	 * between 1 [sec] and 60 [sec].
	 * @param TimeoutHz [SEC]
	 * @param TimeoutV  [SEC]
	 * @return %R1P,0,0:RC
	 *
	 */
	public GCDataPacket AUT_SetTimeout(AUT_TIMEOUT timeOut) {
		GCDataPacket packet = new GCDataPacket(
				RPC.AUT_SetTimeout,
				timeOut.dTimeoutHz+","+timeOut.dTimeoutV
		);
		this.doCommand(packet);
		return packet;
	}

	/**
	 * Turns telescope to specified position
	 * turns the telescope absolute to the in Hz and V specified position, taking tolerance settings for
	 * positioning (see <code>AUT_POS_TOL</code>) into account. Any active control function is terminated by this function call.
	 * If the position mode is set to normal (<code>AUT_POS_MODE = AUT_NORMAL</code>) it is assumed that the current value of the
	 * compensator measurement is valid. Positioning precise (<code>AUT_POS_MODE = AUT_PRECISE</code>) forces a new compensator
	 * measurement at the specified position and includes this information for positioning.
	 * If ATR is possible and activated and the <code>AUT_ATR_MODE</code> is set to <code>AUT_TARGET</code>, the instrument tries to position onto
	 * a target in the destination area. In addition, the target is locked after positioning if the <code>LockIn</code> status is set. If the
	 * Lock status not set, the manual driving wheel is activated after the positioning.
	 * @param Hz              Horizontal (telescope) position [rad].
	 * @param V               Vertical (instrument) position [rad].
	 * @param AUT_POS_MODE    Position mode:
	 *                        AUT_NORMAL: (default) uses the current
	 *                        value of the compensator (no
	 *                        compensator measurement while
	 *                        positioning). For values >25GON
	 *                        positioning might tend to inaccuracy.
	 *                        AUT_PRECISE: tries to measure exact
	 *                        inclination of target. Tend to longer
	 *                        position time (check AUT_TIMEOUT
	 *                        and/or COM-time out if necessary).
	 * @param AUT_ATR_MODE    Mode of ATR:
	 *                        AUT_POSITION: (default) conventional
	 *                        position using values Hz and V.
	 *                        AUT_TARGET: tries to position onto a
	 *                        target in the destination area. This mode is
	 *                        only possible if ATR exists and is
	 *                        activated.
	 * @param bDummy          It is reserved for future use, set bDummy always to <strong>0 = FALSE</strong>
	 * @see AUT_POS_MODE
	 * @see AUT_ATR_MODE
	 * @return %R1P,0,0:RC
	 *
	 */
	public GCDataPacket AUT_MakePositioning(double dHz, double dV, AUT_POS_MODE POSMode, AUT_ATR_MODE ATRMode) {
		GCDataPacket packet = new GCDataPacket(
				RPC.AUT_MakePositioning,
				dHz+","+dV+","+POSMode.getValue()+","+ATRMode.getValue()+","+BOOLE.FALSE.getValue(),
				20000L
		);
		this.doCommand(packet);
		return packet;
	}

	/**
	 * turns the telescope to the other face - second fase.
	 * Is in the moment of the function calling an other control function active it will be terminated before.
	 * The start angle is automatically measured before the position starts.
	 * If the position mode is set to normal (<code>AUT_POS_PRG = AUT_NORMAL</code>) it is allowed that the current value of the
	 * compensator measurement is inexact. Positioning precise (<code>AUT_POS_PRG = AUT_PRECISE</code>) forces a new
	 * compensator measurement. If this measurement is not possible, the position does not take place.
	 * If ATR is possible and activated and the ATR mode is set to AUT_TARGET the instrument tries to position onto
	 * a target in the destination area. In addition, the target is locked after positioning if the <code>LockIn</code> status is set.
	 * @param bDummy         It's reserved for future use, set bDummy always to <strong>0 = FALSE</strong>
	 * @param AUT_POS_MODE
	 * @param AUT_ATR_MODE
	 * @return %R1P,0,0:RC
	 *
	 */
	public GCDataPacket AUT_ChangeFace(AUT_CHANGE_FACE face) {
		GCDataPacket packet = new GCDataPacket(
				RPC.AUT_ChangeFace,
				face.PosMode.getValue()+","+face.ATRMode.getValue()+","+face.bDummy.getValue(),
				20000L
		);
		this.doCommand(packet);
		return packet;
	}

	/**
	 * performs a positioning of the Theodolite axis onto a destination target. If the target is not within
	 * the sensor measure region a target search will be executed. The target search range is limited by the parameter
	 * <code>dSrchV</code> in V- direction and by parameter <code>dSrchHz</code> in Hz - direction. If no target found the instrument turns back
	 * to the initial start position. The <strong>ATR mode must be enabled</strong> for this functionality, see <code>AUS_SetUserAtrState</code>
	 * and <code>AUS_GetUserAtrState</code>.
	 * Any actual target lock is terminated by this procedure call. After position, the target is not locked again.
	 * The timeout of this operation is set to 5s, regardless of the general position timeout settings. The positioning
	 * tolerance is depends on the previously set up the fine adjust mode (see <code>AUT_SetFineAdjustMode</code> and
	 * <code>AUT_GetFineAdjustMode)</code>.
	 * Tolerance settings (with <code>AUT_SetTol</code> and <code>AUT_ReadTol</code>) have no influence to this operation. The tolerance
	 * settings as well as the ATR measure precision depends on the instrument's class and the used EDM measure
	 * mode (The EDM measure modes are handled by the subsystem TMC).
	 * @param bDummy        - It's reserved for future use, set bDummy always to <strong>0 = FALSE</strong>
	 * @param dSrchHz [RAD]
	 * @param dSrchV  [RAD]
	 * @return %R1P,0,0:RC
	 * @see AUS_SetUserAtrState
	 * @see AUS_GetUserAtrState
	 *
	 */
	public GCDataPacket AUT_FineAdjust(AUT_FINE_ADJUST fineAdjust) {
		GCDataPacket packet = new GCDataPacket(
				RPC.AUT_FineAdjust,
				fineAdjust.dSrchHz+","+fineAdjust.dSrchV+","+fineAdjust.bDummy.getValue()
		);
		this.doCommand(packet);
		return packet;
	}

	/**
	 * performs an automatically target search within a given area. The search area has a rectangular
	 * shape where the input parameters determine the axis in horizontal and vertical direction. If the search was
	 * successful, the telescope will position to the target in the exactness of the field of vision (1,66gon / 1'30'),
	 * otherwise the instrument turns back to the initial start position. With the ESC key a running search process will
	 * be aborted. The <strong>ATR mode must be enabled for this functionality</strong>, see <code>AUS_SetUserAtrState()</code> and
	 * <code>AUS_GetUserAtrState</code>. For a exact positioning use fine adjust (see <code>AUT_FineAdjust</code>) afterwards.
	 * <em>If you expand the search range of the function AUT_FineAdjust, then you have a target search and a fine
	 * positioning in one function.</em>
	 * @param bDummy         It's reserved for future use, set bDummy always to <strong>0 = FALSE</strong>
	 * @param Hz_Area [RAD]
	 * @param V_Area  [RAD]
	 * @return %R1P,0,0:RC
	 * @see AUS_SetUserAtrState
	 * @see AUS_GetUserAtrState
	 *
	 */
	public GCDataPacket AUT_Search(double Hz_Area, double V_Area) {
		GCDataPacket packet = new GCDataPacket(
				RPC.AUT_Search,
				Hz_Area+","+V_Area+","+BOOLE.FALSE.getValue()
		);
		this.doCommand(packet);
		return packet;
	}

	/**
	 * returns the current activated fine adjust positioning mode. This command is valid for all
	 * instruments, but has only effects for instruments equipped with ATR.
	 *
	 * @return %R1P,0,0:RC,AdjMode[integer]
	 * @see AUT_ADJ_MODE
	 *
	 */
	public GCDataPacket AUT_GetFineAdjustMode() {
		GCDataPacket packet = new GCDataPacket(
				RPC.AUT_GetFineAdjustMode
		);
		this.doCommand(packet);
		return packet;
	}

	/**
	 * sets the positioning tolerances (default values for both modes) relating the angle accuracy or the
	 * point accuracy for the fine adjust. This command is valid for all instruments, but has only effects for instruments
	 * equipped with ATR. If a target is very near or held by hand, it's recommended to set the adjust-mode to
	 * <code>AUT_POINT_MODE</code>
	 * @param AUT_ADJ_MODE
	 * @return %R1P,0,0:RC
	 * @see AUT_ADJ_MODE
	 *
	 */
	public GCDataPacket AUT_SetFineAdjustMode(AUT_ADJ_MODE rAdjMode) {
		GCDataPacket packet = new GCDataPacket(
				RPC.AUT_SetFineAdjustMode,
				String.valueOf(rAdjMode.getValue())
		);
		this.doCommand(packet);
		return packet;
	}

	/**
	 * starts the target tracking. Is at this time another ATR-configuration active, this configuration will be
	 * aborted before. The function can be called several times. If the target is already locked, the command will be
	 * ignored. The <tt>LOCK</tt> mode must be enabled for this functionality, see <code>AUS_SetUserLockState</code> and
	 * <code>AUS_GetUserLockState</code>. The ATR can only lock the target, if it is in the field of view (FoV).
	 *
	 * @return %R1P,0,0:RC
	 * @see AUS_SetUserAtrState
	 * @see AUS_GetUserAtrState
	 * @see MOT_ReadLockStatus
	 *
	 */
	public GCDataPacket AUT_LockIn() {
		GCDataPacket packet = new GCDataPacket(
				RPC.AUT_LockIn
		);
		this.doCommand(packet);
		return packet;
	}

	/**
	 * returns the current position and size of the PowerSearch Window. This command is valid for all
	 * instruments, but has only effects for instruments equipped with PowerSearch.
	 *
	 * @return %R1P,0,0:RC,dCenterHz[double],dCenterV[double],dRangeHz[double],dRangeV[double],bEnabled[Boolean]
	 *
	 */
	public GCDataPacket AUT_GetSearchArea() {
		GCDataPacket packet = new GCDataPacket(
				RPC.AUT_GetSearchArea
		);
		this.doCommand(packet);
		return packet;
	}

	/**
	 * defines the position and dimensions of the PowerSearch window. This command is valid for all
	 * instruments, but has only effects for instruments equipped with PowerSearch.
	 * @param dCenterHz [RAD]
	 * @param dCenterV  [RAD]
	 * @param dRangeHz  [RAD]
	 * @param RangeV    [RAD]
	 * @param bEnabled  0=FALSE or 1=TRUE (active)
	 * @return %R1P,0,0:RC
	 *
	 */
	public GCDataPacket AUT_SetSearchArea(AUT_SEARCH_AREA area) {
		GCDataPacket packet = new GCDataPacket(
				RPC.AUT_SetSearchArea,
				area.dCenterHz+","+area.dCenterV+","+area.dRangeHz+","+area.dRangeV+","+area.bEnabled.getValue()
		);
		this.doCommand(packet);
		return packet;
	}

	/**
	 * returns the current dimension of ATR search window. This command is valid for all instruments,
	 * but has only effects for instruments equipped with ATR.
	 *
	 * @return %R1P,0,0:RC,dRangeHz[double],dRangeV [double]
	 *
	 */
	public GCDataPacket AUT_GetUserSpiral() {
		GCDataPacket packet = new GCDataPacket(
				RPC.AUT_GetUserSpiral
		);
		this.doCommand(packet);
		return packet;
	}

	/**
	 * sets the dimension of the ATR search window. This command is valid for all instruments, but has
	 * only effects for instruments equipped with ATR.
	 *
	 * @param dRangeHz [RAD]
	 * @param dRangeV [RAD]
	 * @return %R1P,0,0:RC
	 *
	 */
	public GCDataPacket AUT_SetUserSpiral(AUT_SEARCH_SPIRAL SpiralDim) {
		GCDataPacket packet = new GCDataPacket(
				RPC.AUT_SetUserSpiral,
				SpiralDim.dRangeHz+","+SpiralDim.dRangeV
		);
		this.doCommand(packet);
		return packet;
	}

	/**
	 * enables / disables the PowerSearch window including the user distance limits for Powersearch,
	 * set by <code>AUT_PS_SetRange</code>
	 * @param enable 1=TRUE or 0=FALSE
	 * @return %R1P,0,0:RC
	 *
	 */
	public GCDataPacket AUT_PS_EnableRange(BOOLE enable) {
		GCDataPacket packet = new GCDataPacket(
				RPC.AUT_PS_EnableRange,
				String.valueOf(enable.getValue())
		);
		this.doCommand(packet);
		return packet;
	}

	/**
	 * defines the distance range for Powersearch and enables the range checking.
	 * These additional limits (additional to the PowerSearch window) will be used as long as the range checking is
	 * enabled (AUT_PS_EnableRange).
	 *
	 * @param lMinDist >= 0
	 * @param lMaxDist <= 400 and lMinDist+10 <= lMaxDist
	 * @return %R1P,0,0:RC
	 *
	 */
	public GCDataPacket AUT_PS_SetRange(int lMinDist, int lMaxDist) {
		GCDataPacket packet = new GCDataPacket(
				RPC.AUT_PS_SetRange,
				lMinDist+","+lMaxDist
		);
		
		if (lMinDist < 0 || lMinDist + 10 <= lMaxDist || lMaxDist >= 400)
			packet.setGRC(GRC.GRC_IVPARAM);
		else
			this.doCommand(packet);
		return packet;
	}

	/**
	 * starts PowerSearch. It searches inside of the given PowerSearch window, defined by
	 * <code>AUT_SetWorkingArea</code> and optional by <code>AUT_PS_SetRange</code>
	 *
	 * @return %R1P,0,0:RC
	 *
	 */
	public GCDataPacket AUT_PS_SearchWindow() {
		GCDataPacket packet = new GCDataPacket(
				RPC.AUT_PS_SearchWindow
		);
		this.doCommand(packet);
		return packet;
	}

	/**
	 * searches the next target using Powersearch
	 * @param lDirection  Defines the searching direction
	 *                    (AUT_CLOCKWISE = 1 or
	 *                    AUT_ANTICLOCKWISE = -1)
	 * @param bSwing      TRUE=1: Searching starts -10 gon to the
	 *                    given direction lDirection. This setting
	 *                    finds targets close to the telescope
	 *                    direction faster
	 * @return %R1P,0,0:RC
	 *
	 */
	public GCDataPacket AUT_PS_SearchNext(AUT_ROTATION_DIRECTION lDirection, BOOLE bSwing) {
		GCDataPacket packet = new GCDataPacket(
				RPC.AUT_PS_SearchNext,
				lDirection.getValue()+","+bSwing.getValue()
		);
		this.doCommand(packet);
		return packet;
	}
	
	/***********************************************/
	/*                                             */
	/*    ELECTRONIC DISTANCE MEASUREMENT - EDM    */
	/*                                             */
	/***********************************************/

	/**
	 * Switch on/off laserpointer
	 * @param OnOff On=1 or Off=0
	 * @return %R1P,0,0:RC
	 *
	 */
	public GCDataPacket EDM_Laserpointer(ON_OFF_TYPE OnOff) {
		GCDataPacket packet = new GCDataPacket(
				RPC.EDM_Laserpointer,
				String.valueOf(OnOff.getValue())
		);
		this.doCommand(packet);
		return packet;
	}

	/**
	 * Get value of intensity of guide light
	 *
	 * @return %R1Q,0,0:RC,EDM_EGLINTENSITY_TYPE[long]
	 * @see EDM_EGLINTENSITY_TYPE
	 *
	 */
	public GCDataPacket EDM_GetEglIntensity() {
		GCDataPacket packet = new GCDataPacket(
				RPC.EDM_GetEglIntensity
		);
		this.doCommand(packet);
		return packet;
	}

	/**
	 * Set value of intensity of guide light
	 * @param EDM_EGLINTENSITY_PRG
	 * @return %R1P,0,0:RC
	 * @see EDM_EGLINTENSITY_TYPE
	 *
	 */
	public GCDataPacket EDM_SetEglIntensity(EDM_EGLINTENSITY_TYPE eIntensity) {
		GCDataPacket packet = new GCDataPacket(
				RPC.EDM_SetEglIntensity,
				String.valueOf(eIntensity.getValue())
		);
		this.doCommand(packet);
		return packet;
	}
	
	/**
	 * This command checks if the continuous measurement is active.
	 * 
	 * @param eMeasType
	 * @return %R1P,0,0:RC,rbActive
	 */
	public GCDataPacket EDM_IsContMeasActive (EDM_MEASUREMENT_TYPE eMeasType) {
		GCDataPacket packet = new GCDataPacket(
				RPC.EDM_IsContMeasActive,
				String.valueOf(eMeasType.getValue())
		);
		this.doCommand(packet);
		return packet;
	}
	
	/**
	 * This command enables or disables the boomerang filter.
	 * @param eOnOff
	 * @return %R1P,0,0:RC
	 */
	public GCDataPacket EDM_SetBoomerangFilter (ON_OFF_TYPE eOnOff) {
		GCDataPacket packet = new GCDataPacket(
				RPC.EDM_SetBoomerangFilter,
				String.valueOf(eOnOff.getValue())
		);
		this.doCommand(packet);
		return packet;
	}
	
	  
	/****************************/
	/*                          */
	/*    MOTORISATION - MOT    */
	/*                          */
	/****************************/

	/**
	 * returns the current condition of the LockIn control (see subsystem AUT for further information).
	 *
	 * @return %R1P,0,0:RC,Status[long]
	 * @see MOT_LOCK_STATUS
	 *
	 */
	public GCDataPacket MOT_ReadLockStatus() {
		GCDataPacket packet = new GCDataPacket(
				RPC.MOT_ReadLockStatus
		);
		this.doCommand(packet);
		return packet;
	}

	/**
	 * returns the current condition of the LockIn control (see subsystem AUT for further information).
	 *
	 * @return %R1P,0,0:RC
	 * @see MOT_MODE
	 *
	 */
	public GCDataPacket MOT_StartController(MOT_MODE ControlMode) {
		GCDataPacket packet = new GCDataPacket(
				RPC.MOT_StartController,
				String.valueOf(ControlMode.getValue())
		);
		this.doCommand(packet);
		return packet;
	}

	/**
	 * used to stop movement and to stop the motor controller operation.
	 *
	 * @return %R1P,0,0:RC
	 * @see MOT_STOP_MODE
	 *
	 */
	public GCDataPacket MOT_StopController(MOT_STOP_MODE Mode) {
		GCDataPacket packet = new GCDataPacket(
				RPC.MOT_StopController,
				String.valueOf(Mode.getValue())
		);
		this.doCommand(packet);
		return packet;
	}

	/**
	 * used to set up the velocity of motorization. This function is valid only if
	 * <code>MOT_StartController(MOT_OCONST)</code> has been called previously.
	 * <code>HZSpeed</code> denotes the horizontal and
	 * <code>VSpeed</code> denotes the vertical velocity setting.
	 * The speed in horizontal and vertical direction in rad/s. The maximum speed
	 * is +/- 0.79 rad/s each.
	 * @param HZSpeed [RAD/SEC]
	 * @param VSpeed  [RAD/SEC]
	 * @return %R1P,0,0:RC
	 * @see MOT_MODE
	 *
	 */
	public GCDataPacket MOT_SetVelocity(MOT_COM_PAIR RefOmega) {
		GCDataPacket packet = new GCDataPacket(
				RPC.MOT_SetVelocity,
				RefOmega.HZSpeed+","+RefOmega.VSpeed
		);
		this.doCommand(packet);
		return packet;
	}	
	
	/********************************/
	/*                              */
	/*    CENTRAL SERVICES - CSV    */
	/*                              */
	/********************************/

	/**
	 * Get factory defined instrument number
	 *
	 * @return %R1P,0,0:RC,SerialNo[long]
	 *
	 */
	public GCDataPacket CSV_GetInstrumentNo() {
		GCDataPacket packet = new GCDataPacket(
				RPC.CSV_GetInstrumentNo
		);
		this.doCommand(packet);
		return packet;
	}

	/**
	 * Get Leica specific instrument name
	 * <em>Notice: exploit the GCDataPacket as String (see getParameterStr)!</em>
	 * @return %R1P,0,0:RC,Name[string]
	 * @see GCDataPacket
	 * @see GCDataPacket #getParameterStr
	 *
	 */
	public GCDataPacket CSV_GetInstrumentName() {
		GCDataPacket packet = new GCDataPacket(
				RPC.CSV_GetInstrumentName
		);
		this.doCommand(packet);
		return packet;
	}

	/**
	 * Get instrument configuration
	 * @return %R1P,0,0:RC,DevicePrecisionClass[long],DeviceConfigurationType[long]
	 * @see TPS_DEVICE_TYPE
	 * @see TPS_DEVICE_CLASS
	 *
	 */
	public GCDataPacket CSV_GetDeviceConfig() {
		GCDataPacket packet = new GCDataPacket(
				RPC.CSV_GetDeviceConfig
		);

		this.doCommand(packet);
		return packet;
	}

	/**
	 * returns information about the reflectorless and long range distance
	 * measurement (RL) of the instrument.
	 * @return %R1P,0,0:RC,reRefLessClass[long]
	 * @see TPS_REFLESS_CLASS
	 * 
	 */
	public GCDataPacket CSV_GetReflectorlessClass() {
		GCDataPacket packet = new GCDataPacket(
				RPC.CSV_GetReflectorlessClass
		);

		this.doCommand(packet);
		return packet;
	}

	/**
	 * Get date and time.
	 * The ASCII response is formatted corresponding to the data type DATIME. A possible response can look like
	 * this: %R1P,0,0:0,1996,7,19,10,13,2
	 * @return %R1P,0,0:RC,Year,Month,Day,Hour,Minute,Second
	 *
	 */
	public GCDataPacket CSV_GetDateTime() {
		GCDataPacket packet = new GCDataPacket(
				RPC.CSV_GetDateTime
		);

		this.doCommand(packet);
		return packet;
	}

	/**
	 * Set date and time.
	 * It is not possible to set invalid date or time. See data type description of DATIME for valid date and time.
	 * @param year
	 * @param month
	 * @param date
	 * @param hour
	 * @param minute
	 * @param secound
	 * @return %R1P,0,0:RC
	 *
	 */
	public GCDataPacket CSV_SetDateTime(DATIME DateAndTime) {
		// Integer.toHexString
		GCDataPacket packet = new GCDataPacket(
				RPC.CSV_SetDateTime,
				DateAndTime.Date.Year   + "," +
				DateAndTime.Date.Month  + "," +
				DateAndTime.Date.Day    + "," +
				DateAndTime.Time.Hour   + "," +
				DateAndTime.Time.Minute + "," +
				DateAndTime.Time.Second
		);

		this.doCommand(packet);
		return packet;
	}

	/**
	 * Returns the system software version.
	 * @return %R1P,0,0:RC,nRelease,nVersion,nSubVersion[all short]
	 *
	 */
	public GCDataPacket CSV_GetSWVersion() {
		GCDataPacket packet = new GCDataPacket(
				RPC.CSV_GetSWVersion
		);

		this.doCommand(packet);
		return packet;
	}

	/**
	 * returns the capacity of the current power source and its source (internal or external).
	 * unCapacity out Actual capacity [%]
	 * eActivePower out Actual power source [CSV_POWER_PATH]
	 * ePowerSuggest out Not supported. [CSV_POWER_PATH]
	 * @return %R1P,0,0:RC,unCapacity[long],eActivePower[long],ePowerSuggest[long]
	 * @see CSV_POWER_PATH
	 *
	 */
	public GCDataPacket CSV_CheckPower() {
		GCDataPacket packet = new GCDataPacket(
				RPC.CSV_CheckPower
		);

		this.doCommand(packet);
		return packet;
	}

	/**
	 * Get the internal temperature of the instrument, measured on the Mainboard side.
	 * Values are reported in degrees Celsius.
	 * @return %R1P,0,0:RC,Temp[long]
	 *
	 */
	public GCDataPacket CSV_GetIntTemp() {
		GCDataPacket packet = new GCDataPacket(
				RPC.CSV_GetIntTemp
		);

		this.doCommand(packet);
		return packet;
	}

	/**
	 * Gets the current date and time of the instrument
	 * @return %R1P,0,0:RC,Year,Month,Day,Hour,Minute,Second,CentiSecond[all short]
	 */
	public GCDataPacket CSV_GetDateTimeCentiSec() {
		GCDataPacket packet = new GCDataPacket(
				RPC.CSV_GetDateTimeCentiSec
		);

		this.doCommand(packet);
		return packet;
	}
	
	/**
	 * This command gets the status of the charger.
	 * 
	 * @return %R1P,0,0:RC,rbOn
	 */
	public GCDataPacket CSV_GetCharging () {
		GCDataPacket packet = new GCDataPacket(
				RPC.CSV_GetCharging
		);

		this.doCommand(packet);
		return packet;
	}
	
	/**
	 * Get the intensity of the Laserlot. (Intensity from 0 to 100.)
	 * @return %R1P,0,0:RC,nIntens
	 */
	public GCDataPacket CSV_GetLaserlotIntens () {
		GCDataPacket packet = new GCDataPacket(
				RPC.CSV_GetLaserlotIntens
		);

		this.doCommand(packet);
		return packet;
	}
	
	/**
	 * Get the status if the Laserlot is turned on or off.
	 * @return %R1P,0,0:RC,eOnOff
	 */
	public GCDataPacket CSV_GetLaserlotStatus () {
		GCDataPacket packet = new GCDataPacket(
				RPC.CSV_GetLaserlotStatus
		);

		this.doCommand(packet);
		return packet;
	}
	
	/**
	 * This command gets the preferred power source.
	 *   0: External
	 *   2: Internal
	 * @return %R1P,0,0:RC,reBattery
	 */
	public GCDataPacket CSV_GetPreferredPowerSource () {
		GCDataPacket packet = new GCDataPacket(
				RPC.CSV_GetPreferredPowerSource
		);

		this.doCommand(packet);
		return packet;
	}
	
	/**
	 * This command checks if the startup message mode is enabled or not.
	 * @return %R1P,0,0:RC,rbOn
	 */
	public GCDataPacket CSV_GetStartUpMessageMode () {
		GCDataPacket packet = new GCDataPacket(
				RPC.CSV_GetStartUpMessageMode
		);

		this.doCommand(packet);
		return packet;
	}
	
	/**
	 * Get the actual voltage of the instrument in millivolt. Instrument voltage [mV]
	 * @return %R1P,0,0:RC,milliVolt
	 */
	public GCDataPacket CSV_GetVoltage () {
		GCDataPacket packet = new GCDataPacket(
				RPC.CSV_GetVoltage
		);

		this.doCommand(packet);
		return packet;
	}
	
	/**
	 * This command turns the charger On or Off.
	 * @param bOn
	 * @return %R1P,0,0:RC
	 */
	public GCDataPacket CSV_SetCharging (ON_OFF_TYPE bOn) {
		GCDataPacket packet = new GCDataPacket(
				RPC.CSV_SetCharging,
				String.valueOf(bOn.getValue())
		);

		this.doCommand(packet);
		return packet;
	}
	
	/**
	 * This command sets the intensity of the Laserlot.
	 * @param nIntens Intensity from 0 to 100.
	 * @return %R1P,0,0:RC
	 */
	public GCDataPacket CSV_SetLaserlotIntens (int nIntens) {
		nIntens = Math.max(0, Math.min(100, nIntens));
		GCDataPacket packet = new GCDataPacket(
				RPC.CSV_SetLaserlotIntens,
				String.valueOf(nIntens)
		);

		this.doCommand(packet);
		return packet;
	}
	
	/**
	 * This command sets the preferred power source.
	 *   0: External
	 *   2: Internal
	 *   
	 * @param eBattery
	 * @return %R1P,0,0:RC
	 */
	public GCDataPacket CSV_SetPreferredPowerSource (CSV_BATTERY eBattery) {
		GCDataPacket packet = new GCDataPacket(
				RPC.CSV_SetPreferredPowerSource,
				String.valueOf(eBattery.getValue())
		);

		this.doCommand(packet);
		return packet;
	}
	
	/**
	 * This command allows you to enable the 'startup message'. This is primarily 
	 * useful for scripted connections to the device over serial. When the system 
	 * is up and running, a predefined string is sent to signalize that everything
	 * is ready.
	 * 
	 * @param bOn
	 * @return %R1P,0,0:RC
	 */
	public GCDataPacket CSV_SetStartUpMessageMode (ON_OFF_TYPE bOn) {
		GCDataPacket packet = new GCDataPacket(
				RPC.CSV_SetStartUpMessageMode,
				String.valueOf(bOn.getValue())
		);

		this.doCommand(packet);
		return packet;
	}
	
	/**
	 * This command turns the Laserlot on or off.
	 * @param eOnOff
	 * @return %R1P,0,0:RC
	 */
	public GCDataPacket CSV_SwitchLaserlot (ON_OFF_TYPE eOnOff) {
		GCDataPacket packet = new GCDataPacket(
				RPC.CSV_SwitchLaserlot,
				String.valueOf(eOnOff.getValue())
		);

		this.doCommand(packet);
		return packet;
	}
	
	
	
	
	/**************************/
	/*                        */
	/*    SUPERVISOR - SUP    */
	/*                        */
	/**************************/

	/**
	 * returned settings are power off configuration and timeout [ms].
	 * @return %R1P,0,0:RC,Reserved[long],AutoPower[long],Timeout[long]
	 * @see SUP_AUTO_POWER
	 *
	 */
	public GCDataPacket SUP_GetConfig() {
		GCDataPacket packet = new GCDataPacket(
				RPC.SUP_GetConfig
		);

		this.doCommand(packet);
		return packet;
	}

	/**
	 * Set power management configuration
	 * Set the auto power off automatic AUTO_POWER_DISABLED or AUTO_POWER_OFF and the corresponding
	 * timeout[ms] for the auto power off automatic.
	 * @param Reserved On=1 or off=0
	 * @param AutoPower - see SUP_AUTO_POWER
	 * @param TimeOut [ms]  The timeout in ms. After this time the device switches in the mode defined by the
	 *                      value of AutoPower when no user activity (press a key, turn the device or
	 *                      communication via GeoCOM) occurs. The default value for Timeout is 900000ms = 15 Min.
	 * @return %R1P,0,0:RC
	 * @see SUP_AUTO_POWER
	 *
	 */
	public GCDataPacket SUP_SetConfig(SUP_POWER_MANAGEMENT_CONFIG config) {
		GCDataPacket packet = new GCDataPacket(
				RPC.SUP_SetConfig,
				config.Reserved+","+config.AutoPower+","+config.Timeout
		);

		this.doCommand(packet);
		return packet;
	}
	
	/**
	 * Configure the instrument to automatically restart when power is 
	 * available and the instrument has not been shutdown regularly before.
	 * 
	 * @param bAutoRestart
	 * @return %R1P,0,0:RC
	 */
	public GCDataPacket SUP_SetPowerFailAutoRestart(BOOLE bAutoRestart) {
		GCDataPacket packet = new GCDataPacket(
				RPC.SUP_SetPowerFailAutoRestart,
				String.valueOf(bAutoRestart.getValue())
		);

		this.doCommand(packet);
		return packet;
	}

	/*******************************************/
	/*                                         */
	/*    BASIC MAN MACHINE INTERFACE - BMM    */
	/*                                         */
	/*******************************************/

	/**
	 * produces a triple beep with the configured intensity and frequency, which cannot be changed. If
	 * there is a continuous signal active, it will be stopped before.
	 * @return %R1P,0,0:RC
	 *
	 */
	public GCDataPacket BMM_BeepAlarm() {
		GCDataPacket packet = new GCDataPacket(
				RPC.BMM_BeepAlarm
		);

		this.doCommand(packet);
		return packet;
	}

	/**
	 * produces a single beep with the configured intensity and frequency, which cannot be changed. If a
	 * continuous signal is active, it will be stopped first.
	 * @return %R1P,0,0:RC
	 *
	 */
	public GCDataPacket BMM_BeepNormal() {
		GCDataPacket packet = new GCDataPacket(
				RPC.BMM_BeepNormal
		);

		this.doCommand(packet);
		return packet;
	}

	/**
	 * switches on the beep-signal with the intensity nIntens. If a continuous signal is active, it will be
	 * stopped first. Turn off the beeping device with <code>IOS_BeepOff</code>.
	 * @param Volumen [%]
	 * @return %R1P,0,0:RC
	 *
	 */
	public GCDataPacket IOS_BeepOn(int Volumen) {
		GCDataPacket packet = new GCDataPacket(
				RPC.IOS_BeepOn,
				String.valueOf(Volumen)
		);

		this.doCommand(packet);
		return packet;
	}

	/**
	 * switches on the beep-signal with the intensity nIntens. If a continuous signal is active, it will be
	 * stopped first. Turn off the beeping device with <code>IOS_BeepOff</code>.
	 * @param Volumen [%]
	 * @return %R1P,0,0:RC
	 *
	 */
	public GCDataPacket IOS_BeepOff() {
		GCDataPacket packet = new GCDataPacket(
				RPC.IOS_BeepOff
		);

		this.doCommand(packet);
		return packet;
	}	


	/**********************************/
	/*                                */
	/*    BASIC APPLICATIONS - BAP    */
	/*                                */
	/**********************************/

	/**
	 * Gets the current target type for distance measurements (with reflector or without reflector).
	 * @return %R1Q,0,0:RC,BAP_TARGET_TYPE[long]
	 * @see BAP_TARGET_TYPE
	 *
	 */
	public GCDataPacket BAP_GetTargetType() {
		GCDataPacket packet = new GCDataPacket(
				RPC.BAP_GetTargetType
		);

		this.doCommand(packet);
		return packet;
	}

	/**
	 * Defines the target type, with reflector or reflector-free
	 * If the actual distance measurement not valid for the set target type, then the measurement program will be
	 * changed to the last used one for this type.
	 * <code>BAP_SetMeasPrg</code> can also change the target type.
	 * Reflector-free measurement programs are not available on all instrument types.
	 * @return %R1Q,0,0:RC
	 * @see BAP_TARGET_TYPE
	 *
	 */
	public GCDataPacket BAP_SetTargetType(BAP_TARGET_TYPE TargetType) {
		GCDataPacket packet = new GCDataPacket(
				RPC.BAP_SetTargetType,
				String.valueOf(TargetType.getValue())
		);

		this.doCommand(packet);
		return packet;
	}

	/**
	 * Gets the current prism type.
	 * @return %R1Q,0,0:RC,ePrismType[long]
	 * @see BAP_PRISM_TYPE
	 *
	 */
	public GCDataPacket BAP_GetPrismType() {
		GCDataPacket packet = new GCDataPacket(
				RPC.BAP_GetPrismType
		);

		this.doCommand(packet);
		return packet;
	}

	/**
	 * Sets the prism type for measurements with a reflector. <strong>It overwrites the prism constant</strong>, set by
	 * <code>TMC_SetPrismCorr</code>.
	 * @return %R1Q,0,0:RC
	 * @see BAP_PRISM_TYPE
	 *
	 */
	public GCDataPacket BAP_SetPrismType(BAP_PRISM_TYPE PrismType) {
		GCDataPacket packet = new GCDataPacket(
				RPC.BAP_SetPrismType,
				String.valueOf(PrismType.getValue())
		);

		this.doCommand(packet);
		return packet;
	}

	/**
	 * Gets the current prism type and name.
	 * @return %R1Q,0,0:RC,ePrismType[long],szPrismName[string]
	 * @see BAP_PRISM_TYPE
	 *
	 */
	public GCDataPacket BAP_GetPrismType2() {
		GCDataPacket packet = new GCDataPacket(
				RPC.BAP_GetPrismType2
		);

		this.doCommand(packet);
		return packet;
	}

	/**
	 * Sets the prism type for measurements with a reflector.
	 * It overwrites the prism constant, set by <code>TMC_SetPrismCorr</code>.
	 * Prism name: Required if prism type is <code>BAP_PRISM_USE</code>
	 * @return %R1Q,0,0:RC,
	 * @see BAP_PRISM_TYPE
	 *
	 */
	public GCDataPacket BAP_SetPrismType2(BAP_PRISM_TYPE_AND_NAME prism) {
		GCDataPacket packet = new GCDataPacket(
				RPC.BAP_SetPrismType2,
				prism.rePrismType.getValue()+",\""+prism.szPrismName+"\""
		);

		this.doCommand(packet);
		return packet;
	}

	/**
	 * Get the definition of a prism.
	 * @param PrismType
	 * @return %R1Q,0,0:RC,Name[String],dAddConst[double],eReflType[long]
	 * @see BAP_PRISM_TYPE
	 *
	 */
	public GCDataPacket BAP_GetPrismDef(BAP_PRISM_TYPE PrismType) {
		GCDataPacket packet = new GCDataPacket(
				RPC.BAP_GetPrismDef,
				String.valueOf(PrismType.getValue())
		);

		this.doCommand(packet);
		return packet;
	}

	/**
	 * Get definition of an user prism.
	 * @param PrismName
	 * @return %R1P,0,0:RC,rdAddConst[double],reReflType[long],szCreator[String]
	 *
	 */
	public GCDataPacket BAP_GetUserPrismDef(String PrismName) {
		GCDataPacket packet = new GCDataPacket(
				RPC.BAP_GetUserPrismDef,
				"\""+PrismName+"\""
		);

		this.doCommand(packet);
		return packet;
	}

	/**
	 * Sets a user prism definition.
	 * @param PrismName
	 * @param AddConst  [m]
	 * @param ReflType
	 * @param Creator
	 * @return %R1P,0,0:RC
	 * @see BAP_REFL_TYPE
	 *
	 */
	public GCDataPacket BAP_SetUserPrismDef(BAP_PRISMDEF prism) {
		GCDataPacket packet = new GCDataPacket(
				RPC.BAP_SetUserPrismDef,
				"\""+prism.szPrismName+"\","+prism.dAddConst+","+prism.eReflType.getValue()+",\""+prism.szCreator+"\""
		);

		this.doCommand(packet);
		return packet;
	}

	/**
	 * Get actual distance measurement program.
	 * @return %R1Q,0,0:RC,MeasPrg[long]
	 * @see BAP_USER_MEASPRG
	 *
	 */
	public GCDataPacket BAP_GetMeasPrg() {
		GCDataPacket packet = new GCDataPacket(
				RPC.BAP_GetMeasPrg
		);

		this.doCommand(packet);
		return packet;
	}

	/**
	 * Defines the distance measurement program i.e. for <code>BAP_MeasDistanceAngle</code>
	 * Reflector-free measurement programs are not available on all instrument types.
	 * Changing the measurement programs may change the target type too (with reflector / reflector-free)
	 *
	 * @param MeasPrg
	 * @return %R1Q,0,0:RC
	 * @see BAP_USER_MEASPRG
	 *
	 */
	public GCDataPacket BAP_SetMeasPrg(BAP_USER_MEASPRG MeasPrg) {
		GCDataPacket packet = new GCDataPacket(
				RPC.BAP_SetMeasPrg,
				String.valueOf(MeasPrg.getValue())
		);

		this.doCommand(packet);
		return packet;
	}

	/**
	 * This function measures distances and angles depending on the mode <code>DistMode</code> and updates the internal data
	 * pool after correct measurements. It controls the special beep (sector or lost lock), maintains measurement icons
	 * and disables the "FNC"-key during tracking.
	 *
	 * @param DistMode
	 * @return %R1P,0,0:RC, dHz[double], dV[double], dDist[double],DistMode[long]
	 * @see BAP_MEASURE_PRG
	 *
	 */
	public GCDataPacket BAP_MeasDistanceAngle(BAP_MEASURE_PRG DistMode) {
		GCDataPacket packet = new GCDataPacket(
				RPC.BAP_MeasDistanceAngle,
				String.valueOf(DistMode.getValue())
		);

		this.doCommand(packet);
		return packet;
	}

	/**
	 * Function searches a target. The used searching range depends on the defined ATR search window. The
	 * functionality is only available for ATR instruments.
	 *
	 * @param bDummy  It's reserved for future use, set <code>bDummy</code> always to <code>FALSE=0</code>
	 * @return %R1P,0,0:RC
	 * @see AUT_GetUserSpiral
	 * @see AUT_SetUserSpiral
	 * @see AUT_GetSearchArea
	 * @see AUT_SetSearchArea
	 *
	 */
	public GCDataPacket BAP_SearchTarget() {
		GCDataPacket packet = new GCDataPacket(
				RPC.BAP_SearchTarget,
				String.valueOf(BOOLE.FALSE.getValue())
		);

		this.doCommand(packet);
		return packet;
	}
	
	/**
	 * getting the current ATR low vis mode.
	 *
	 * @return %R1Q,0,0:RC,reATRSetting[long]
	 * @see BAP_ATRSETTING
	 *
	 */
	public GCDataPacket BAP_GetATRSetting() {
		GCDataPacket packet = new GCDataPacket(
				RPC.BAP_GetATRSetting,
				String.valueOf(BOOLE.FALSE.getValue())
		);

		this.doCommand(packet);
		return packet;
	}
	
	/**
	 * setting the current ATR low vis mode
	 *
	 * @return %R1P,0,0:RC
	 * @see BAP_ATRSETTING
	 *
	 */
	public GCDataPacket BAP_SetATRSetting(BAP_ATRSETTING reATRSetting) {
		GCDataPacket packet = new GCDataPacket(
				RPC.BAP_SetATRSetting,
				String.valueOf(reATRSetting.getValue())
		);

		this.doCommand(packet);
		return packet;
	}
	
	/**
	 * getting the reduced ATR field of view.
	 *
	 * @return %R1Q,0,0:RC, reRedFov[long]
	 * @see ON_OFF_TYPE
	 *
	 */
	public GCDataPacket BAP_GetRedATRFov() {
		GCDataPacket packet = new GCDataPacket(
				RPC.BAP_GetRedATRFov
		);

		this.doCommand(packet);
		return packet;
	}
	
	/**
	 * setting the reduced ATR field of view.
	 *
	 * @return %R1Q,0,0:RC
	 * @see ON_OFF_TYPE
	 *
	 */
	public GCDataPacket BAP_SetRedATRFov(ON_OFF_TYPE OnOff) {
		GCDataPacket packet = new GCDataPacket(
				RPC.BAP_SetRedATRFov,
				String.valueOf(OnOff.getValue())
		);

		this.doCommand(packet);
		return packet;
	}
	
	/**
	 * Get the information if precise ATR mode is On / Off. Precise ATR 
	 * is just available for Instrument with a precision of 0.5".
	 *   1: ATR precise mode is on.
	 *   0: ATR precise mode is off.
	 * 
	 * @return %R1Q,0,0:RC,eAtrPrecise
	 */
	public GCDataPacket BAP_GetATRPrecise() {
		GCDataPacket packet = new GCDataPacket(
				RPC.BAP_GetATRPrecise
		);

		this.doCommand(packet);
		return packet;
	}
	
	/**
	 * Set precise ATR mode to On / Off. Precise ATR is just available 
	 * for Instrument with a precision of 0.5".
	 *   1: ATR precise mode is on.
	 *   0: ATR precise mode is off.
	 * 
	 * @param eAtrPrecise
	 * @return %R1Q,0,0:RC
	 */
	public GCDataPacket BAP_SetATRPrecise(ON_OFF_TYPE eAtrPrecise) {
		GCDataPacket packet = new GCDataPacket(
				RPC.BAP_SetATRPrecise,
				String.valueOf(eAtrPrecise.getValue())
		);

		this.doCommand(packet);
		return packet;
	}
	
	
	/************************************* KEYBOARD DISPLAY UNIT - KDM *********************************\
	
	/**
	 * Set the display power on or off. The display will be switched off after one minute 
	 * like a screensaver. See also WinCE/Control Panel/Power Properties: 'Switch state to user idle'
	 * @param bOn
	 * @return %R1P,0,0:RC
	 */
	public GCDataPacket KDM_SetLcdPower(BOOLE bOn) {
		GCDataPacket packet = new GCDataPacket(
				RPC.KDM_SetLcdPower,
				String.valueOf(bOn.getValue())
		);

		this.doCommand(packet);
		return packet;
	}
	
	/**
	 * Get the status for the display power:
	 *   0: Display in screensaver mode
	 *   1: Display is on
	 *   
	 * @return %R1P,0,0:RC,rbIsOn
	 */
	public GCDataPacket KDM_GetLcdPower() {
		GCDataPacket packet = new GCDataPacket(
				RPC.KDM_GetLcdPower
		);

		this.doCommand(packet);
		return packet;
	}
	
	
	/************************* FILE TRANSFER - FTR *******************************/
	
	/**
	 * This command sets up the device, file type and search path. It 
	 * has to be called before command FTR_List can be used.
	 * 
	 * Search path. Optional. Must be specified if file type is 
	 *  FTR_FILE_UNKNOWN. 
	 * Can be used with *.* to list also job folders for file type 
	 *  FTR_FILE_POINTRELATEDDB.
	 * 
	 * @param eDeviceType
	 * @param eFileType
	 * @param szSearchPath
	 * @return %R1P,0,0:RC
	 */
	public GCDataPacket FTR_SetupList(FTR_DEVICETYPE eDeviceType, FTR_FILETYPE eFileType, String szSearchPath) {
		GCDataPacket packet = new GCDataPacket(
				RPC.FTR_SetupList,
				String.valueOf(eDeviceType.getValue()) + "," + String.valueOf(eFileType.getValue()) + "," + szSearchPath
		);

		this.doCommand(packet);
		return packet;
	}
	
	/**
	 * This command gets one single file entry. The command FTR_SetupList has to be called first.
	 * 
	 * @param bNext
	 * @return %R1P,0,0:RC,rbLast,szFileName,ulFileSize,ucHour,ucMinute,ucSecond,ucCentisecond,ucDay,ucMonth,ucYear
	 */
	public GCDataPacket FTR_List(BOOLE bNext) {
		GCDataPacket packet = new GCDataPacket(
				RPC.FTR_List,
				String.valueOf(bNext.getValue())
		);

		this.doCommand(packet);
		return packet;
	}
	
	/**
	 * This command aborts or ends file list command.
	 * @return %R1P,0,0:RC
	 */
	public GCDataPacket FTR_AbortList() {
		GCDataPacket packet = new GCDataPacket(
				RPC.FTR_AbortList
		);

		this.doCommand(packet);
		return packet;
	}
	
	/**
	 * This command sets up the download of a file from the instrument. It has to be called before 
	 * command FTR_Download can be used.
	 * File name with extension. If file type is FTR_FILE_UNKNOWN additional file path is required.
	 * Block size. Max value is FTR_MAX_BLOCKSIZE.
	 * 
	 * @param eDeviceType
	 * @param eFileType
	 * @param szFileNameSrc File name with extension. If file type is FTR_FILE_UNKNOWN additional file path is required.
	 * @param unBlockSize Block size. Max value is FTR_MAX_BLOCKSIZE.
	 * @return %R1P,0,0:RC,unNumOfBlocks  Number of blocks required to upload the file.
	 */
	public GCDataPacket FTR_SetupDownload(FTR_DEVICETYPE eDeviceType, FTR_FILETYPE eFileType, String szFileNameSrc, int unBlockSize) {
		// %R1Q,23303:eDeviceType,eFileType,szFileNameSrc,unBlockSize
		GCDataPacket packet = new GCDataPacket(
				RPC.TR_SetupDownload,
				String.valueOf(eDeviceType.getValue()) + "," + 
				String.valueOf(eFileType.getValue()) + "," +
				"\"" + szFileNameSrc + "\"," + 
				String.valueOf(unBlockSize)
		);

		this.doCommand(packet);
		return packet;
	}

	
	/**
	 * This command sets up the download fir large files from the instrument. 
	 * It has to be called before command FTR_DownloadXL can be used.
	 * 
	 * File name with extension. If file type is FTR_FILE_UNKNOWN additional file path is required.
	 * Block size. Max value is FTR_MAX_BLOCKSIZE_LARGE.
	 * 
	 * @param eDeviceType
	 * @param eFileType
	 * @param szFileNameSrc File name with extension. If file type is FTR_FILE_UNKNOWN additional file path is required.
	 * @param unBlockSize Block size. Max value is FTR_MAX_BLOCKSIZE_LARGE.
	 * @return %R1P,0,0:RC,rulNumOfBlocks
	 */
	public GCDataPacket FTR_SetupDownloadLarge(FTR_DEVICETYPE eDeviceType, FTR_FILETYPE eFileType, String szFileNameSrc, int unBlockSize) {
		GCDataPacket packet = new GCDataPacket(
				RPC.FTR_SetupDownloadLarge,
				String.valueOf(eDeviceType.getValue()) + "," + 
				String.valueOf(eFileType.getValue()) + "," +
				"\"" + szFileNameSrc + "\"," + 
				String.valueOf(unBlockSize)
		);

		this.doCommand(packet);
		return packet;
	}
	
	/**
	 * This command gets one single block of data. The command FTR_SetupDownload has to be called first.
	 * 
	 * Note: The maximum block number in C/VB is 65535/32767 therefore the file size is limited to 28MB/14MB.
	 * 
	 * @param unBlockNumber Blocknumber. The block number starts with 1. If block number is 0 then the download process is aborted
	 * @param timeOut for serial support
	 * @return %R1P,0,0:RC,FTR_BLOCK_val
	 */
	public GCDataPacket FTR_Download(int unBlockNumber, long timeOut) {
		GCDataPacket packet = new GCDataPacket(
				RPC.FTR_Download,
				String.valueOf(unBlockNumber),
				timeOut
		);

		this.doCommand(packet);
		return packet;
	}
	
	/**
	 * This command gets one single block of data. The command FTR_SetupDownloadLarge has to be called first.
	 * 
	 * Note: The maximum block number in C/VB is 65535/32767 therefore the file size is limited to 112MB/56MB.
	 * 
	 * 
	 * FileOutputStream output = new FileOutputStream("image.jpg", true);
	 *		try {
	 *			int unBlockNumber=1;
	 *			int len = 0;
	 *			do {
	 *				packet = geoCOM.FTR_DownloadXL(unBlockNumber, 60000);
	 *				if (!JGeoCOM.checkGRC(packet, FTR_BLOCK_LARGE.class)) {
	 *					System.err.println("Fehler, beim Uebertragen der Daten! " + packet.getGRC());
	 *					return;
	 *				}
     *	
	 *				FTR_BLOCK_LARGE block = (FTR_BLOCK_LARGE)packet.getOnAnswerArgument();
	 *				len = block.FTR_BLOCK_LARGE_len;
	 *				output.write(block.FTR_BLOCK_LARGE_val, 0, block.FTR_BLOCK_LARGE_len);
	 *				unBlockNumber++;
	 *			} 
	 *			while (len == maxBlockSize);
     *	
	 *		} finally {
	 *			output.close();
	 *		}
	 * 
	 * 
	 * @param ulBlockNumber Blocknumber. The block number starts with 1. If block number is 0 then the download process is aborted
	 * @param timeOut for serial support
	 * @return %R1P,0,0:RC,FTR_BLOCK_LARGE_val
	 */
	public GCDataPacket FTR_DownloadXL(long ulBlockNumber, long timeOut) {
		GCDataPacket packet = new GCDataPacket(
				RPC.FTR_DownloadXL,
				String.valueOf(ulBlockNumber),
				timeOut
		);

		this.doCommand(packet);
		return packet;
	}
		
	/**
	 * This command aborts or ends file download command.
	 * @return %R1P,0,0:RC
	 */
	public GCDataPacket FTR_AbortDownload() {
		GCDataPacket packet = new GCDataPacket(
				RPC.FTR_AbortDownload
		);

		this.doCommand(packet);
		return packet;
	}
	
	/**
	 * This command deletes one or more files. Wildcards may be used to delete multiple files. 
	 * If deletion date is valid only files older than deletion date are deleted.
	 * 
	 * @param eDeviceType
	 * @param eFileType
	 * @param DelDate Deletion date. Valid if ucMonth is not 0.
	 * @param szFileName
	 * @return %R1P,0,0:RC,unNumFilesDeleted
	 */
	public GCDataPacket FTR_Delete(FTR_DEVICETYPE eDeviceType, FTR_FILETYPE eFileType, FTR_MODDATE DelDate, String szFileName) {
		// %R1Q,23309:eDeviceType,eFileType,ucDay,ucMonth,ucYear,szFileName
		GCDataPacket packet = new GCDataPacket(
				RPC.FTR_Delete,
				String.valueOf(eDeviceType.getValue()) + "," + String.valueOf(eFileType.getValue()) + "," +
				String.valueOf(DelDate.ucDay) + "," + String.valueOf(DelDate.ucMonth) + "," + String.valueOf(DelDate.ucYear) + "," +
				szFileName		
		);

		this.doCommand(packet);
		return packet;
	}
	
	/**
	 * This command deletes one or more directories. Wildcards may be used to delete multiple directories. 
	 * If deletion date is valid only directories older than deletion date are deleted.
	 * It is only possible to use this command for file type FTR_FILE_POINTRELATEDDB.
	 * 
	 * @param eDeviceType
	 * @param eFileType
	 * @param DelDate Deletion date. Valid if ucMonth is not 0.
	 * @param szDirName
	 * @return %R1P,0,0:RC,unNumDirDeleted
	 */
	public GCDataPacket FTR_DeleteDir(FTR_DEVICETYPE eDeviceType, FTR_FILETYPE eFileType, FTR_MODDATE DelDate, String szDirName) {
		GCDataPacket packet = new GCDataPacket(
				RPC.FTR_DeleteDir,
				String.valueOf(eDeviceType.getValue()) + "," + String.valueOf(eFileType.getValue()) + "," +
				String.valueOf(DelDate.ucDay) + "," + String.valueOf(DelDate.ucMonth) + "," + String.valueOf(DelDate.ucYear) + "," +
				szDirName		
		);

		this.doCommand(packet);
		return packet;
	}

	/**
	 * Fuegt einen Change-Listener hinzu
	 * @param listener
	 */
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		this.pcs.addPropertyChangeListener(listener);
	}

	/**
	 * Entfernt einen Change-Listener
	 * @param listener
	 */
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		this.pcs.removePropertyChangeListener(listener);
	}
		
	public static boolean checkGRC(GCDataPacket packet) {
		return JGeoCOM.checkGRC(packet, null);
	}
	
	public static boolean checkGRC(GCDataPacket packet, Class<?> clazz) {
		return (packet != null && packet.getGRC() == GRC.GRC_OK && (clazz == null || packet.getOnAnswerArgument() != null && packet.getOnAnswerArgument().getClass() == clazz));
	}
	
	private byte[] hexStringToByteArray(String s) {
	    int len = s.length();
	    byte[] data = new byte[len / 2];
	    for (int i = 0; i < len; i += 2) {
	        data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
	                             + Character.digit(s.charAt(i+1), 16));
	    }
	    return data;
	}

	@Override
	public RxTx getRxTx() {
		return this.connRxTx;
	}
}
