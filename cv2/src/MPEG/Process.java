package MPEG;

//import java.awt.Desktop.Action;
import java.net.URL;
import java.util.ResourceBundle;

import Jama.Matrix;
import ij.ImagePlus;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;



public class Process implements Initializable{
	private ImagePlus imagePlus;
	private ColorTransform colorTransform;
	private ColorTransform colorTransformOrig;
	private ColorTransform colorTransformObr1;
	private ColorTransform colorTransformObr2;
	private ColorTransform colorTransformTmp1;
	private ColorTransform colorTransformTmp2;
	private Quality quality = new Quality();
	private MPEG mpeg = new MPEG();
	private TransformMatrix transformMatrix = new TransformMatrix();
	private int TransformMode;
	private int blockSize = 0;
	private int frameSize = 0;
	private Matrix[][] YBlock;
	private Matrix[][] CbBlock;
	private Matrix[][] CrBlock;
	
	public static final int RED = 1;
	public static final int GREEN = 2;
	public static final int BLUE = 3;
	public static final int Y = 4;
	public static final int Cb = 5;
	public static final int Cr = 6;
	public static final int C444 = 7;
	public static final int C422 = 8;
	public static final int C420 = 9;
	public static final int C411 = 10;
	
//	public Process(ImagePlus imagePlus) {
//		this.imagePlus = imagePlus;
//		private ColorTransform colorTransform;
//		private ColorTransform colorTransformOrig;
//		
//		this.colorTransform = new ColorTransform(imagePlus.getBufferedImage());
//		
//		test1();
//	}

	public void test1() {
		imagePlus.show("Original Image");
		colorTransform.getRGB();
		colorTransform.convertRgbToYcbcr();
		colorTransform.convertYcbcrToRgb();
		colorTransform.setImageFromRGB(colorTransform.getRed().length,
				colorTransform.getRed()[0].length, colorTransform.getRed(),
				colorTransform.getGreen(), colorTransform.getBlue()).show(
				"Transformed Image");

	}
	
	public void nactiOrigObraz()
	{
		this.imagePlus = new ImagePlus("pomaly.jpg");
		this.colorTransformObr1 = new ColorTransform(imagePlus.getBufferedImage());
		this.imagePlus = new ImagePlus("pomaly2.jpg");
		
		this.colorTransformObr2 = new ColorTransform(imagePlus.getBufferedImage());		
		this.imagePlus = new ImagePlus("lena_std.jpg");
		this.colorTransformOrig = new ColorTransform(imagePlus.getBufferedImage());
		this.colorTransform = new ColorTransform(imagePlus.getBufferedImage());
		
		this.colorTransformObr1.getRGB();
		this.colorTransformObr2.getRGB();		
		this.colorTransformObr1.convertRgbToYcbcr();
		this.colorTransformObr2.convertRgbToYcbcr();
		this.colorTransform.getRGB();
		this.colorTransformOrig.getRGB();		
		this.colorTransform.convertRgbToYcbcr();
		this.colorTransformOrig.convertRgbToYcbcr();
		this.colorTransformObr1.setImageFromRGB(this.colorTransformObr1.getImageWidth(), this.colorTransformObr1.getImageHeight(), this.colorTransformObr1.getRed(), this.colorTransformObr1.getGreen(), this.colorTransformObr1.getBlue(), "Snimek1").show("Snimek1");
		this.colorTransformObr2.setImageFromRGB(this.colorTransformObr2.getImageWidth(), this.colorTransformObr2.getImageHeight(), this.colorTransformObr2.getRed(), this.colorTransformObr2.getGreen(), this.colorTransformObr2.getBlue(), "Snimek2").show("Snimek2");

	}
	
