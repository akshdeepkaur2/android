// This #include statement was automatically added by the Particle IDE.
#include <InternetButton.h>
InternetButton b = InternetButton();


void setup() {

    b.begin();
    Particle.function("music", getPianoKey);

}

void loop(){

}


int getPianoKey(String command)
{

    b.playSong(command);
    return 1;
}
