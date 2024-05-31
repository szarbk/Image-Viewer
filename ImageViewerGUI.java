import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;

public class ImageViewerGUI extends JFrame implements ActionListener{
    JButton selectFileButton= new JButton("select file");
    JButton showImageButton= new JButton("show image");
    JButton resizeButton= new JButton("resize");
    JButton grayscaleButton= new JButton("gray scale");
    JButton brightnessButton= new JButton("brightness");
    JButton closeButton= new JButton("close");
    JButton showResizeButton= new JButton("result");
    JButton showBrightnessButton=new JButton("result");
    JButton backButton=new JButton("back");
    JTextField widthTextField=new JTextField();
    JTextField heightTextField=new JTextField();
    JTextField brightnessTextField=new JTextField();
    String filePath = "/home/...";
    File file;
    JFileChooser fileChooser = new JFileChooser(filePath);
    int h = 900;
    int w = 1200;
    float brightenFactor = 1;
    BufferedImage chimg;
    ImageViewerGUI(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Image Viewer");
        this.setSize(700, 300);
        this.setVisible(true);
        this.setResizable(true);
        selectFileButton.addActionListener(this);
        showImageButton.addActionListener(this);
        brightnessButton.addActionListener(this);
        grayscaleButton.addActionListener(this);
        closeButton.addActionListener(this);
        resizeButton.addActionListener(this);
        backButton.addActionListener(this);
        showResizeButton.addActionListener(this);
        showBrightnessButton.addActionListener(this);
        mainPanel();
    }

    public void mainPanel(){

        // Create main panel for adding to Frame
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        // Create Grid panel for adding buttons to it, then add it all to main panel
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setBounds(150,70,400,120);
        buttonsPanel.setLayout(new GridLayout(3, 2,5,5));
        // Adding all buttons to Grid panel
        buttonsPanel.add(this.selectFileButton);
        buttonsPanel.add(this.showImageButton);
        buttonsPanel.add(this.brightnessButton);
        buttonsPanel.add(this.grayscaleButton);
        buttonsPanel.add(this.resizeButton);
        buttonsPanel.add(this.closeButton);
        // add Grid panel that contains 6 buttons to main panel
        mainPanel.add(buttonsPanel);
        // add main panel to our frame
        this.add(mainPanel);

    }
    public void resizePanel(){
        JPanel resizePanel = new JPanel();
        resizePanel.setLayout(new GridLayout(3, 2,40,40));
        JLabel widthl= new JLabel("     width :");
        JLabel heightl= new JLabel("     height :");
        resizePanel.add(widthl);
        resizePanel.add(this.widthTextField);
        resizePanel.add(heightl);
        resizePanel.add(this.heightTextField);
        resizePanel.add(this.backButton);
        resizePanel.add(this.showResizeButton);
        this.add(resizePanel);
    }
    public void brightnessPanel(){
        JPanel brightnessPanel = new JPanel();
        brightnessPanel.setLayout(new GridLayout(2, 2,40,40));
        JLabel brightnessl= new JLabel("     enter number ( must be between 0 and 1 ) :");
        brightnessPanel.add(brightnessl);
        brightnessPanel.add(this.brightnessTextField);
        brightnessPanel.add(this.showBrightnessButton);
        brightnessPanel.add(this.backButton);
        this.add(brightnessPanel);
    }