	@FXML
	private Button RedButton;
	@FXML
	private Button GreenButton;
	@FXML
	private Button BlueButton;
	@FXML
	private Button YButton;
	@FXML
	private Button CbButton;
	@FXML
	private Button CrButton;
	@FXML
	private Button T444Button;
	@FXML
	private Button T422Button;
	@FXML
	private Button T420Button;
	@FXML
	private Button T411Button;
	@FXML
	private Button OverButton;
	@FXML
	private Button QualityButton;
	@FXML
	private Button DCTButton;
	@FXML
	private Button InverseDCTButton;
	@FXML
	private Button WHTButton;
	@FXML
	private Button InverseWHTButton;
	@FXML
	private Button quantizationButton;
	@FXML
	private Button InvQuantizationBontton;
	@FXML
	private Button DPCMButton;
	@FXML
	private Button CompButton;
	@FXML
	private Button DPCMkomButton;
	//@FXML
	//private Button DekoderBontton;
	@FXML
	private RadioButton Qant16;
	@FXML
	private RadioButton Qant8;
	@FXML
	private RadioButton Qant4;
	@FXML
	private RadioButton Qant2;
	@FXML
	private RadioButton Frame16;
	@FXML
	private RadioButton Frame8;
	@FXML
	private RadioButton Frame4;
	@FXML
	private RadioButton Frame2;
	
	final ToggleGroup RadioButtonGroup = new ToggleGroup();
	final ToggleGroup RadioButtonGroupFrame = new ToggleGroup();
		
	private RadioButton selectedTransformBlockSizeRadioButton;
	private RadioButton selectedFrameSizeRadioButton;
	@FXML
	private Slider QuantizationSlider;
	@FXML
	private TextField MSE;
	@FXML
	private TextField PSNR;
	@FXML
	private TextField SAD;
	
	public void rButtonPressed(ActionEvent event) {
		// TODO Auto-generated method stub
		getComponent(Process.RED).show("Red Component");
	}
	
	public void gButtonPressed(ActionEvent event) {
		// TODO Auto-generated method stub
		getComponent(Process.GREEN).show("Green Component");
	}
	
	public void bButtonPressed(ActionEvent event) {
		// TODO Auto-generated method stub
		getComponent(Process.BLUE).show("Blue Component");
	}
	
	
	
	public void YButtonPressed(ActionEvent event) {
		// TODO Auto-generated method stub
		getComponent(Process.Y).show("Blue Y");
	}
	
	public void CbButtonPressed(ActionEvent event) {
		// TODO Auto-generated method stub
		getComponent(Process.Cb).show("Blue Cb");
	}
	
	public void CrButtonPressed(ActionEvent event) {
		// TODO Auto-generated method stub
		getComponent(Process.Cr).show("Blue Cr");
	}
	
	
	public void C444ButtonPressed(ActionEvent event) {
		// TODO Auto-generated method stub
		downSample(Process.C444);
	}
	
	public void C422bButtonPressed(ActionEvent event) {
		// TODO Auto-generated method stub
		downSample(Process.C422);
	}
	
	public void C420ButtonPressed(ActionEvent event) {
		// TODO Auto-generated method stub
		downSample(Process.C420);
	}
	
	public void C411ButtonPressed(ActionEvent event) {
		// TODO Auto-generated method stub
				downSample(Process.C411);
	}
	
	public void OverButtonPressed(ActionEvent event) {
		// TODO Auto-generated method stub
		overSample();
	}
	
	public void QualityButtonPressed(ActionEvent event) {
		// TODO Auto-generated method stub
		double mse = 0;
		mse += quality.getMse(colorTransformOrig.getImageWidth(), colorTransformOrig.getImageHeight(), colorTransformOrig.getRed(), colorTransform.getRed());
		mse += quality.getMse(colorTransformOrig.getImageWidth(), colorTransformOrig.getImageHeight(), colorTransformOrig.getGreen(), colorTransform.getGreen());
		mse += quality.getMse(colorTransformOrig.getImageWidth(), colorTransformOrig.getImageHeight(), colorTransformOrig.getBlue(), colorTransform.getBlue());
		mse = mse/3;
		MSE.setText(String.valueOf(mse));
		//PSNR.setText(String.valueOf(quality.getPsnr(colorTransformOrig.getImageWidth(), colorTransformOrig.getImageHeight(), colorTransformOrig.getRed(), colorTransform.getRed())));
		PSNR.setText(String.valueOf(quality.getPsnr(mse)));
	}
	
