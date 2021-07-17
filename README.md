# FlappyBirdNeuralNetwork
Evolution README
 
Overview:
    my evolution project has 12 classes
    Constants: Contains the constants used throughout the program
    
    App: Sets the scene and runs the entire program
    
    PaneOrganizer: Sets up the root pane, and the game mode selection screen. Depending on what the player chooses
    it either starts either the manual or smart game mode.
    
    FlappyBird: This is the game class and contains associations to almost every class. Within it a board, stats, and
    bird is created depending on which constructor is used.
    Board: This class creates all the pipes on the screen.
    
    Pipe: This class is used for creating the pipes on the screen. It also has the ability to move left towards the bird
    Bird: This class creates a bird. It has collision detection and can jump up or fall. It also graphically adds it to
    the pane.
    
    SmartBird: Contains all functionality of a bird through inheritance. It also has a neural network and can update
    that neural network.
    
    Stats: Contains all the text elements to create a stat bar for manual flappy bird.
    
    SmartStats: Contains all the text elements to create a stat bar for smart flappy bird.
    
    SpeedControl: This creates 4 buttons to change the speed in smart flappy bird
    
    NeuralNetwork: The neural network has two different constructors. The first doesnt have any parameters and will
    randomly create weights. The second takes in a neural network and sets the weights of the new neural network to it.
    Then it randomly changes the weights by using a mutation rate. The neural network works by setting the inputs
    through a public method within the class. Then it uses forward propagation to get an output that determines if the
    bird jumps or not.
 
