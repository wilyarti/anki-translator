use strict;
use warnings;
use feature 'say';
my $count = 0;
while (<<>>) {
	$_ =~ s/[\s]+/ /;
	my $line = $_;
	my @words = split(' ', $_);
	if (defined($words[1])) {
		print "$words[0],$words[1],$words[3],";
		for (4 .. (scalar @words - 1)) {
		        print $words[$_] . " ";
            }
        print "\n";
		$count++;
	} else {
		#print $_;
	}
}