	public void DCTButtonPressed(ActionEvent event) {
		// TODO Auto-generated method stub
		colorTransform.setY(colorTransform.transform(colorTransform.getImageWidth(), transformMatrix.getDctMatrix(colorTransform.getImageWidth()), colorTransformOrig.getY()));
		colorTransform.setcB(colorTransform.transform(colorTransform.getImageWidth(), transformMatrix.getDctMatrix(colorTransform.getImageWidth()), colorTransformOrig.getcB()));
		colorTransform.setcR(colorTransform.transform(colorTransform.getImageWidth(), transformMatrix.getDctMatrix(colorTransform.getImageWidth()), colorTransformOrig.getcR()));
	}
	
	public void InverseDCTButtonPressed(ActionEvent event) {
		// TODO Auto-generated method stub
		colorTransform.setY(colorTransform.inverseTransform(colorTransform.getImageWidth(), transformMatrix.getDctMatrix(colorTransform.getImageWidth()), colorTransform.getY()));
		colorTransform.setcB(colorTransform.inverseTransform(colorTransform.getImageWidth(), transformMatrix.getDctMatrix(colorTransform.getImageWidth()), colorTransform.getcB()));
		colorTransform.setcR(colorTransform.inverseTransform(colorTransform.getImageWidth(), transformMatrix.getDctMatrix(colorTransform.getImageWidth()), colorTransform.getcR()));
		this.colorTransform.convertYcbcrToRgb();
		this.colorTransform.setImageFromRGB(this.colorTransform.getImageWidth(), this.colorTransform.getImageHeight(), this.colorTransform.getRed(), this.colorTransform.getGreen(), this.colorTransform.getBlue(), "Po transformaci").show("Po transformaci");
	}
	
	public void WHTButtonPressed(ActionEvent event) {
		// TODO Auto-generated method stub
		colorTransform.setY(colorTransform.transform(colorTransform.getImageWidth(), transformMatrix.getWhtMatrix(colorTransform.getImageWidth()), colorTransformOrig.getY()));
		colorTransform.setcB(colorTransform.transform(colorTransform.getImageWidth(), transformMatrix.getWhtMatrix(colorTransform.getImageWidth()), colorTransformOrig.getcB()));
		colorTransform.setcR(colorTransform.transform(colorTransform.getImageWidth(), transformMatrix.getWhtMatrix(colorTransform.getImageWidth()), colorTransformOrig.getcR()));
	}
	
	public void InverseWHTButtonPressed(ActionEvent event) {
		// TODO Auto-generated method stub
		colorTransform.setY(colorTransform.inverseTransform(colorTransform.getImageWidth(), transformMatrix.getWhtMatrix(colorTransform.getImageWidth()), colorTransform.getY()));
		
		colorTransform.setcB(colorTransform.inverseTransform(colorTransform.getImageWidth(), transformMatrix.getWhtMatrix(colorTransform.getImageWidth()), colorTransform.getcB()));
		colorTransform.setcR(colorTransform.inverseTransform(colorTransform.getImageWidth(), transformMatrix.getWhtMatrix(colorTransform.getImageWidth()), colorTransform.getcR()));
		this.colorTransform.convertYcbcrToRgb();
		this.colorTransform.setImageFromRGB(this.colorTransform.getImageWidth(), this.colorTransform.getImageHeight(), this.colorTransform.getRed(), this.colorTransform.getGreen(), this.colorTransform.getBlue(), "Po transformaci").show("Po transformaci");
	}
	
