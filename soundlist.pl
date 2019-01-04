use warnings;
use strict;
while (<<>>) {
    chomp($_);
    my @words = split(",", $_);
    print "$words[0],$words[1],$words[2] {{c1::";
    foreach my $i (3 .. (scalar @words-1)) {
        print "$words[$i] ";
    }

    print "}},[sound:$words[0].mp3]";
    print "\n";
}
