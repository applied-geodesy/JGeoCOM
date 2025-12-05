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
//import java.io.FileOutputStream;
//
//
//import org.applied_geodesy.instrument.totalstaion.leica.GCDataPacket;
//import org.applied_geodesy.instrument.totalstaion.leica.JGeoCOM;
//import org.applied_geodesy.instrument.totalstaion.leica.data.FTR_BLOCK_LARGE;
//import org.applied_geodesy.instrument.totalstaion.leica.es.BOOLE;
//import org.applied_geodesy.instrument.totalstaion.leica.es.CAM_COMPRESSION_TYPE;
//import org.applied_geodesy.instrument.totalstaion.leica.es.CAM_ID_TYPE;
//import org.applied_geodesy.instrument.totalstaion.leica.es.CAM_JPEG_COMPR_QUALITY_TYPE;
//import org.applied_geodesy.instrument.totalstaion.leica.es.CAM_RESOLUTION_TYPE;
//import org.applied_geodesy.instrument.totalstaion.leica.es.CAM_WHITE_BALANCE_TYPE;
//import org.applied_geodesy.instrument.totalstaion.leica.es.CAM_ZOOM_FACTOR_TYPE;
//import org.applied_geodesy.instrument.totalstaion.leica.es.FTR_DEVICETYPE;
//import org.applied_geodesy.instrument.totalstaion.leica.es.FTR_FILETYPE;
//import org.applied_geodesy.instrument.totalstaion.leica.es.ON_OFF_TYPE;
//
//import gnu.io.CommPortIdentifier;
//import gnu.io.SerialPort;
//
//public class FTPTest {
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
//					CAM_RESOLUTION_TYPE.CAM_RES_2560x1920, //.CAM_RES_2560x1920,
//					CAM_COMPRESSION_TYPE.CAM_COMP_JPEG,   // RAW 
//					CAM_JPEG_COMPR_QUALITY_TYPE.CAM_JPGQ_BEST
//					);
//
//			if (!JGeoCOM.checkGRC(packet)) {
//				System.err.println("Fehler, Kameraeinstellungen konnten nicht geschrieben werden! " + packet.getGRC());
//				return false;
//			}
//
//			packet = geoCOM.CAM_SetActualImageName(camID, "OVC5MPx", imgNumber);
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
//			System.out.println("EInstellungen gesetzt!");
//			return true;
//		}
//		System.err.println("Fehler, Kamera nicht messbereit! " + packet.getGRC());
//		return true;
//	}
//	
//	public static void main(String[] args) throws Exception {
//		System.out.println("Start Application...");
//		JGeoCOM geoCOM = new JGeoCOM();
//		CAM_ID_TYPE camID = CAM_ID_TYPE.CAM_ID_OVC;
//		
//		try {
//			
//			boolean open = geoCOM.open(CommPortIdentifier.getPortIdentifier("COM2"), 9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
//			if (!open) {
//				System.err.println("Port kann nicht geoeffnet werden!");
//				return;
//			}
//			
//			GCDataPacket packet = null;
//			
//			if (!setImageSettings(geoCOM, camID, 1)) {
//				System.err.println("Fehler, Einstellungen nicht gesetzt!");
//				return;
//			}
//			
//			packet = geoCOM.CAM_OVC_SetActDistance(3.0, BOOLE.TRUE);
//			if (!JGeoCOM.checkGRC(packet)) {
//				System.err.println("Fehler, Strecke konnte nicht gesetzt werden! " + packet.getGRC());
//				return;
//			}
//			
//			packet = geoCOM.CAM_TakeImage(camID);
//			
//			if (!JGeoCOM.checkGRC(packet)) {
//				System.err.println("Fehler, Bild konnte nicht erstellt werden! " + packet.getGRC());
//				return;
//			}
//			
//			int maxBlockSize = 100; // FTR_BLOCK_LARGE.FTR_MAX_BLOCKSIZE_LARGE
//			packet = geoCOM.FTR_SetupDownloadLarge(
//					FTR_DEVICETYPE.FTR_DEVICE_SDCARD,
//					FTR_FILETYPE.FTR_FILE_IMAGES_OVC_JPG,
//					"OVC5MPx00001.jpg", 
//					maxBlockSize);
//			
//			
//			if (!JGeoCOM.checkGRC(packet)) {
//				System.err.println("Fehler, FTP nicht konfiguriert! " + packet.getGRC());
//				return;
//			}
//
//			FileOutputStream output = new FileOutputStream("C:\\Users\\michael.loesler\\Desktop\\aaaa.jpg", true);
//			try {
//				int unBlockNumber=1;
//				int len = 0;
//				do {
//					packet = geoCOM.FTR_DownloadXL(unBlockNumber, 60000);
//					if (!JGeoCOM.checkGRC(packet, FTR_BLOCK_LARGE.class)) {
//						System.err.println("Fehler, beim Uebertragen der Daten! " + packet.getGRC());
//						return;
//					}
//
//					FTR_BLOCK_LARGE block = (FTR_BLOCK_LARGE)packet.getOnAnswerArgument();
//					len = block.FTR_BLOCK_LARGE_len;
//					output.write(block.FTR_BLOCK_LARGE_val, 0, block.FTR_BLOCK_LARGE_len);
//					unBlockNumber++;
//				} 
//				while (len == maxBlockSize);
//
//			} finally {
//				output.close();
//			}
//			
//			
//			
//			
//		}
//		finally {
//			geoCOM.FTR_AbortDownload();
//			geoCOM.close();
//		}
//	}
//}