	public void quantizationButtonPressed(ActionEvent event) {
		// TODO Auto-generated method stub
		
		selectedTransformBlockSizeRadioButton = (RadioButton) RadioButtonGroup.getSelectedToggle();

		if (selectedTransformBlockSizeRadioButton.getText().equalsIgnoreCase("16x16"))
			blockSize = 16;
		if (selectedTransformBlockSizeRadioButton.getText().equalsIgnoreCase("8x8"))
			blockSize = 8;
		if (selectedTransformBlockSizeRadioButton.getText().equalsIgnoreCase("4x4"))
			blockSize = 4;
		if (selectedTransformBlockSizeRadioButton.getText().equalsIgnoreCase("2x2"))
			blockSize = 2;
		YBlock = colorTransform.MatrixToBlocks(colorTransform.getY(), blockSize);
		/*System.out.println("Vypisuji matici pøed qantizaci::");
		YBlock[0][0].print(2, 0);
		System.out.println("");*/
		
		CbBlock = colorTransform.MatrixToBlocks(colorTransform.getcB(), blockSize);
		CrBlock = colorTransform.MatrixToBlocks(colorTransform.getcR(), blockSize);
		
		//colorTransform.getY().print(2, 2);
		
		/*YBlock = colorTransform.MatrixToBlocks(colorTransform.getY(), blockSize);
		CbBlock = colorTransform.MatrixToBlocks(colorTransform.getcB(), blockSize);
		CrBlock = colorTransform.MatrixToBlocks(colorTransform.getcR(), blockSize);*/
		
		YBlock = colorTransform.QuantizationY(YBlock, QuantizationSlider.getValue());
		/*System.out.println("Vypisuji matici po qantizaci::");
		YBlock[0][0].print(2, 3);
		System.out.println("");*/
		CbBlock = colorTransform.QuantizationC(CbBlock, QuantizationSlider.getValue());
		CrBlock = colorTransform.QuantizationC(CrBlock, QuantizationSlider.getValue());
		
	}
	
	public void InvQuantizationBonttonPressed(ActionEvent event) {
		// TODO Auto-generated method stub
		if (blockSize == 0) return;
		
		YBlock = colorTransform.InvQuantizationY(YBlock, QuantizationSlider.getValue());
		CbBlock = colorTransform.InvQuantizationC(CbBlock, QuantizationSlider.getValue());
		CrBlock = colorTransform.InvQuantizationC(CrBlock, QuantizationSlider.getValue());
		
		colorTransform.setY(colorTransform.BlocksToMatrix(YBlock, blockSize));
		/*System.out.println("Vypisuji matici po obnoveni:");
		YBlock[0][0].print(2, 0);
		System.out.println("");*/
		colorTransform.setcB(colorTransform.BlocksToMatrix(CbBlock, blockSize));
		colorTransform.setcR(colorTransform.BlocksToMatrix(CrBlock, blockSize));
		

	}
	
	public void DPCMButtonPressed(ActionEvent event) {
		colorTransformTmp1 = mpeg.DPCM(colorTransformObr2, colorTransformObr1);
		//colorTransformTmp1.convertYcbcrToRgb();
		//colorTransformTmp1.convertRgbToYcbcr();
		this.colorTransformTmp1.setImageFromRGB(this.colorTransformTmp1.getImageWidth(), this.colorTransformTmp1.getImageHeight(), this.colorTransformTmp1.getY(), "Y DPCM").show();
		this.colorTransformTmp1.setImageFromRGB(this.colorTransformTmp1.getcB().getColumnDimension(), this.colorTransformTmp1.getcB().getRowDimension(), this.colorTransformTmp1.getcB(), "Cb DPCM").show();
		this.colorTransformTmp1.setImageFromRGB(this.colorTransformTmp1.getcR().getColumnDimension(), this.colorTransformTmp1.getcR().getRowDimension(), this.colorTransformTmp1.getcR(), "Cr DPCM").show();
		
	}
	
	private void getFrameSize() {
		selectedFrameSizeRadioButton = (RadioButton) RadioButtonGroupFrame.getSelectedToggle();

		if (selectedFrameSizeRadioButton.getText().equalsIgnoreCase("16x16"))
			frameSize = 16;
		if (selectedFrameSizeRadioButton.getText().equalsIgnoreCase("8x8"))
			frameSize = 8;
		if (selectedFrameSizeRadioButton.getText().equalsIgnoreCase("4x4"))
			frameSize = 4;
		if (selectedFrameSizeRadioButton.getText().equalsIgnoreCase("2x2"))
			frameSize = 2;
	}
	