    public void chooseFileImage(){
        int option = fileChooser.showOpenDialog(this);
        if(option==JFileChooser.APPROVE_OPTION){
            file = fileChooser.getSelectedFile();
            filePath=file.getPath();
            System.out.println(filePath);
            try {
                chimg = ImageIO.read(file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            showOriginalImage();
        }

    }
    public void showOriginalImage(){
        JFrame tempFrame = new JFrame();
        JPanel tempPanel = new JPanel();
        JLabel picturelabel = new JLabel();
        ImageIcon imageIcon = new ImageIcon(chimg.getScaledInstance(1000,500,Image.SCALE_SMOOTH));
        picturelabel.setIcon(imageIcon);
        tempPanel.add(picturelabel);
        tempPanel.setSize(1000, 500);
        tempFrame.setTitle("Image Viewer");
        tempFrame.setSize(1000, 500);
        tempFrame.setVisible(true);
        tempFrame.setResizable(true);
        tempFrame.add(tempPanel);
    }

    public void grayScaleImage() {
        JFrame tempFrame = new JFrame();
        JPanel tempPanel = new JPanel();
        ImageIcon imageIcon = new ImageIcon(chimg.getScaledInstance(1000,500,Image.SCALE_SMOOTH));
        Image image = imageIcon.getImage();
        ImageFilter filter = new GrayFilter(true, 10);
        ImageProducer producer = new FilteredImageSource(image.getSource(), filter);
        Image mage = Toolkit.getDefaultToolkit().createImage(producer);
        JLabel picLabel = new JLabel(new ImageIcon(mage));
        tempPanel.add(picLabel);
        tempPanel.setSize(1000, 500);
        tempFrame.setTitle("Image Viewer");
        tempFrame.setSize(1000, 500);
        tempFrame.setVisible(true);
        tempFrame.setResizable(true);
        tempFrame.add(tempPanel);    }
    public void showResizeImage(int w, int h){
        JFrame tempFrame = new JFrame();
        JPanel tempPanel = new JPanel();
        JLabel picturelabel = new JLabel();
        ImageIcon imageIcon = new ImageIcon(new ImageIcon(String.valueOf(file)).getImage().getScaledInstance(w,h,Image.SCALE_DEFAULT));
        picturelabel.setIcon(imageIcon);
        tempPanel.add(picturelabel);
        tempPanel.setSize(w, h);
        tempFrame.setTitle("Image Viewer");
        tempFrame.setSize(w, h);
        tempFrame.setVisible(true);
        tempFrame.setResizable(true);
        tempFrame.add(tempPanel);
    }
    public void showBrightnessImage(float f){
        JFrame tempFrame = new JFrame();
        JPanel tempPanel = new JPanel();
        BufferedImage image =chimg;
        RescaleOp op = new RescaleOp(brightenFactor, 0, null);
        image = op.filter(image, image);
        ImageIcon mage = new ImageIcon(image.getScaledInstance(1000,500,Image.SCALE_SMOOTH));;
        JLabel age = new JLabel();
        age.setIcon(mage);
        tempPanel.add(age);
        tempPanel.setSize(1000, 500);
        tempFrame.setTitle("Image Viewer");
        tempFrame.setSize(1000, 500);
        tempFrame.setVisible(true);
        tempFrame.setResizable(true);
        tempFrame.add(tempPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e){

        if(e.getSource()==resizeButton){
            this.getContentPane().removeAll();
            this.resizePanel();
            this.revalidate();
            this.repaint();

        }else if(e.getSource()== showImageButton){
            showOriginalImage();

        }else if(e.getSource()==grayscaleButton){
            grayScaleImage();

        }else if(e.getSource()== showResizeButton){
            h=Integer.parseInt(heightTextField.getText());
            w=Integer.parseInt(widthTextField.getText());
            showResizeImage(w,h);
        }else if(e.getSource()==brightnessButton){
            this.getContentPane().removeAll();
            this.brightnessPanel();
            this.revalidate();
            this.repaint();

        }else if(e.getSource()== showBrightnessButton){
            brightenFactor = Float.parseFloat(brightnessTextField.getText());
            showBrightnessImage(brightenFactor);
        }else if(e.getSource()== selectFileButton){
             chooseFileImage();

        }else if(e.getSource()==closeButton){
            this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        }
        else if(e.getSource()==backButton){
            this.getContentPane().removeAll();
            this.mainPanel();
            this.revalidate();
            this.repaint();
        }
    }
}