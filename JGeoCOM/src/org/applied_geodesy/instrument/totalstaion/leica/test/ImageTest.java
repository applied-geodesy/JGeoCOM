///***********************************************************************
//* Copyright by Michael Loesler, https://software.applied-geodesy.org   *
//*                                                                      *
//* This program is free software; you can redistribute it and/or modify *
//* it under the terms of the GNU General Public License as published by *
//* the Free Software Foundation; either version 3 of the License, or    *
//* at your option any later version.                                    *
//*                                                                      *
//* This program is distributed in the hope that it will be useful,      *
//* but WITHOUT ANY WARRANTY; without even the implied warranty of       *
//* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the        *
//* GNU General Public License for more details.                         *
//*                                                                      *
//* You should have received a copy of the GNU General Public License    *
//* along with this program; if not, see <http://www.gnu.org/licenses/>  *
//* or write to the                                                      *
//* Free Software Foundation, Inc.,                                      *
//* 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.            *
//*                                                                      *
//***********************************************************************/
//
//package org.applied_geodesy.instrument.totalstaion.leica.test;
//
//import org.applied_geodesy.instrument.totalstaion.leica.GCDataPacket;
//import org.applied_geodesy.instrument.totalstaion.leica.JGeoCOM;
//import org.applied_geodesy.instrument.totalstaion.leica.data.AUT_FINE_ADJUST;
//import org.applied_geodesy.instrument.totalstaion.leica.data.CAM_2D_COORD_TYPE;
//import org.applied_geodesy.instrument.totalstaion.leica.data.CAM_3D_COORD_TYPE;
//import org.applied_geodesy.instrument.totalstaion.leica.data.CAM_OVC_EXTER_CALIB_TYPE;
//import org.applied_geodesy.instrument.totalstaion.leica.data.CAM_OVC_INTER_CALIB_TYPE;
//import org.applied_geodesy.instrument.totalstaion.leica.data.TMC_FULL_MEAS;
//import org.applied_geodesy.instrument.totalstaion.leica.data.TMC_STATION;
//import org.applied_geodesy.instrument.totalstaion.leica.es.AUT_ATR_MODE;
//import org.applied_geodesy.instrument.totalstaion.leica.es.AUT_POS_MODE;
//import org.applied_geodesy.instrument.totalstaion.leica.es.BOOLE;
//import org.applied_geodesy.instrument.totalstaion.leica.es.CAM_COMPRESSION_TYPE;
//import org.applied_geodesy.instrument.totalstaion.leica.es.CAM_ID_TYPE;
//import org.applied_geodesy.instrument.totalstaion.leica.es.CAM_JPEG_COMPR_QUALITY_TYPE;
//import org.applied_geodesy.instrument.totalstaion.leica.es.CAM_RESOLUTION_TYPE;
//import org.applied_geodesy.instrument.totalstaion.leica.es.CAM_WHITE_BALANCE_TYPE;
//import org.applied_geodesy.instrument.totalstaion.leica.es.CAM_ZOOM_FACTOR_TYPE;
//import org.applied_geodesy.instrument.totalstaion.leica.es.ON_OFF_TYPE;
//import org.applied_geodesy.instrument.totalstaion.leica.es.TMC_INCLINE_PRG;
//import org.applied_geodesy.instrument.totalstaion.leica.es.TMC_MEASURE_PRG;
//
//import gnu.io.CommPortIdentifier;
//import gnu.io.SerialPort;
//
//public class ImageTest {
//	public static final double RHO_GRAD2RAD = Math.PI/200.0;
//	public static final double RHO_RAD2GRAD = 200.0/Math.PI;
//	static final double MOD(double x, double y){
//		return x-Math.floor(x/y)*y;
//	}
//
//	static boolean setImageSettings(JGeoCOM geoCOM, CAM_ID_TYPE camID, int imgNumber) {
//		GCDataPacket packet = null;
//
//		// Schalte Kamera ein (sofern diese aus ist)
//		packet = geoCOM.CAM_GetCameraPowerSwitch(camID);
//
//		if (JGeoCOM.checkGRC(packet, ON_OFF_TYPE.class) && packet.getOnAnswerArgument() == ON_OFF_TYPE.OFF) {
//			packet = geoCOM.CAM_SetCameraPowerSwitch(camID, ON_OFF_TYPE.ON);
//			if (!JGeoCOM.checkGRC(packet)) {
//				System.err.println("Fehler, Kamera konnte nicht aktiviert werden " + packet.getGRC());
//				return false;
//			}
//		}
//		
//		// Pruefe, ob Kamera messbereit
//		packet = geoCOM.CAM_IsCameraReady(camID);
//		if (JGeoCOM.checkGRC(packet)) {
//			// Deaktiviere Auto-Focus
//			packet = geoCOM.CAM_AF_ContinuousAutofocus(BOOLE.FALSE);
//			if (!JGeoCOM.checkGRC(packet)) {
//				System.err.println("Fehler, Autofocus konnten nicht deaktiviert werden! " + packet.getGRC());
//				return false;
//			}
//
//			// Zoom-Einstellungen
//			packet = geoCOM.CAM_SetZoomFactor(camID, CAM_ZOOM_FACTOR_TYPE.CAM_ZOOM_1X);
//			if (!JGeoCOM.checkGRC(packet)) {
//				System.err.println("Fehler, Zoom-Einstellung konnte nicht gesetzt werden! " + packet.getGRC());
//				return false;
//			}
//
//			// Setze Bildeinstellungen
//			packet = geoCOM.CAM_SetCameraProperties(
//					camID, 
//					CAM_RESOLUTION_TYPE.CAM_RES_2560x1920,
//					CAM_COMPRESSION_TYPE.CAM_COMP_RAW, 
//					CAM_JPEG_COMPR_QUALITY_TYPE.CAM_JPGQ_BEST
//					);
//
//			if (!JGeoCOM.checkGRC(packet)) {
//				System.err.println("Fehler, Kameraeinstellungen konnten nicht geschrieben werden! " + packet.getGRC());
//				return false;
//			}
//
//			packet = geoCOM.CAM_SetActualImageName(camID, "OVCKALIB201707195MPx", imgNumber);
//			if (!JGeoCOM.checkGRC(packet)) {
//				System.err.println("Fehler, Bildname konnten nicht geschrieben werden! " + packet.getGRC());
//				return false;
//			}
//
//			packet = geoCOM.CAM_SetWhiteBalanceMode(camID, CAM_WHITE_BALANCE_TYPE.CAM_WB_AUTO);
//			if (!JGeoCOM.checkGRC(packet)) {
//				System.err.println("Fehler, Weissabgleich konnten nicht gesetzt werden! " + packet.getGRC());
//				return false;
//			}
//			return true;
//		}
//		System.err.println("Fehler, Kamera nicht messbereit! " + packet.getGRC());
//		return true;
//	}
//
//	static double[] reduceToFaceI(double distance, double azimuth, double zenith) {
//		// Vollkreisreduktion
//		zenith  = MOD(zenith, 2.0*Math.PI);
//		azimuth = MOD(azimuth, 2.0*Math.PI);
//
//		// Messung in Lage II? Dann reduziere auf Lage I
//		if (zenith > Math.PI) {
//			//					0  = 400 gon - (zI + zII)
//			//					0  = 400 gon - zI - zII
//			//					zI = 400 gon - zII
//
//			zenith  = 2.0*Math.PI - zenith;
//			azimuth = azimuth - Math.PI;
//
//			zenith  = MOD(zenith,  2.0*Math.PI);
//			azimuth = MOD(azimuth, 2.0*Math.PI);
//		}
//		return new double[] {distance, azimuth, zenith};
//	}
//
//	public static double[] doMeasurement(JGeoCOM geoCom) {
//		GCDataPacket packet = null;
//		packet = geoCom.TMC_DoMeasure(TMC_MEASURE_PRG.TMC_CLEAR, TMC_INCLINE_PRG.TMC_AUTO_INC);
//		if (!JGeoCOM.checkGRC(packet))
//			return null;
//		
//		AUT_FINE_ADJUST fineAdjust = new AUT_FINE_ADJUST(0.05*RHO_GRAD2RAD, 0.05*RHO_GRAD2RAD);
//		packet = geoCom.AUT_FineAdjust(fineAdjust);
//		if (!JGeoCOM.checkGRC(packet))
//			return null;
//
//		// TMC_RTRK_DIST  TMC_DEF_DIST
//		packet = geoCom.TMC_DoMeasure(TMC_MEASURE_PRG.TMC_DEF_DIST, TMC_INCLINE_PRG.TMC_AUTO_INC);
//		if (!JGeoCOM.checkGRC(packet))
//			return null;
//
//		packet = geoCom.TMC_GetFullMeas(12500L, TMC_INCLINE_PRG.TMC_AUTO_INC );
//		
//		//long obsTime = System.currentTimeMillis();
//		if (!JGeoCOM.checkGRC(packet, TMC_FULL_MEAS.class))
//			return null;
//		TMC_FULL_MEAS polarObs = (TMC_FULL_MEAS)packet.getOnAnswerArgument();
//
//		double distance = polarObs.rdSlopeDist;
//		double azimuth  = polarObs.rdHzAngle;
//		double zenith   = polarObs.rdVAngle;
//		//long distTime   = (long)polarObs.rdDistTime;
//
//		return reduceToFaceI(distance, azimuth, zenith);
//	}
//
//	public boolean goPosition(JGeoCOM geoCom, double distance, double azimuth, double zenith) {
//		// OHNE ATR --> AUT_ATR_MODE.AUT_TARGET
//		GCDataPacket packet = geoCom.AUT_MakePositioning(azimuth, zenith, AUT_POS_MODE.AUT_NORMAL, AUT_ATR_MODE.AUT_POSITION);
//		return JGeoCOM.checkGRC(packet);
//	}
//
//	public static void main(String[] args) throws Exception {
//		System.out.println("Start Application...");
//		JGeoCOM geoCOM = new JGeoCOM();
//		CAM_ID_TYPE camID = CAM_ID_TYPE.CAM_ID_OVC;
//		try {
//			GCDataPacket packet = null;
//			boolean open = geoCOM.open(CommPortIdentifier.getPortIdentifier("COM2"), 9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
//
//			if (!open) {
//				System.err.println("Port kann nicht geoeffnet werden!");
//				return;
//			}
//			
//			packet = null;
//			TMC_STATION station = new TMC_STATION(0.0, 0.0, 0.0, 0.0);
//			packet = geoCOM.TMC_SetStation(station);
//			if (!JGeoCOM.checkGRC(packet))
//				return;
//	
//			packet = geoCOM.AUT_MakePositioning(0.0 * Math.PI, 0.5 * Math.PI, AUT_POS_MODE.AUT_NORMAL, AUT_ATR_MODE.AUT_POSITION);
//			if (!JGeoCOM.checkGRC(packet))
//				return;
//			
//			if (!setImageSettings(geoCOM, camID, 1)) {
//				System.err.println("Fehler, Einstellungen nicht gesetzt!");
//				return;
//			}
//			
//			packet = geoCOM.CAM_OVC_ReadInterOrient(BOOLE.TRUE);
//
//			if (JGeoCOM.checkGRC(packet, CAM_OVC_INTER_CALIB_TYPE.class)) {
//				CAM_OVC_INTER_CALIB_TYPE interCalib = (CAM_OVC_INTER_CALIB_TYPE)packet.getOnAnswerArgument();				
//				System.out.println("Focal length   c = " + interCalib.dFocalLength);
//				System.out.println("PrincipalPoint x = " + interCalib.principalPoint.dX);
//				System.out.println("PrincipalPoint y = " + interCalib.principalPoint.dY);
//				System.out.println("Pixel size     s = " + interCalib.dPixelSize);
//			}
//			
//			packet = geoCOM.CAM_GetCamPos(camID);
//			
//			if (JGeoCOM.checkGRC(packet, CAM_3D_COORD_TYPE.class)) {
//				CAM_3D_COORD_TYPE coord = (CAM_3D_COORD_TYPE)packet.getOnAnswerArgument();
//				System.out.println("X0 = " + coord.dX);
//				System.out.println("Y0 = " + coord.dY);
//				System.out.println("Z0 = " + coord.dZ);
//			}
//			
//			packet = geoCOM.CAM_OVC_ReadExterOrient(BOOLE.TRUE);
//			
//			if (JGeoCOM.checkGRC(packet, CAM_OVC_EXTER_CALIB_TYPE.class)) {
//				CAM_OVC_EXTER_CALIB_TYPE exterCalib = (CAM_OVC_EXTER_CALIB_TYPE)packet.getOnAnswerArgument();
//				
//				System.out.println("X0 = " + exterCalib.cameraLocation.dX);
//				System.out.println("Y0 = " + exterCalib.cameraLocation.dY);
//				System.out.println("Z0 = " + exterCalib.cameraLocation.dZ);
//				
//				
//				System.out.println("phi   = " + exterCalib.cameraRotation.dPhi   );
//				System.out.println("theta = " + exterCalib.cameraRotation.dTheta );
//				System.out.println("kappa = " + exterCalib.cameraRotation.dKappa );
//			}
//			
//			
//			
//			double[] distanceAzimuthZenith = doMeasurement(geoCOM);
//			if (distanceAzimuthZenith != null && distanceAzimuthZenith.length == 3) {
//				System.out.println("DIST = " + distanceAzimuthZenith[0]);
//				System.out.println("AZIM = " + distanceAzimuthZenith[1]);
//				System.out.println("ZENI = " + distanceAzimuthZenith[2]);
//			}
//			double dDist = distanceAzimuthZenith[0];
//			
//			
//			packet = geoCOM.CAM_OVC_SetActDistance(dDist, BOOLE.TRUE);
//			
//			
//			packet = geoCOM.CAM_OVC_GetActCameraCentre();
//				
//			if (JGeoCOM.checkGRC(packet, CAM_2D_COORD_TYPE.class)) {
//				CAM_2D_COORD_TYPE coord2D = (CAM_2D_COORD_TYPE)packet.getOnAnswerArgument();
//				
//				System.out.println("px = " + coord2D.dX);
//				System.out.println("py = " + coord2D.dY);
//			}
//			
//			
//			
////			for (int i=0; i<1; i++) {
////				Thread.sleep(2500);
////				System.out.println("-------------------------------");
////
////				System.out.println("Setze Kameraeinstellungen");
////				if (!setImageSettings(geoCOM, camID, 1)) {
////					System.err.println("Fehler, Einstellungen nicht gesetzt!");
////					return;
////				}
////
////				System.out.println("Starte polare Messung....   " + dfmt.format(new Date()));
////				double distAzZe[] = doMeasurement(geoCOM);
////				double distance = distAzZe[0];
////				double azimuth  = distAzZe[1];
////				double zenith   = distAzZe[2];
////
////				System.out.println("Polare Messung " + distance + "   " + azimuth*RHO_RAD2GRAD + "   " + zenith*RHO_RAD2GRAD + "  " +  dfmt.format(new Date()));
////
////				packet = geoCOM.CAM_OVC_SetActDistance(distance, BOOLE.TRUE);
////				if (!JGeoCOM.checkGRC(packet)) {
////					System.err.println("Strecke konnte nicht gesetzt werden! " + packet.getRPC());
////				}
////
////				System.out.println("Starte Bildaufnahme...  " + dfmt.format(new Date()));
////				packet = geoCOM.CAM_TakeImage(camID);
////				if (!JGeoCOM.checkGRC(packet)) {
////					System.err.println("Fehler, Bild konnte nicht erstellt werden! " + packet.getGRC());
////					return;
////				}
////				System.out.println("Bild gespeichert...  " + dfmt.format(new Date()));
////				System.out.println();
////
////				packet = geoCOM.CAM_OVC_GetActCameraCentre();
////				if (!JGeoCOM.checkGRC(packet, CAM_2D_COORD_TYPE.class)) {
////					System.err.println("Fehler, Position des Stichkreuzes konnte nicht gelesen werden! " + packet.getGRC());
////					return;
////				}
////				CAM_2D_COORD_TYPE coord2d = (CAM_2D_COORD_TYPE)packet.getOnAnswerArgument();
////				System.out.println("Strichkreuzpos:       " + coord2d.dX+"   "+coord2d.dY);
////
////				packet = geoCOM.CAM_GetCamPos(camID);
////				if (!JGeoCOM.checkGRC(packet, CAM_3D_COORD_TYPE.class)) {
////					System.err.println("Fehler, 3D-Position der Kamera konnte nicht gelesen werden! " + packet.getGRC());
////					return;
////				}
////				CAM_3D_COORD_TYPE coord3d = (CAM_3D_COORD_TYPE)packet.getOnAnswerArgument();
////				System.out.println("Kameraposition-3D:    " + coord3d.dX+"   "+coord3d.dY+"   "+coord3d.dZ);
////
////
////				packet = geoCOM.CAM_OVC_ReadExterOrient(BOOLE.TRUE);
////				if (!JGeoCOM.checkGRC(packet, CAM_OVC_EXTER_CALIB_TYPE.class)) {
////					System.err.println("Fehler, aussere Orientierung konnte nicht gelesen werden! " + packet.getGRC());
////					return;
////				}
////				CAM_OVC_EXTER_CALIB_TYPE exterCalib = (CAM_OVC_EXTER_CALIB_TYPE)packet.getOnAnswerArgument();
////				System.out.println("Aussere Ori 3D-Pos:   " + exterCalib.cameraLocation.dX + "   " + exterCalib.cameraLocation.dY + "   " + exterCalib.cameraLocation.dZ);
////				System.out.println("Aussere Ori RotAngle: " + MOD(exterCalib.cameraRotation.dPhi, 2.0*Math.PI)*RHO_RAD2GRAD + "   " + MOD(exterCalib.cameraRotation.dTheta, 2.0*Math.PI)*RHO_RAD2GRAD + "   " + MOD(exterCalib.cameraRotation.dKappa,2.0*Math.PI)*RHO_RAD2GRAD);
////
////				packet = geoCOM.CAM_OVC_ReadInterOrient(BOOLE.TRUE);
////				if (!JGeoCOM.checkGRC(packet, CAM_OVC_INTER_CALIB_TYPE.class)) {
////					System.err.println("Fehler, innere Orientierung konnte nicht gelesen werden! " + packet.getGRC());
////					return;
////				}
////				CAM_OVC_INTER_CALIB_TYPE interCalib = (CAM_OVC_INTER_CALIB_TYPE)packet.getOnAnswerArgument();
////				System.out.println("Brennweite            " + interCalib.dFocalLength);
////				System.out.println("Px-Size               " + interCalib.dPixelSize);
////				System.out.println("Bildhauptpkt          " + interCalib.principalPoint.dX + "    " + interCalib.principalPoint.dY);
////			}		
//		}
//		finally {
//			geoCOM.FTR_AbortDownload();
//			geoCOM.close();
//		}
//	}
//}