	public void CompButtonPressed(ActionEvent event) {
		getFrameSize();
		mpeg.fullSearch(colorTransformObr1.getY(), colorTransformObr2.getY(), frameSize);
		this.colorTransformTmp2 = mpeg.motionCompensation(colorTransformObr1, frameSize);
		
		this.colorTransformTmp2.setImageFromRGB(this.colorTransformTmp2.getImageWidth(), this.colorTransformTmp2.getImageHeight(), this.colorTransformTmp2.getY(), "Y Kompenzace").show();
		this.colorTransformTmp2.setImageFromRGB(this.colorTransformTmp2.getcB().getColumnDimension(), this.colorTransformTmp2.getcB().getRowDimension(), this.colorTransformTmp2.getcB(), "Cb Kompenzace").show();
		this.colorTransformTmp2.setImageFromRGB(this.colorTransformTmp2.getcR().getColumnDimension(), this.colorTransformTmp2.getcR().getRowDimension(), this.colorTransformTmp2.getcR(), "Cr Kompenzace").show();
		
	}
	
	public void DPCMkomButton(ActionEvent event) {
		getFrameSize();
		mpeg.fullSearch(colorTransformObr1.getY(), colorTransformObr2.getY(), frameSize);
		this.colorTransformTmp2 = mpeg.motionCompensation(colorTransformObr1, frameSize);
		
		colorTransformTmp2 = mpeg.DPCM(colorTransformObr2, colorTransformTmp2);
		
		this.colorTransformTmp2.setImageFromRGB(this.colorTransformTmp2.getImageWidth(), this.colorTransformTmp2.getImageHeight(), this.colorTransformTmp2.getY(), "Y Kompenzace").show();
		this.colorTransformTmp2.setImageFromRGB(this.colorTransformTmp2.getcB().getColumnDimension(), this.colorTransformTmp2.getcB().getRowDimension(), this.colorTransformTmp2.getcB(), "Cb Kompenzace").show();
		this.colorTransformTmp2.setImageFromRGB(this.colorTransformTmp2.getcR().getColumnDimension(), this.colorTransformTmp2.getcR().getRowDimension(), this.colorTransformTmp2.getcR(), "Cr Kompenzace").show();
		
		this.colorTransformTmp2.setImageFromRGB(this.colorTransformTmp2.getImageWidth(), this.colorTransformTmp2.getImageHeight(), this.colorTransformTmp2.getY(), "Y Kompenzace + DPCM").show();
		this.colorTransformTmp2.setImageFromRGB(this.colorTransformTmp2.getcB().getColumnDimension(), this.colorTransformTmp2.getcB().getRowDimension(), this.colorTransformTmp2.getcB(), "Cb Kompenzace + DPCM").show();
		this.colorTransformTmp2.setImageFromRGB(this.colorTransformTmp2.getcR().getColumnDimension(), this.colorTransformTmp2.getcR().getRowDimension(), this.colorTransformTmp2.getcR(), "Cr Kompenzace + DPCM").show();
		if(colorTransformTmp1 != null) {
			double sad = (quality.getSad(colorTransformTmp1.getY(), colorTransformTmp2.getY()) + quality.getSad(colorTransformTmp1.getcB(), colorTransformTmp2.getcB()) + quality.getSad(colorTransformTmp1.getcR(), colorTransformTmp2.getcR()))/3;
			SAD.setText(String.valueOf(sad));
		}
		
	}
	
	/*public void DekoderBonttonPressed(ActionEvent event) {
		// TODO Auto-generated method stub
		overSample();
	}*/
	
	public void downSample(int mode)
	{
		TransformMode = mode;
		this.colorTransform.setY(this.colorTransformOrig.getY());
			Matrix Cb = new Matrix(this.colorTransformOrig.getcB().getArray());
			Matrix Cr =new Matrix(this.colorTransformOrig.getcR().getArray());
			
				switch (mode) {
				case C422:
					  Cb = this.colorTransform.downSample(Cb);
					  Cr = this.colorTransform.downSample(Cr);
					break;
					
				case C411:
	
					for (int i = 0; i < 2; i++) 
					{
					  Cb = this.colorTransform.downSample(Cb);
					  Cr = this.colorTransform.downSample(Cr);
					}
					break;
					
				case C420:
					for (int i = 0; i < 2; i++) 
					{
						Cb = this.colorTransform.downSample(Cb);
						Cr = this.colorTransform.downSample(Cr);
						Cb = Cb.transpose();
						Cr = Cr.transpose();
					}
					break;
				
					default:
						break;
				}
				this.colorTransform.setcB(Cb);
				this.colorTransform.setcR(Cr);
		
		this.colorTransform.setImageFromRGB(this.colorTransform.getImageWidth(), this.colorTransform.getImageHeight(), this.colorTransform.getY(), "Y").show();
		this.colorTransform.setImageFromRGB(this.colorTransform.getcB().getColumnDimension(), this.colorTransform.getcB().getRowDimension(), this.colorTransform.getcB(), "Cb").show();
		this.colorTransform.setImageFromRGB(this.colorTransform.getcR().getColumnDimension(), this.colorTransform.getcR().getRowDimension(), this.colorTransform.getcR(), "Cr").show();
	}
	
	public void overSample()
	{
		if (TransformMode != C444) {
			Matrix Cb=colorTransform.getcB();
			Matrix Cr=colorTransform.getcR();
			
				switch (TransformMode) {
				case C422:
					  Cb = this.colorTransform.overSample(Cb);
					  Cr = this.colorTransform.overSample(Cr);
					break;
					
				case C411:
	
					for (int i = 0; i < 2; i++) 
					{
					  Cb = this.colorTransform.overSample(Cb);
					  Cr = this.colorTransform.overSample(Cr);
					}
					break;
					
				case C420:
					for (int i = 0; i < 2; i++) 
					{
						Cb = this.colorTransform.overSample(Cb);
						Cr = this.colorTransform.overSample(Cr);
						Cb = Cb.transpose();
						Cr = Cr.transpose();
					}
					break;
				}
				this.colorTransform.setcB(Cb);
				this.colorTransform.setcR(Cr);
			}
		
		this.colorTransform.convertYcbcrToRgb();
		this.colorTransform.setImageFromRGB(this.colorTransform.getImageWidth(), this.colorTransform.getImageHeight(), this.colorTransform.getRed(), this.colorTransform.getGreen(), this.colorTransform.getBlue(), "Po transformaci").show("Po transformaci");
	}
	
	
	public ImagePlus getComponent(int component) {
		ImagePlus imagePlus = null;
		switch (component) {
		case RED:
			imagePlus = colorTransform.setImageFromRGB(colorTransform.getImageWidth(), colorTransform.getImageHeight(), colorTransform.getRed(), "RED");
			break;
		case GREEN:
			imagePlus = colorTransform.setImageFromRGB(colorTransform.getImageWidth(), colorTransform.getImageHeight(), colorTransform.getGreen(), "GREEN");
			break;
		case BLUE:
			imagePlus = colorTransform.setImageFromRGB(colorTransform.getImageWidth(), colorTransform.getImageHeight(), colorTransform.getBlue(), "BLUE");
			break;
			
		case Y:
			imagePlus = colorTransform.setImageFromRGB(colorTransform.getY().getColumnDimension(), colorTransform.getY().getRowDimension(), colorTransform.getY(), "Y");
			break;
		case Cb:
			imagePlus = colorTransform.setImageFromRGB(colorTransform.getcR().getColumnDimension(), colorTransform.getcR().getRowDimension(), colorTransform.getcR(), "Cb");
			break;
		case Cr:
			imagePlus = colorTransform.setImageFromRGB(colorTransform.getcB().getColumnDimension(), colorTransform.getcB().getRowDimension(), colorTransform.getcB(), "Cr");
			break;
			
		default:
			break;
		}
		return imagePlus;
	}
	
	void TestovaciFCE() {
		Matrix testMat = colorTransform.getQuantizationMatrix8Y();
		
		Matrix[][] testMatB = colorTransform.MatrixToBlocks(testMat, 4);
		System.out.println("Vypisuji matici po rozdìlení na bloky 0 0:");
		testMatB[0][0].print(2, 0);
		System.out.println("");
		testMatB = colorTransform.QuantizationY(testMatB, 60);
		testMatB = colorTransform.InvQuantizationY(testMatB, 60);
		testMat = colorTransform.BlocksToMatrix(testMatB, 4);
		System.out.println("Vypisuji matici po obnoveni:");
		testMat.print(2, 0);
		System.out.println("");
		
		testMat = colorTransform.getQuantizationMatrix8Y();
		testMatB = colorTransform.MatrixToBlocks(testMat, 4);
		System.out.println("Vypisuji matici po rozdìlení na bloky 0 0:");
		testMatB[0][0].print(2, 0);
		System.out.println("");
		testMatB = colorTransform.QuantizationY(testMatB, 60);
		testMatB = colorTransform.InvQuantizationY(testMatB, 60);
		testMat = colorTransform.BlocksToMatrix(testMatB, 4);
		System.out.println("Vypisuji matici po obnoveni:");
		testMat.print(2, 0);
		System.out.println("");

	}
	
	void TestovaciFCE2() {
		int i = 1;
		int x = 1;
		int y = 1;
		int size = 4;
		Matrix mat2 = new Matrix(2*size, 2*size);
		
		
		Matrix mat = new Matrix(size, size);
		for (int m = 0; m < size; m++) {
			for (int n = 0; n < size; n++) {
				mat.set(m, n, i);
				i++;
			}
		}
		mpeg.ExpandMatrix(mat, 4).print(2, 0);
		
		//mat2.setMatrix(0, size, 0, arg3, arg4);
				mat2.setMatrix(x, x+size-1, y, y+size-1, mat);
		mat2.setMatrix(x, x+size-1, y, y+size-1, mat);
		
		int vectors[] = mpeg.Search(mat2, mat); 
		System.out.println("Vypisuji testovací matici:");
		mat.print(2, 0);
		System.out.println("");
		System.out.println("Vypisuji testovací matici2:");
		mat2.print(2, 0);
		System.out.println("");
		
		System.out.println("Vypisuji testovací matici2,2:");
		mat2.getMatrix((int)(size/2), (int)(size*1.5-1), (int)(size/2), (int)(size*1.5-1)).print(2, 0);
		System.out.println("");
		
		System.out.println("matice je v matici 2 posunuta:");
		System.out.println("Øádky: " + vectors[0] + "    Sloupce: " + vectors[1]);
		mpeg.fullSearch(mat, mat2.getMatrix((int)(size/2), (int)(size*1.5-1), (int)(size/2), (int)(size*1.5-1)), 2);
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		Qant16.setToggleGroup(RadioButtonGroup);
		Qant16.setSelected(true);
		Qant8.setToggleGroup(RadioButtonGroup);
		Qant4.setToggleGroup(RadioButtonGroup);
		Qant2.setToggleGroup(RadioButtonGroup);
		Frame16.setToggleGroup(RadioButtonGroupFrame);
		Frame16.setSelected(true);
		Frame8.setToggleGroup(RadioButtonGroupFrame);
		Frame4.setToggleGroup(RadioButtonGroupFrame);
		Frame2.setToggleGroup(RadioButtonGroupFrame);	
		nactiOrigObraz();
		imagePlus.setTitle("Original Image");
		imagePlus.show("Original Image");
		//TestovaciFCE2();
		//TestovaciFCE();
		
	}
}

		
